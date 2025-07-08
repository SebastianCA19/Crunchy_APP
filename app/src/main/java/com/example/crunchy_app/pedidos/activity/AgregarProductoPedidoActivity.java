package com.example.crunchy_app.pedidos.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.R;
import com.example.crunchy_app.pedidos.DAO.ProductoDelPedidoDao;
import com.example.crunchy_app.pedidos.model.Pedido;
import com.example.crunchy_app.pedidos.model.ProductoDelPedido;
import com.example.crunchy_app.productos.DAO.ProductoDao;
import com.example.crunchy_app.productos.DAO.ValorAtributoProductoDao;
import com.example.crunchy_app.productos.OnProductsSelectedListener;
import com.example.crunchy_app.productos.adapter.ProductosPedidoAgregarAdapter;
import com.example.crunchy_app.productos.comidas.adapter.FoodAdapter;
import com.example.crunchy_app.productos.model.AtributoProducto;
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.ValorAtributoProducto;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AgregarProductoPedidoActivity extends AppCompatActivity implements OnProductsSelectedListener {

    private List<ValorAtributoProducto> atributosActivos;

    private Pedido pedido;

    private AppDataBase db;
    private ProductoDelPedidoDao productoDelPedidoDao;

    private ValorAtributoProductoDao atributoProductoDao;

    private ProductoDao productoDao;

    private Map<Producto, Integer> productosPedido;

    private List<Producto> listProdcutos;

    private TextView txtNombreCliente;
    private TextView txtFecha;
    private Button btnComidas;
    private Button btnBebidas;
    private RecyclerView addProductView;
    private RecyclerView productosPedidoView;
    private Button btnAgregarProductos;

    private final int COMIDAS = 0;
    private  final int BEBIDAS = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.pedido = (Pedido) getIntent().getSerializableExtra("pedido");
        this.db = AppDataBase.getInstance(getApplicationContext());

        this.productosPedido = new HashMap<>();

        this.productoDelPedidoDao = db.productoDelPedidoDao();
        this.productoDao = db.productoDao();
        this.atributoProductoDao = db.valorAtributoProductoDao();

        getProductosPedido();

        setContentView(R.layout.add_producto_pedido);
        txtNombreCliente = findViewById(R.id.txtNombreCliente);
        txtFecha = findViewById(R.id.txtFecha);
        btnComidas = findViewById(R.id.btnComidasEditar);
        btnBebidas = findViewById(R.id.btnBebidasEditar);
        addProductView = findViewById(R.id.addProductView);
        addProductView.setLayoutManager(new LinearLayoutManager(this));
        productosPedidoView = findViewById(R.id.productosPedidoView);
        productosPedidoView.setLayoutManager(new LinearLayoutManager(this));
        btnAgregarProductos = findViewById(R.id.btnAgregarProductos);

        txtNombreCliente.setText(pedido.getNombreCliente());
        txtFecha.setText(pedido.getFecha().toString());

        mostrarProductos(COMIDAS);
        btnComidas.setOnClickListener(v -> mostrarProductos(COMIDAS));
        btnBebidas.setOnClickListener(v -> mostrarProductos(BEBIDAS));
        btnAgregarProductos.setOnClickListener(v -> guardarProductosPedido());
    }

    private void getProductosPedido(){
        new Thread(() -> {
            List<ProductoDelPedido> productosDelPedido = productoDelPedidoDao.getProductosByPedido(pedido.getIdPedido());
            List<Producto> productos = productoDao.getAll();

            for (ProductoDelPedido pdp : productosDelPedido) {
                for (Producto p : productos) {
                    if (p.getIdProducto().equals(pdp.getIdProducto())) {
                        productosPedido.put(p, pdp.getCantidad());
                        break;
                    }
                }
            }

            runOnUiThread(this::mostrarProductosPedido);
        }).start();
    }

    private void mostrarProductos(int tipoProducto){
        new Thread(() ->{
            if(tipoProducto == COMIDAS){
                listProdcutos = productoDao.getComidas();
            }else if(tipoProducto == BEBIDAS){
                listProdcutos = productoDao.getBebidas();
            }

            atributosActivos = atributoProductoDao.getAll().stream().filter(ValorAtributoProducto::isActivo).collect(Collectors.toList());

            Map<String, Integer> mapaIds = db.atributoProductoDao().getProductos().stream()
                    .collect(Collectors.toMap(
                            a -> a.getNombreAtributoProducto().toLowerCase().trim(),
                            AtributoProducto::getIdAtributoProducto
                    ));

            int ID_CHICHARRON = mapaIds.getOrDefault("chicharrón", -1);
            int ID_CHORIZO = mapaIds.getOrDefault("chorizo", -1);
            int ID_BOLLO = mapaIds.getOrDefault("bollo", -1);

            runOnUiThread(() ->{
                addProductView.setAdapter(new FoodAdapter(
                        listProdcutos,
                        atributosActivos,
                        ID_CHICHARRON,
                        ID_CHORIZO,
                        ID_BOLLO,
                        this
                ));
            });
        }).start();
    }

    private void mostrarProductosPedido(){
        ProductosPedidoAgregarAdapter adapter = new ProductosPedidoAgregarAdapter((HashMap<Producto, Integer>) productosPedido);
        productosPedidoView.setAdapter(adapter);
        Log.d("productosPedido", "Tamaño: " + productosPedido.size());
    }

    private void guardarProductosPedido(){
        new Thread(() -> {
            for (Map.Entry<Producto, Integer> entry : productosPedido.entrySet()) {
                Producto producto = entry.getKey();
                int cantidad = entry.getValue();

                Log.d("guardarProductosPedido", "Producto: " + producto.getNombreProducto() + ", Cantidad: " + cantidad);

                if(productoDelPedidoDao.productoExistente(producto.getIdProducto(), pedido.getIdPedido())){
                    productoDelPedidoDao.updateCantidad(producto.getIdProducto(), cantidad, pedido.getIdPedido());
                }else{
                    ProductoDelPedido productoDelPedido = new ProductoDelPedido();
                    productoDelPedido.setIdPedido(pedido.getIdPedido());
                    productoDelPedido.setIdProducto(producto.getIdProducto());
                    productoDelPedido.setCantidad(cantidad);

                    productoDelPedidoDao.insert(productoDelPedido);
                }
            }
            runOnUiThread(() -> {
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            });
        }).start();
    }

    @Override
    public void onFoodSelected(int foodId) {
        new Thread(() -> {
            Producto producto = productoDao.getProductoById(foodId);
            SharedPreferences productosStock = getSharedPreferences("stock_prefs", Context.MODE_PRIVATE);
            SharedPreferences productosVendidos = getSharedPreferences("productos_vendidos", Context.MODE_PRIVATE);

            float cantidadChicharron = productosStock.getFloat("chicharron", 0);
            int cantidadChorizo = productosStock.getInt("chorizos", 0);

            float cantidadChicharronVendido = productosVendidos.getFloat("chicharron_vendido", 0);
            int cantidadChorizoVendido = productosVendidos.getInt("chorizo_vendido", 0);
            ValorAtributoProductoDao valorAtributoProductoDao = db.valorAtributoProductoDao();
            if (producto != null) {
                float cantidadChicharronProducto = valorAtributoProductoDao.getChicharronValue(producto.getIdProducto());
                int cantidadChorizoProducto = valorAtributoProductoDao.getChorizoValue(producto.getIdProducto());

                float cantidadChicharronSolicitada = cantidadChicharronProducto + cantidadChicharronVendido;
                int cantidadChorizoSolicitada = cantidadChorizoProducto + cantidadChorizoVendido;

                Log.d("cantidadChicharronSolicitada", String.valueOf(cantidadChicharronSolicitada));
                Log.d("cantidadChicharron", String.valueOf(cantidadChicharron));
                Log.d("cantidadChorizoSolicitada", String.valueOf(cantidadChorizoSolicitada));
                Log.d("cantidadChorizo", String.valueOf(cantidadChorizo));

                if((cantidadChicharronSolicitada > cantidadChicharron) ||
                        (cantidadChorizoSolicitada > cantidadChorizo)) {

                    runOnUiThread(() -> Toast.makeText(
                            AgregarProductoPedidoActivity.this,
                            "No hay suficiente stock, elige otro producto",
                            Toast.LENGTH_SHORT
                    ).show());

                    return;
                }

                int cantidadActual = productosPedido.getOrDefault(producto, 0);
                productosPedido.put(producto, cantidadActual + 1);
            }

            runOnUiThread(() ->{
                mostrarProductosPedido();
            });
        }).start();
    }

    @Override
    public void showInfoDialog(String productName) {

    }
}
