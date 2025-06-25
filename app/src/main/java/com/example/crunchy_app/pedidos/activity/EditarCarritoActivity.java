package com.example.crunchy_app.pedidos.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.R;
import com.example.crunchy_app.pedidos.adapter.EditarCarritoAdapter;
import com.example.crunchy_app.pedidos.model.ProductoDelPedido;
import com.example.crunchy_app.productos.model.Producto;

import java.util.List;

public class EditarCarritoActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private EditarCarritoAdapter adapter;
    private AppDataBase db;
    private int pedidoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_carrito);
        db = AppDataBase.getInstance(this);
        pedidoId = getIntent().getIntExtra("pedidoId", -1);

        recycler = findViewById(R.id.recyclerEditarCarrito);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btnGuardarCambiosCarrito).setOnClickListener(v -> guardarCambios());

        cargarDatos();
    }

    private void cargarDatos() {
        new Thread(() -> {
            List<ProductoDelPedido> pdpList = db.productoDelPedidoDao().getProductosDelPedido(pedidoId);
            List<Producto> productos = db.productoDao().getAll();

            runOnUiThread(() -> {
                adapter = new EditarCarritoAdapter(this, pdpList, productos);
                recycler.setAdapter(adapter);
            });
        }).start();
    }

    private void guardarCambios() {
        new Thread(() -> {
            db.productoDelPedidoDao().eliminarPorPedido(String.valueOf(pedidoId));
            for (ProductoDelPedido pdp : adapter.getProductosEditados()) {
                pdp.setIdPedido(pedidoId);
                db.productoDelPedidoDao().insert(pdp);
            }

            runOnUiThread(() -> {
                Toast.makeText(this, "Productos actualizados", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}