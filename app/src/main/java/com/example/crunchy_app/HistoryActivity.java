package com.example.crunchy_app;

import android.os.Bundle;
import androidx.annotation.Nullable;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Simulación de compras
        listaCompras = new ArrayList<>();
        listaCompras.add(new Compra("Juan Pérez", "Hamburguesa", "Coca-Cola", "$15.000", "09/03/2025"));
        listaCompras.add(new Compra("María López", "Pizza", "", "$20.000", "08/03/2025"));
        listaCompras.add(new Compra("Carlos Ramírez", "Tacos", "Agua", "$12.500", "07/03/2025"));
        listaCompras.add(new Compra("Juan Pérez", "Hamburguesa", "Coca-Cola", "$15.000", "09/03/2025"));
        listaCompras.add(new Compra("Juan Pérez", "Hamburguesa", "Coca-Cola", "$15.000", "09/03/2025"));
        listaCompras.add(new Compra("Juan Pérez", "Hamburguesa", "Coca-Cola", "$15.000", "09/03/2025"));
        listaCompras.add(new Compra("Juan Pérez", "Hamburguesa", "Coca-Cola", "$15.000", "09/03/2025"));
        listaCompras.add(new Compra("Juan Pérez", "Hamburguesa", "Coca-Cola", "$15.000", "09/03/2025"));
        listaCompras.add(new Compra("Juan Pérez", "Hamburguesa", "Coca-Cola", "$15.000", "09/03/2025"));
        listaCompras.add(new Compra("Juan Pérez", "Hamburguesa", "Coca-Cola", "$15.000", "09/03/2025"));
        listaCompras.add(new Compra("Juan Pérez", "Hamburguesa", "Coca-Cola", "$15.000", "09/03/2025"));


        adapter = new CompraAdapter(listaCompras);
        recyclerView.setAdapter(adapter);
    }
}
