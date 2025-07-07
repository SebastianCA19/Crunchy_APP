package com.example.crunchy_app.reportes.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import androidx.annotation.RequiresApi;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.pedidos.model.Pedido;
import com.example.crunchy_app.pedidos.model.ProductoDelPedido;
import com.example.crunchy_app.productos.model.AtributoProducto;
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.ValorAtributoProducto;
import com.example.crunchy_app.reportes.model.ResumenPorDia;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.O)
public class GeneradorResumen {

    public static ResumenPorDia generar(Context context, String fecha) {
        AppDataBase db = AppDataBase.getInstance(context);

        List<Pedido> pedidosDelDia = db.pedidoDao().getPedidosPorFecha(fecha);
        List<ProductoDelPedido> productosDelDia = db.productoDelPedidoDao().getProductosDelPedidoPorFecha(fecha);
        List<ValorAtributoProducto> atributos = db.valorAtributoProductoDao().getAll();
        List<Producto> productos = db.productoDao().getAll();

        Map<String, Integer> mapaIds = db.atributoProductoDao().getProductos().stream()
                .collect(Collectors.toMap(
                        a -> a. getNombreAtributoProducto().toLowerCase().trim(),
                        AtributoProducto::getIdAtributoProducto
                ));

        int ID_CHICHARRON = mapaIds.get("chicharrón");
        int ID_CHORIZO = mapaIds.get("chorizo");
        int ID_BOLLO = mapaIds.get("bollo");


        // Cargar valor por gramo desde SharedPreferences
        SharedPreferences prefs = context.getSharedPreferences("stock_prefs", Context.MODE_PRIVATE);
        float valorPorGramo = prefs.getFloat("valor_por_gramo", 0f);

        float ingresoEfectivo = 0f;
        float ingresoTransferencia = 0f;

        float totalChicharronGr = 0f;
        int totalChorizoUnidades = 0;
        float totalBolloUnidades = 0f;

        float ingresoBebidaPersonal = 0f;
        float ingresoBebidaFamiliar = 0f;
        float ingresoBebidaAlcoholica = 0f;
        float ingresoCombos = 0f;
        float ingresoPicadas = 0f;

        Map<String, Integer> topCombos = new HashMap<>();
        Map<String, Integer> topPicadas = new HashMap<>();
        Map<String, Integer> topBebidaPersonal = new HashMap<>();
        Map<String, Integer> topBebidaFamiliar = new HashMap<>();
        Map<String, Integer> topBebidaAlcoholica = new HashMap<>();

        // Map de productos por ID
        Map<Integer, Producto> mapaProductos = productos.stream()
                .collect(Collectors.toMap(Producto::getIdProducto, p -> p));

        // Map de productos del pedido agrupados por ID de pedido
        Map<Integer, List<ProductoDelPedido>> productosPorPedido = productosDelDia.stream()
                .collect(Collectors.groupingBy(ProductoDelPedido::getIdPedido));

        // Calcular ingresos por método de pago
        for (Pedido pedido : pedidosDelDia) {
            float totalPedido = 0f;
            List<ProductoDelPedido> productosDeEstePedido = productosPorPedido.get(pedido.getIdPedido());
            if (productosDeEstePedido != null) {
                for (ProductoDelPedido pdp : productosDeEstePedido) {
                    Producto producto = mapaProductos.get(pdp.getIdProducto());
                    if (producto != null) {
                        int tipoId = producto.getIdTipoProducto();
                        float subtotal = 0;

                        if (tipoId == 6) { // personalizado
                            for (ValorAtributoProducto v : atributos) {
                                if (v.getIdProducto().equals(producto.getIdProducto()) && v.isActivo()) {
                                    if (v.getIdAtributoProducto() == 2) { // chicharrón
                                        subtotal += v.getValorAtributoProducto() * valorPorGramo * pdp.getCantidad();
                                    } else {
                                        subtotal += producto.getValorProducto() * pdp.getCantidad();
                                    }
                                }
                            }
                        } else {
                            subtotal = producto.getValorProducto() * pdp.getCantidad();
                        }

                        totalPedido += subtotal;
                    }
                }
            }

            if (pedido.getIdMetodoPago() == 1) {
                ingresoEfectivo += totalPedido;
            } else {
                ingresoTransferencia += totalPedido;
            }
        }

        // Calcular ingresos y cantidades por tipo
        for (ProductoDelPedido p : productosDelDia) {
            Producto producto = mapaProductos.get(p.getIdProducto());
            if (producto == null) continue;

            int cantidad = p.getCantidad();
            int tipoId = producto.getIdTipoProducto();
            String nombre = producto.getNombreProducto();
            float subtotal = producto.getValorProducto() * cantidad;

            switch (tipoId) {
                case 1: // combo
                    ingresoCombos += subtotal;
                    topCombos.put(nombre, topCombos.getOrDefault(nombre, 0) + cantidad);
                    for (ValorAtributoProducto v : atributos) {
                        if (v.getIdProducto().equals(producto.getIdProducto()) && v.isActivo()) {
                            int idAtributo = v.getIdAtributoProducto();
                            float valor = v.getValorAtributoProducto();
                            if (idAtributo == ID_CHICHARRON) totalChicharronGr += valor * cantidad;
                            if (idAtributo == ID_CHORIZO) totalChorizoUnidades += (int) (valor * cantidad);
                            if (idAtributo == ID_BOLLO) totalBolloUnidades += valor * cantidad;
                        }
                    }
                    break;
                case 2: // picada
                    ingresoPicadas += subtotal;
                    topPicadas.put(nombre, topPicadas.getOrDefault(nombre, 0) + cantidad);
                    for (ValorAtributoProducto v : atributos) {
                        if (v.getIdProducto().equals(producto.getIdProducto()) && v.isActivo()) {
                            int idAtributo = v.getIdAtributoProducto();
                            float valor = v.getValorAtributoProducto();
                            if (idAtributo == ID_CHICHARRON) totalChicharronGr += valor * cantidad;
                            if (idAtributo == ID_BOLLO) totalBolloUnidades += valor * cantidad;

                        }
                    }
                    break;
                case 3: // bebida personal
                    ingresoBebidaPersonal += subtotal;
                    topBebidaPersonal.put(nombre, topBebidaPersonal.getOrDefault(nombre, 0) + cantidad);
                    break;
                case 4: // bebida familiar
                    ingresoBebidaFamiliar += subtotal;
                    topBebidaFamiliar.put(nombre, topBebidaFamiliar.getOrDefault(nombre, 0) + cantidad);
                    break;
                case 5: // bebida alcohólica
                    ingresoBebidaAlcoholica += subtotal;
                    topBebidaAlcoholica.put(nombre, topBebidaAlcoholica.getOrDefault(nombre, 0) + cantidad);
                    break;
                case 6: // personalizado
                    for (ValorAtributoProducto v : atributos) {
                        if (v.getIdProducto().equals(producto.getIdProducto()) && v.isActivo()) {
                            int idAtributo = v.getIdAtributoProducto();
                            float valor = v.getValorAtributoProducto();
                            if (idAtributo == ID_CHICHARRON) totalChicharronGr += valor * cantidad;
                            if (idAtributo == ID_CHORIZO) totalChorizoUnidades += (int) (valor * cantidad);
                            if (idAtributo == ID_BOLLO) totalBolloUnidades += valor * cantidad;
                        }
                    }
                    break;
            }
        }

        return new ResumenPorDia(
                LocalDate.parse(fecha),
                ingresoEfectivo,
                ingresoTransferencia,
                totalChicharronGr,
                totalChorizoUnidades,
                totalBolloUnidades,
                ingresoBebidaPersonal,
                ingresoBebidaFamiliar,
                ingresoBebidaAlcoholica,
                ingresoCombos,
                ingresoPicadas,
                top3(topCombos),
                top3(topPicadas),
                top3(topBebidaPersonal),
                top3(topBebidaFamiliar),
                top3(topBebidaAlcoholica),
                pedidosDelDia.size()
        );
    }

    private static String top3(Map<String, Integer> mapa) {
        return mapa.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(3)
                .map(e -> "   • " + e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining("\n"));
    }
}
