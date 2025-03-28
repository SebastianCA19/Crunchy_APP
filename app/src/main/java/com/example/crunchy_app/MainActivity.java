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
import com.example.crunchy_app.productos.DAO.ProductoDao;
import com.example.crunchy_app.productos.DAO.TipoProductoDao;
import com.example.crunchy_app.productos.model.InfoProducto;

import java.util.List;

import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.TipoProducto;
import com.example.crunchy_app.secciones.activity.HomeActivity;

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
            AppDataBase db = AppDataBase.getInstance(getApplicationContext());

            // Verificar que la BD se creó con un log
            Log.d("BASE DE DATOS WIII", "Base de datos creada correctamente: " + db);
            Log.d("BASE DE DATOS", "Ruta de la base de datos: " + getDatabasePath("crunchy-DB").getAbsolutePath());

        }).start();

    }
    public void goToHome(View v){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}
