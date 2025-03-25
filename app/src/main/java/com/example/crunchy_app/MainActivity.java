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
            AppDataBase db = Room.databaseBuilder(getApplicationContext(),
                            AppDataBase.class, "crunchy-DB")
                    .fallbackToDestructiveMigration() // Borra y recrea la BD si hay cambios en la estructura
                    .build();

            // Obtener DAOs
            InfoProductoDao infoProductoDao = db.infoProductoDao();
            ProductoDao productoDao = db.productoDao();
            TipoProductoDao tipoProductoDao = db.tipoProductoDao();

            // Insertar datos de prueba en TipoProducto
            TipoProducto tipo1 = new TipoProducto("Embutidos");
            long idTipo1 = tipoProductoDao.insert(tipo1);

            // Insertar datos de prueba en InfoProducto
            InfoProducto info1 = new InfoProducto(10, 2);
            long idInfo1 = infoProductoDao.insert(info1);

            // Insertar datos de prueba en Producto
            Producto producto1 = new Producto("Chorizo", (int) idTipo1, 5000, (int) idInfo1);
            productoDao.insert(producto1);

            // Obtener y loguear los datos de InfoProducto
            List<InfoProducto> infoProductos = infoProductoDao.getAll();
            for (InfoProducto info : infoProductos) {
                Log.d("Pruebita", "InfoProducto - ID: " + info.getIdInfoProducto() +
                        ", Chicharrón: " + info.getCantidadChicharron() +
                        ", Chorizo: " + info.getCantidadChorizo());
            }

            // Obtener y loguear los datos de Producto
            List<Producto> productos = productoDao.getAll();
            for (Producto producto : productos) {
                Log.d("Pruebita", "Producto - ID: " + producto.getIdProducto() +
                        ", Nombre: " + producto.getNombreProducto() +
                        ", Precio: " + producto.getPrecio() +
                        ", ID Tipo Producto: " + producto.getIdTipoProducto() +
                        ", ID Info Producto: " + producto.getIdInfoProducto());
            }

            // Obtener y loguear los datos de TipoProducto
            List<TipoProducto> tiposProductos = tipoProductoDao.getAll();
            for (TipoProducto tipo : tiposProductos) {
                Log.d("Pruebita", "TipoProducto - ID: " + tipo.getIdTipoProdcuto() +
                        ", Nombre: " + tipo.getNombreTipoProducto());
            }

        }).start();

    }
    public void goToHome(View v){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}
