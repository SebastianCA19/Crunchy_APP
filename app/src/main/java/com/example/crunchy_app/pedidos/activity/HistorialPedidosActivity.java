package com.example.crunchy_app.pedidos.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.R;
import com.example.crunchy_app.pedidos.adapter.PedidoHistorialAdapter;
import com.example.crunchy_app.pedidos.model.EstadoPedido;
import com.example.crunchy_app.pedidos.model.PedidoConEstado;
import com.example.crunchy_app.pedidos.model.ProductoDelPedido;
import com.example.crunchy_app.productos.model.Producto;

import java.util.List;

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

        new Thread(() -> {
            List<PedidoConEstado> pedidos = db.pedidoDao().getPedidosConEstado();
            List<EstadoPedido> estados = db.estadoPedidoDao().getAll();
            List<ProductoDelPedido> productosPedido = db.productoDelPedidoDao().getAll();
            List<Producto> productos = db.productoDao().getAll();

            runOnUiThread(() -> {
                PedidoHistorialAdapter adapter = new PedidoHistorialAdapter(
                        pedidos, productosPedido, productos, estados, db
                );
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }
}