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
import com.example.crunchy_app.pagos.DAO.MetodoPagoDao;
import com.example.crunchy_app.pagos.model.MetodoPago;
import com.example.crunchy_app.pedidos.DAO.EstadoPedidoDao;
import com.example.crunchy_app.pedidos.DAO.LocacionDao;
import com.example.crunchy_app.pedidos.model.EstadoPedido;
import com.example.crunchy_app.pedidos.model.Locacion;
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
            Log.d("BASE DE DATOS", "Base de datos creada correctamente: " + db);
            Log.d("BASE DE DATOS", "Ruta de la base de datos: " + getDatabasePath("crunchy-DB").getAbsolutePath());

            if(db.estadoPedidoDao().count() == 0 || db.metodoPagoDao().count() == 0 ||
                    db.tipoProductoDao().count() == 0 || db.infoProductoDao().count() == 0 ||
                    db.productoDao().count() == 0 || db.locacionDao().count() == 0){
                insertarDatosIniciales(db);
            }


        }).start();

    }
    public void goToHome(View v){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private static void insertarDatosIniciales(AppDataBase db) {
        EstadoPedidoDao estadoPedidoDao = db.estadoPedidoDao();
        MetodoPagoDao metodoPagoDao = db.metodoPagoDao();
        LocacionDao locacionDao = db.locacionDao();

        TipoProductoDao tipoProductoDao = db.tipoProductoDao();
        InfoProductoDao infoProductoDao = db.infoProductoDao();
        ProductoDao productoDao = db.productoDao();

        // Insertar Estados de Pedido
        if(estadoPedidoDao.count() == 0){
            estadoPedidoDao.insert(new EstadoPedido("encargada"));
            estadoPedidoDao.insert(new EstadoPedido("preparando"));
            estadoPedidoDao.insert(new EstadoPedido("pagado"));
            estadoPedidoDao.insert(new EstadoPedido("en camino"));
        }
        //Insertar Locaciones
        if(locacionDao.count() == 0){
            locacionDao.insert(new Locacion("libano", null, 4000));
            locacionDao.insert(new Locacion("pando", null, 8000));
            locacionDao.insert(new Locacion("santa cruz", null, 0));
        }

        // Insertar Métodos de Pago
        if(metodoPagoDao.count() == 0){
            metodoPagoDao.insert(new MetodoPago("efectivo"));
            metodoPagoDao.insert(new MetodoPago("transferencia"));
            metodoPagoDao.insert(new MetodoPago("mixto"));
        }

        // Insertar Tipos de Producto
        if (tipoProductoDao.count() == 0){
            tipoProductoDao.insert(new TipoProducto("combo"));
            tipoProductoDao.insert(new TipoProducto("picada"));
            tipoProductoDao.insert(new TipoProducto("bebida-personal"));
            tipoProductoDao.insert(new TipoProducto("bebida-familiar"));
            tipoProductoDao.insert(new TipoProducto("bebida-alcoholica"));
            tipoProductoDao.insert(new TipoProducto("personalizado"));
        }

        // Insertar InfoProducto
        if (infoProductoDao.count() == 0){
            infoProductoDao.insert(new InfoProducto(0, 1, 1));
            infoProductoDao.insert(new InfoProducto(215, 1, 1.5f));
            infoProductoDao.insert(new InfoProducto(360, 1, 2));
            infoProductoDao.insert(new InfoProducto(480, 2, 3));
            infoProductoDao.insert(new InfoProducto(695, 2, 4));
            infoProductoDao.insert(new InfoProducto(820, 3, 5));
            infoProductoDao.insert(new InfoProducto(215, 0, 1));
            infoProductoDao.insert(new InfoProducto(290, 0, 1.5f));
            infoProductoDao.insert(new InfoProducto(430, 0, 2));
            infoProductoDao.insert(new InfoProducto(605, 0, 3));
            infoProductoDao.insert(new InfoProducto(820, 0, 4));
            infoProductoDao.insert(new InfoProducto(1000, 0, 5));
        }

        if (productoDao.count() == 0){
            // Insertar Productos
            productoDao.insert(new Producto("chorizo artesanal", 1, 1, 7000));
            productoDao.insert(new Producto("combo #1(17+1)", 1, 2, 22000));
            productoDao.insert(new Producto("combo #2(29+1)", 1, 3, 34000));
            productoDao.insert(new Producto("combo #3(38+2)", 1, 4, 48000));
            productoDao.insert(new Producto("combo #4(55+2)", 1, 5, 65000));
            productoDao.insert(new Producto("combo #5(65+3)", 1, 6, 80000));
            productoDao.insert(new Producto("chicharron personal", 2, 7, 17000));
            productoDao.insert(new Producto("picada #1(23)", 2, 8, 23000));
            productoDao.insert(new Producto("picada #2(34)", 2, 9, 34000));
            productoDao.insert(new Producto("picada #3(48)", 2, 10, 48000));
            productoDao.insert(new Producto("picada #4(65)", 2, 11, 65000));
            productoDao.insert(new Producto("kilo(80)", 2, 12, 80000));

            // Insertar Bebidas
            productoDao.insert(new Producto("coca-cola 237ml", 3, null, 2000));
            productoDao.insert(new Producto("quatro 237ml", 3, null, 2000));
            productoDao.insert(new Producto("sprite 237ml", 3, null, 2000));
            productoDao.insert(new Producto("postobon-manzana 250ml", 3, null, 2000));
            productoDao.insert(new Producto("postobon-naranja 250ml", 3, null, 2000));
            productoDao.insert(new Producto("postobon-kola 250ml", 3, null, 2000));
            productoDao.insert(new Producto("colombiana 250ml", 3, null, 2000));
            productoDao.insert(new Producto("agua-manzana 280ml", 3, null, 2000));
            productoDao.insert(new Producto("agua 600ml", 3, null, 2000));
            productoDao.insert(new Producto("jugo-hit-mora 500ml", 3, null, 3000));
            productoDao.insert(new Producto("jugo-hit-tropical 500ml", 3, null, 3000));
            productoDao.insert(new Producto("jugo-hit-lulo 500ml", 3, null, 3000));
            productoDao.insert(new Producto("jugo-hit-naranjaPiña 500ml", 3, null, 3000));
            productoDao.insert(new Producto("bretaña 500ml", 3, null, 3000));
            productoDao.insert(new Producto("coca-cola-normal 400ml", 3, null, 3500));
            productoDao.insert(new Producto("coca-cola-zero 400ml", 3, null, 3500));
            productoDao.insert(new Producto("pony-malta 1L", 4, null, 5000));
            productoDao.insert(new Producto("coca-cola 1L", 4, null, 5000));
            productoDao.insert(new Producto("coca-cola 1.5L", 4, null, 7500));
            productoDao.insert(new Producto("coca-cola 2L", 4, null, 7500));
            productoDao.insert(new Producto("costeñita", 5, null, 3000));
            productoDao.insert(new Producto("miller-lite", 5, null, 3000));
            productoDao.insert(new Producto("budweiser", 5, null, 3500));
            productoDao.insert(new Producto("aguila-negra", 5, null, 3500));
            productoDao.insert(new Producto("poker", 5, null, 3500));
            productoDao.insert(new Producto("heineken", 5, null, 4000));
            productoDao.insert(new Producto("coronita", 5, null, 4000));
            productoDao.insert(new Producto("club-colombia", 5, null, 4000));
        }

    }

}
