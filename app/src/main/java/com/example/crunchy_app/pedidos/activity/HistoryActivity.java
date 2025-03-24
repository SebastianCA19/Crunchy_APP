package com.example.crunchy_app.pedidos.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crunchy_app.pedidos.adapter.PedidoAdapter;
import com.example.crunchy_app.R;
import com.example.crunchy_app.pedidos.model.Pedido;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PedidoAdapter adapter;
    private List<Pedido> listaPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.RecyclerViewHistorial);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaPedidos = new ArrayList<>();
        cargarDatosDeEjemplo();

        adapter = new PedidoAdapter(listaPedidos);
        recyclerView.setAdapter(adapter);
    }

    private void cargarDatosDeEjemplo() {
        listaPedidos.add(new Compra("Juan Pérez", "Hamburguesa", "Coca Cola", 25000, "2025-03-21"));
        listaPedidos.add(new Compra("María González", "Pizza", "", 30000, "2025-03-20"));
        listaPedidos.add(new Compra("Carlos López", "Hot Dog", "Sprite", 18000, "2025-03-19"));
        listaPedidos.add(new Compra("Ana Torres", "Sushi", "Té Verde", 45000, "2025-03-18"));
        listaPedidos.add(new Compra("Pedro Ramírez", "Arepa", "Jugo de naranja", 12000, "2025-03-17"));
        listaPedidos.add(new Compra("Pedro Ramírez", "Arepa", "Jugo de naranja", 12000, "2025-03-17"));
        listaPedidos.add(new Compra("Pedro Ramírez", "Arepa", "Jugo de naranja", 12000, "2025-03-17"));
        listaPedidos.add(new Compra("Pedro Ramírez", "Arepa", "Jugo de naranja", 12000, "2025-03-17"));
        listaPedidos.add(new Compra("Pedro Ramírez", "Arepa", "Jugo de naranja", 12000, "2025-03-17"));
        listaPedidos.add(new Compra("Pedro Ramírez", "Arepa", "Jugo de naranja", 12000, "2025-03-17"));
        listaPedidos.add(new Compra("Pedro Ramírez", "Arepa", "Jugo de naranja", 12000, "2025-03-17"));
        listaPedidos.add(new Compra("Pedro Ramírez", "Arepa", "Jugo de naranja", 12000, "2025-03-17"));
    }
}
