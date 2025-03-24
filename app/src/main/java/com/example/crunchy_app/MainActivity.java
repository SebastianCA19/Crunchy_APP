package com.example.crunchy_app;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.productos.DAO.InfoProductoDao;
import com.example.crunchy_app.productos.model.InfoProducto;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Manejo de base de datos en un hilo separado
        new Thread(() -> {
            AppDataBase db = Room.databaseBuilder(getApplicationContext(),
                            AppDataBase.class, "crunchy-DB")
                    .fallbackToDestructiveMigration() // Borra y recrea la BD si hay cambios en la estructura
                    .build();

            InfoProductoDao infoProductoDao = db.infoProductoDao();

            // Insertar datos de prueba
            infoProductoDao.insert(new InfoProducto(10, 2));
            infoProductoDao.insert(new InfoProducto(20, 6));

            // Obtener datos y loguearlos
            List<InfoProducto> infos = infoProductoDao.getAll();
            if (!infos.isEmpty()) {
                Log.d("Pruebita", infos.get(0).toString());
            } else {
                Log.d("Pruebita", "No hay productos en la base de datos.");
            }
        }).start();

    }
    public void goToHome(View v){
        Intent intent = new Intent(this, com.example.crunchy_app.Home.class);
        startActivity(intent);
    }

}
