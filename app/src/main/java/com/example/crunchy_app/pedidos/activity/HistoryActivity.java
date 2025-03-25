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

    }
}
