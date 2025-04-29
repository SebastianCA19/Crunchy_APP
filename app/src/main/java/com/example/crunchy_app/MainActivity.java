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

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.pagos.DAO.MetodoPagoDao;
import com.example.crunchy_app.pagos.model.MetodoPago;
import com.example.crunchy_app.pedidos.DAO.EstadoPedidoDao;
import com.example.crunchy_app.pedidos.DAO.LocacionDao;
import com.example.crunchy_app.pedidos.model.EstadoPedido;
import com.example.crunchy_app.pedidos.model.Locacion;
import com.example.crunchy_app.productos.DAO.AtributoProductoDao;
import com.example.crunchy_app.productos.DAO.ProductoDao;
import com.example.crunchy_app.productos.DAO.TipoProductoDao;

import com.example.crunchy_app.productos.DAO.ValorAtributoProductoDao;
import com.example.crunchy_app.productos.model.AtributoProducto;
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.TipoProducto;
import com.example.crunchy_app.productos.model.ValorAtributoProducto;
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
                    db.tipoProductoDao().count() == 0 || db.valorAtributoProductoDao().count() == 0 ||
                    db.productoDao().count() == 0 || db.locacionDao().count() == 0 || db.atributoProductoDao().count() == 0){
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
        ValorAtributoProductoDao valoresAtributoProductoDao = db.valorAtributoProductoDao();
        AtributoProductoDao atributoProductoDao =  db.atributoProductoDao();
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

        //Insertar Atributos de Producto
        if (atributoProductoDao.count() == 0){
            atributoProductoDao.insertAtributoProducto(new AtributoProducto("chicharrón"));
            atributoProductoDao.insertAtributoProducto(new AtributoProducto("chorizo"));
            atributoProductoDao.insertAtributoProducto(new AtributoProducto("bollo"));
            atributoProductoDao.insertAtributoProducto(new AtributoProducto("volumen"));
        }

        // Insertar Productos
        if (productoDao.count() == 0){
            productoDao.insert(new Producto("chorizo artesanal", 1,  7000));
            productoDao.insert(new Producto("combo #1(17+1)", 1, 22000));
            productoDao.insert(new Producto("combo #2(29+1)", 1, 34000));
            productoDao.insert(new Producto("combo #3(38+2)", 1,  48000));
            productoDao.insert(new Producto("combo #4(55+2)", 1, 65000));
            productoDao.insert(new Producto("combo #5(65+3)", 1, 80000));
            productoDao.insert(new Producto("chicharron personal", 2,  17000));
            productoDao.insert(new Producto("picada #1(23)", 2, 23000));
            productoDao.insert(new Producto("picada #2(34)", 2, 34000));
            productoDao.insert(new Producto("picada #3(48)", 2, 48000));
            productoDao.insert(new Producto("picada #4(65)", 2,65000));
            productoDao.insert(new Producto("kilo(80)", 2, 80000));

            // Insertar Bebidas
            // Bebidas pequeñas (idProducto 13-28)
            productoDao.insert(new Producto("coca-cola", 3, 2000));          // id 13
            productoDao.insert(new Producto("quatro", 3, 2000));             // id 14
            productoDao.insert(new Producto("sprite", 3, 2000));             // id 15
            productoDao.insert(new Producto("postobon-manzana", 3, 2000));   // id 16
            productoDao.insert(new Producto("postobon-naranja", 3, 2000));   // id 17
            productoDao.insert(new Producto("postobon-kola", 3, 2000));      // id 18
            productoDao.insert(new Producto("colombiana", 3, 2000));         // id 19
            productoDao.insert(new Producto("agua-manzana", 3, 2000));       // id 20
            productoDao.insert(new Producto("agua", 3, 2000));               // id 21
            productoDao.insert(new Producto("jugo-hit-mora", 3, 3000));      // id 22
            productoDao.insert(new Producto("jugo-hit-tropical", 3, 3000));  // id 23
            productoDao.insert(new Producto("jugo-hit-lulo", 3, 3000));      // id 24
            productoDao.insert(new Producto("jugo-hit-naranjaPiña", 3, 3000)); // id 25
            productoDao.insert(new Producto("bretaña", 3, 3000));            // id 26
            productoDao.insert(new Producto("coca-cola-normal", 3, 3500));   // id 27
            productoDao.insert(new Producto("coca-cola-zero", 3, 3500));     // id 28

            // Bebidas grandes (idProducto 29-32)
            productoDao.insert(new Producto("pony-malta", 4, 5000));         // id 29
            productoDao.insert(new Producto("coca-cola", 4, 5000));          // id 30
            productoDao.insert(new Producto("coca-cola", 4, 7500));          // id 31
            productoDao.insert(new Producto("coca-cola", 4, 7500));          // id 32

            // Cervezas (idProducto 33-40)
            productoDao.insert(new Producto("costeñita", 5, 3000));          // id 33
            productoDao.insert(new Producto("miller-lite", 5, 3000));        // id 34
            productoDao.insert(new Producto("budweiser", 5, 3500));          // id 35
            productoDao.insert(new Producto("aguila-negra", 5, 3500));       // id 36
            productoDao.insert(new Producto("poker", 5, 3500));              // id 37
            productoDao.insert(new Producto("heineken", 5, 4000));            // id 38
            productoDao.insert(new Producto("coronita", 5, 4000));            // id 39
            productoDao.insert(new Producto("club-colombia", 5, 4000));       // id 40
        }

        // Insertar Valores de Atributos
        if (valoresAtributoProductoDao.count() == 0){
            //Atributos de las comidas
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(1, 1, 1));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(1, 3, 1));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(2, 2, 215));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(2, 1, 1));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(2, 3, 1));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(3, 2, 360));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(3, 1, 1));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(3, 3, 1));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(4, 2, 480));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(4, 1, 2));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(4, 3, 1));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(5, 2, 695));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(5, 1, 2));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(5, 3, 1));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(6, 2, 820));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(6, 1, 3));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(6, 3,1));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(7, 2, 215));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(7, 3, 1));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(8, 2, 290));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(8, 3, 1));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(9, 2, 430));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(9, 3, 1));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(10, 2, 605));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(10, 3, 1));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(11, 2, 820));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(11, 3, 1));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(12, 2, 1000));
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(12, 3, 1));

            //Atributos de las bebidas
            // Bebidas pequeñas (ml)
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(13, 4, 237));   // coca-cola 237ml
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(14, 4, 237));   // quatro 237ml
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(15, 4, 237));   // sprite 237ml
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(16, 4, 250));   // postobon-manzana 250ml
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(17, 4, 250));   // postobon-naranja 250ml
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(18, 4, 250));   // postobon-kola 250ml
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(19, 4, 250));   // colombiana 250ml
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(20, 4, 280));   // agua-manzana 280ml
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(21, 4, 600));   // agua 600ml
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(22, 4, 500));   // jugo-hit-mora 500ml
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(23, 4, 500));   // jugo-hit-tropical 500ml
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(24, 4, 500));   // jugo-hit-lulo 500ml
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(25, 4, 500));   // jugo-hit-naranjaPiña 500ml
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(26, 4, 500));   // bretaña 500ml
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(27, 4, 400));   // coca-cola-normal 400ml
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(28, 4, 400));   // coca-cola-zero 400ml

            // Bebidas grandes (ml)
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(29, 4, 1000));  // pony-malta 1L (1000ml)
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(30, 4, 1000));  // coca-cola 1L
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(31, 4, 1500));  // coca-cola 1.5L
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(32, 4, 2000));  // coca-cola 2L

            // Cervezas (standard 330ml unless specified otherwise)
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(33, 4, 330));   // costeñita
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(34, 4, 330));   // miller-lite
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(35, 4, 330));   // budweiser
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(36, 4, 330));   // aguila-negra
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(37, 4, 330));   // poker
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(38, 4, 330));   // heineken
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(39, 4, 330));   // coronita
            valoresAtributoProductoDao.insert(new ValorAtributoProducto(40, 4, 330));   // club-colombia
        }

    }

}
