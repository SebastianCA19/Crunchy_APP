package com.example.crunchy_app.pedidos.activity;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.R;
import com.example.crunchy_app.pedidos.adapter.PedidoHistorialAdapter;
import com.example.crunchy_app.pedidos.model.EstadoPedido;
import com.example.crunchy_app.pedidos.model.Locacion;
import com.example.crunchy_app.pedidos.model.PedidoConEstado;
import com.example.crunchy_app.pedidos.model.ProductoDelPedido;
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.ValorAtributoProducto;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HistorialPedidosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pedidos);


        recyclerView = findViewById(R.id.recyclerHistorial);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        db = AppDataBase.getInstance(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.historialToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Historial de pedidos");


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> finish());


        new Thread(() -> {
            List<PedidoConEstado> pedidos = db.pedidoDao().getPedidosConEstado();
            List<EstadoPedido> estados = db.estadoPedidoDao().getAll();
            List<ProductoDelPedido> productosPedido = db.productoDelPedidoDao().getAll();
            List<Producto> productos = db.productoDao().getAll();
            Map<Integer,Locacion> locaciones = db.locacionDao().getAll().stream()
                    .collect(Collectors.toMap(locacion -> locacion.getIdLocacion(), locacion -> locacion));
            List<ValorAtributoProducto> chicharronQuantities = db.valorAtributoProductoDao().getCantidadChicharron();

            runOnUiThread(() -> {
                PedidoHistorialAdapter adapter = new PedidoHistorialAdapter(
                        pedidos, productosPedido, productos, estados, locaciones ,db, chicharronQuantities
                );
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }
}