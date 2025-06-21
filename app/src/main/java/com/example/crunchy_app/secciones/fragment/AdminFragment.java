package com.example.crunchy_app.secciones.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.R;
import com.example.crunchy_app.pedidos.activity.HistorialPedidosActivity;
import com.example.crunchy_app.pedidos.model.Pedido;
import com.example.crunchy_app.pedidos.model.ProductoDelPedido;
import com.example.crunchy_app.productos.DAO.ValorAtributoProductoDao;
import com.example.crunchy_app.productos.activity.GestionProductosActivity;
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.ValorAtributoProducto;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class AdminFragment extends Fragment {

    private TextView txtGanancias;

    private int cantidadChicharron=0;
    private int cantidadChorizos=0;

    private int cantidadChicharronVendidos=0;
    private int cantidadChorizosVendidos=0;

    private AppDataBase db;

    public AdminFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        txtGanancias = view.findViewById(R.id.gananciasValor);
        db = AppDataBase.getInstance(getContext());


        cargarGanancias(); //  llamada inicial
        SharedPreferences prefs = requireContext().getSharedPreferences("stock_prefs", Context.MODE_PRIVATE);
        cantidadChicharron = prefs.getInt("chicharron", 0);
        cantidadChorizos = prefs.getInt("chorizos", 0);

        SharedPreferences productosVendidos = requireContext().getSharedPreferences("productos_vendidos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = productosVendidos.edit();
        if(!productosVendidos.contains("chicharron_vendido") && !productosVendidos.contains("chorizo_vendido")){
            editor.putFloat("chicharron_vendido", 0);
            editor.putInt("chorizo_vendido", 0);
            editor.apply();
        }

        actualizarCantidadChicharron(view);
        actualizarCantidadChorizos(view);
        Button btnHistorial = view.findViewById(R.id.btnHistorial);
        btnHistorial.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HistorialPedidosActivity.class);
            startActivity(intent);
        });

        Button btnGestionProductos = view.findViewById(R.id.btnGestionProductos);
        btnGestionProductos.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), GestionProductosActivity.class);
            startActivity(intent);
        });


        Button btnGuardarChicharron = view.findViewById(R.id.btnGuardarChicharron);

        btnGuardarChicharron.setOnClickListener(v -> {
            EditText inputMaxChicharron = view.findViewById(R.id.inputMaxChicharron);
            String valorInput = inputMaxChicharron.getText().toString().trim();
            if (!valorInput.isEmpty() && Integer.parseInt(valorInput) > 0) {
                cantidadChicharron += Integer.parseInt(valorInput);
                actualizarCantidadChicharron(view);
                inputMaxChicharron.setText("");
            } else {
                inputMaxChicharron.setError("Este campo no puede estar vacío");
            }
            prefs.edit().putInt("chicharron", cantidadChicharron).apply();
        });

        Button btnRestarChicharron = view.findViewById(R.id.btnRestarChicharron);

        btnRestarChicharron.setOnClickListener(v -> {
            EditText inputMaxChicharron = view.findViewById(R.id.inputMaxChicharron);
            String valorInput = inputMaxChicharron.getText().toString().trim();
            int cantidadInput = Integer.parseInt(valorInput);
            if (!valorInput.isEmpty() && Integer.parseInt(valorInput) > 0) {
                if(!(cantidadChicharron - cantidadInput < 0)){
                    cantidadChicharron -= cantidadInput;
                }else{
                    inputMaxChicharron.setError("No se puede restar más de lo que hay en stock");
                }
                actualizarCantidadChicharron(view);
                inputMaxChicharron.setText("");
            }else{
                inputMaxChicharron.setError("Este campo no puede estar vacío");
            }
            prefs.edit().putInt("chicharron", cantidadChicharron).apply();
        });

        Button btnGuardarChorizos = view.findViewById(R.id.btnGuardarChorizos);

        btnGuardarChorizos.setOnClickListener(v -> {
            EditText inputMaxChorizos = view.findViewById(R.id.inputMaxChorizos);
            String valorInput = inputMaxChorizos.getText().toString().trim();
            if (!valorInput.isEmpty() && Integer.parseInt(valorInput) > 0) {
                cantidadChorizos += Integer.parseInt(valorInput);
                actualizarCantidadChorizos(view);
                inputMaxChorizos.setText("");
            } else {
                inputMaxChorizos.setError("Este campo no puede estar vacío");
            }
            prefs.edit().putInt("chorizos", cantidadChorizos).apply();

        });

        Button btnRestarChorizos = view.findViewById(R.id.btnRestarChorizos);
        btnRestarChorizos.setOnClickListener(v -> {
            EditText inputMaxChorizos = view.findViewById(R.id.inputMaxChorizos);
            String valorInput = inputMaxChorizos.getText().toString().trim();
            int cantidadInput = Integer.parseInt(valorInput);
            if (!valorInput.isEmpty() && Integer.parseInt(valorInput) > 0) {
                if(!(cantidadChorizos - cantidadInput < 0)){
                    cantidadChorizos -= cantidadInput;
                }else{
                    inputMaxChorizos.setError("No se puede restar más de lo que hay en stock");
                }
                actualizarCantidadChorizos(view);
                inputMaxChorizos.setText("");
            } else {
                inputMaxChorizos.setError("Este campo no puede estar vacío");
            }
            prefs.edit().putInt("chorizos", cantidadChorizos).apply();
        });

        return view;
    }

    private void actualizarCantidadChorizos(View view) {
        TextView txtChorizos = view.findViewById(R.id.chorizosLabel);
        txtChorizos.setText("Chorizos: " + cantidadChorizosVendidos + " / " + cantidadChorizos+" Unidades");
        actualizarProgressBarChorizos(view);
        SharedPreferences productosVendidos = requireContext().getSharedPreferences("productos_vendidos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = productosVendidos.edit();
        editor.putInt("chorizo_vendido", cantidadChorizosVendidos);
        editor.apply();
    }

    private void actualizarCantidadChicharron(View view) {
        TextView txtChicharron = view.findViewById(R.id.chicharronLabel);
        txtChicharron.setText("Chicharrón: " + cantidadChicharronVendidos + " / " + cantidadChicharron+ " gr");
        actualizarProgressBarChicharron(view);
        SharedPreferences productosVendidos = requireContext().getSharedPreferences("productos_vendidos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = productosVendidos.edit();
        editor.putFloat("chicharron_vendido", cantidadChicharronVendidos);
        editor.apply();
    }

    private void actualizarProgressBarChicharron(View view) {
        int progress = (cantidadChicharron == 0) ? 0 :
                (int) ((double) cantidadChicharronVendidos / cantidadChicharron * 100);
        ProgressBar progressBar = view.findViewById(R.id.progressChicharron);
        progressBar.setProgress(progress);
    }

    private void actualizarProgressBarChorizos(View view) {
        int progress = (cantidadChorizos == 0) ? 0 :
                (int) ((double) cantidadChorizosVendidos / cantidadChorizos * 100);
        ProgressBar progressBar = view.findViewById(R.id.progressChorizos);
        progressBar.setProgress(progress);
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarGanancias();
        cargarCantidadChicharronYChorizos();
    }

    private void cargarGanancias() {
        new Thread(() -> {
            List<Pedido> pedidosPagados = db.pedidoDao().getPedidosPorEstado(3);
            List<ProductoDelPedido> todosProductos = db.productoDelPedidoDao().getAll();
            List<Producto> productos = db.productoDao().getAll();

            double totalGanancias = 0;

            for (Pedido pedido : pedidosPagados) {
                for (ProductoDelPedido pdp : todosProductos) {
                    if (pdp.getIdPedido().equals(pedido.getIdPedido())) {
                        for (Producto producto : productos) {
                            if (producto.getIdProducto().equals(pdp.getIdProducto())) {
                                if(producto.getIdProducto() == 41){
                                    ValorAtributoProductoDao atributoProductoDao = db.valorAtributoProductoDao();
                                    String productoIdFormat = String.format("%d%d", producto.getIdProducto(), pedido.getIdPedido());
                                    float chicharron = atributoProductoDao.getValorAtributoProductoPersonalizado(Integer.valueOf(productoIdFormat)).getValorAtributoProducto();
                                    totalGanancias += chicharron * 80;
                                    break;
                                }
                                totalGanancias += producto.getValorProducto() * pdp.getCantidad();
                                break;
                            }
                        }
                    }
                }
            }

            double finalTotalGanancias = totalGanancias;
            requireActivity().runOnUiThread(() ->
                    txtGanancias.setText("$" + String.format("%,.0f", finalTotalGanancias)+ " COP")
            );
        }).start();
    }

    private void cargarCantidadChicharronYChorizos() {
        cantidadChicharronVendidos = 0;
        cantidadChorizosVendidos = 0;

        new Thread(() -> {
            List<Integer> estadosValidos = Arrays.asList(2, 3, 4); // preparando, pagado, en camino
            List<Pedido> pedidosFiltrados = db.pedidoDao().getListaPedidosPorEstados(estadosValidos);
            List<ProductoDelPedido> todosProductos = db.productoDelPedidoDao().getAll();
            List<Producto> productos = db.productoDao().getAll();
            ValorAtributoProductoDao atributoProductoDao = db.valorAtributoProductoDao();

            // 🚫 NO reinicies aquí dentro: solo fuera arriba.

            for (Pedido pedido : pedidosFiltrados) {
                for (ProductoDelPedido pdp : todosProductos) {
                    if (pdp.getIdPedido().equals(pedido.getIdPedido())) {
                        for (Producto producto : productos) {
                            if (producto.getIdProducto().equals(pdp.getIdProducto())) {

                                if (producto.getIdProducto() == 41) {
                                    String productoIdFormat = String.format("%d%d", producto.getIdProducto(), pedido.getIdPedido());
                                    ValorAtributoProducto valor = atributoProductoDao.getValorAtributoProductoPersonalizado(Integer.valueOf(productoIdFormat));
                                    if (valor != null) {
                                        cantidadChicharronVendidos += valor.getValorAtributoProducto();
                                    }
                                } else if (producto.getIdProducto() == 42) {
                                    cantidadChorizosVendidos += pdp.getCantidad();
                                } else {
                                    for (ValorAtributoProducto vap : atributoProductoDao.getCantidadChicharron()) {
                                        if (vap.getIdProducto().equals(producto.getIdProducto())) {
                                            cantidadChicharronVendidos += vap.getValorAtributoProducto() * pdp.getCantidad();
                                            break;
                                        }
                                    }
                                    for (ValorAtributoProducto vap : atributoProductoDao.getCantidadChorizo()) {
                                        if (vap.getIdProducto().equals(producto.getIdProducto())) {
                                            cantidadChorizosVendidos += vap.getValorAtributoProducto() * pdp.getCantidad();
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 👇 Importante: NO uses requireView() si hay posibilidad de que no esté aún
            requireActivity().runOnUiThread(() -> {
                View view = getView();
                if (view != null) {
                    actualizarCantidadChicharron(view);
                    actualizarCantidadChorizos(view);
                }
            });
        }).start();
    }

}

