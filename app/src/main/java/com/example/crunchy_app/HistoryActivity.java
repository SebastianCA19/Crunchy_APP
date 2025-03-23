package com.example.crunchy_app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CompraAdapter adapter;
    private List<Compra> listaCompras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.RecyclerViewHistorial);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaCompras = new ArrayList<>();
        cargarDatosDeEjemplo();

        adapter = new CompraAdapter(listaCompras);
        recyclerView.setAdapter(adapter);
    }

    private void cargarDatosDeEjemplo() {
        listaCompras.add(new Compra("Juan Pérez", "Hamburguesa", "Coca Cola", 25000, "2025-03-21"));
        listaCompras.add(new Compra("María González", "Pizza", "", 30000, "2025-03-20"));
        listaCompras.add(new Compra("Carlos López", "Hot Dog", "Sprite", 18000, "2025-03-19"));
        listaCompras.add(new Compra("Ana Torres", "Sushi", "Té Verde", 45000, "2025-03-18"));
        listaCompras.add(new Compra("Pedro Ramírez", "Arepa", "Jugo de naranja", 12000, "2025-03-17"));
        listaCompras.add(new Compra("Pedro Ramírez", "Arepa", "Jugo de naranja", 12000, "2025-03-17"));
        listaCompras.add(new Compra("Pedro Ramírez", "Arepa", "Jugo de naranja", 12000, "2025-03-17"));
        listaCompras.add(new Compra("Pedro Ramírez", "Arepa", "Jugo de naranja", 12000, "2025-03-17"));
        listaCompras.add(new Compra("Pedro Ramírez", "Arepa", "Jugo de naranja", 12000, "2025-03-17"));
        listaCompras.add(new Compra("Pedro Ramírez", "Arepa", "Jugo de naranja", 12000, "2025-03-17"));
        listaCompras.add(new Compra("Pedro Ramírez", "Arepa", "Jugo de naranja", 12000, "2025-03-17"));
        listaCompras.add(new Compra("Pedro Ramírez", "Arepa", "Jugo de naranja", 12000, "2025-03-17"));
    }
}
