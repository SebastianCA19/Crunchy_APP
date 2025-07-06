package com.example.crunchy_app;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.DBconnection.JsonExporter;
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

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11+
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent); // Esto lleva al usuario a dar acceso total al almacenamiento
                // Mostrar aviso al usuario
                runOnUiThread(() -> Toast.makeText(this,
                        "La app necesita acceso total a archivos para guardar y restaurar los datos.",
                        Toast.LENGTH_LONG).show());
            }
        } else {
            // Android 6 a Android 10
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        1001
                );
            }
        }

        CountDownLatch latch = new CountDownLatch(1);

        // Manejo de base de datos en un hilo separado
        new Thread(() -> {
            AppDataBase db = AppDataBase.getInstance(getApplicationContext());

            // ✅ Verificar si todas las tablas importantes están completamente vacías
            boolean datosCriticosVacios =
                    db.estadoPedidoDao().count() == 0 &&
                            db.metodoPagoDao().count() == 0 &&
                            db.tipoProductoDao().count() == 0 &&
                            db.valorAtributoProductoDao().count() == 0 &&
                            db.productoDao().count() == 0 &&
                            db.locacionDao().count() == 0 &&
                            db.atributoProductoDao().count() == 0;

            if (datosCriticosVacios) {

                Log.d("CARGA DATOS", "Todas las tablas están vacías. Inicializando...");

                // 🔥 Eliminar la base de datos para reiniciar desde cero
                AppDataBase.resetInstance(); // ✅ Cierra y limpia la instancia
                getApplicationContext().deleteDatabase("crunchy-DB"); // ✅ Borra la base de datos física

                // ✅ Crear nueva instancia limpia
                AppDataBase nuevaDb = AppDataBase.getInstance(getApplicationContext());

                // 📂 Archivos JSON externos
                File prodFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/CrunchyBackup", "productos.json");
                File locFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/CrunchyBackup", "locaciones.json");
                File valAttrFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/CrunchyBackup", "valores_atributo_producto.json");

                // 🧠 Si los archivos existen → importar desde ellos
                if (prodFile.exists() && locFile.exists() && valAttrFile.exists()) {
                    Log.d("CARGA DATOS", "Importando desde archivos JSON externos...");

                    insertarDatosBasicos(nuevaDb);

                    nuevaDb.productoDao().insertAll(JsonExporter.importProductos(getApplicationContext()));
                    nuevaDb.locacionDao().insertAll(JsonExporter.importLocaciones(getApplicationContext()));
                    nuevaDb.valorAtributoProductoDao().insertAll(JsonExporter.importValorAtributoProductos(getApplicationContext()));
                } else {
                    Log.d("CARGA DATOS", "No se encontraron JSONs. Usando datos hardcoded...");

                    insertarDatosIniciales(nuevaDb);

                    // Exportar a JSON
                    JsonExporter.exportProductos(getApplicationContext(), nuevaDb.productoDao().getAll());
                    JsonExporter.exportLocaciones(getApplicationContext(), nuevaDb.locacionDao().getAll());
                    JsonExporter.exportValorAtributoProductos(getApplicationContext(), nuevaDb.valorAtributoProductoDao().getAll());

                    Log.d("EXPORTACIÓN", "Datos exportados exitosamente tras carga hardcoded.");
                }

            } else {
                Log.d("CARGA DATOS", "La base de datos ya contiene información. No se requiere reinicialización.");
            }

            latch.countDown();
        }).start();

        try {
            latch.await(); // Espera que termine el hilo de carga
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1001) {
            boolean allGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }

            if (!allGranted) {
                Toast.makeText(this, "Se necesitan permisos de almacenamiento para guardar los datos", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void goToHome(View v){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private static void insertarDatosBasicos(AppDataBase db){
        EstadoPedidoDao estadoPedidoDao = db.estadoPedidoDao();
        MetodoPagoDao metodoPagoDao = db.metodoPagoDao();

        TipoProductoDao tipoProductoDao = db.tipoProductoDao();
        AtributoProductoDao atributoProductoDao =  db.atributoProductoDao();


        // Insertar Estados de Pedido
        if(estadoPedidoDao.count() == 0){
            estadoPedidoDao.insert(new EstadoPedido("encargado"));
            estadoPedidoDao.insert(new EstadoPedido("entregado"));
            estadoPedidoDao.insert(new EstadoPedido("entregado + pagado"));
        }

        // Insertar Métodos de Pago
        if(metodoPagoDao.count() == 0){
            metodoPagoDao.insert(new MetodoPago("efectivo"));
            metodoPagoDao.insert(new MetodoPago("transferencia"));
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
        estadoPedidoDao.insert(new EstadoPedido("encargado"));
        estadoPedidoDao.insert(new EstadoPedido("entregado"));
        estadoPedidoDao.insert(new EstadoPedido("entregado + pagado"));

        //Insertar Locaciones
        if(locacionDao.count() == 0){
            // los de 4k
            locacionDao.insert(new Locacion("Juan 23", null, 4000));
            locacionDao.insert(new Locacion("Olivos", null, 4000));
            // los de 5k
            locacionDao.insert(new Locacion("Jardin", null, 5000));
            locacionDao.insert(new Locacion("RCN", null, 5000));
            locacionDao.insert(new Locacion("Balcones del L.", null, 5000));
            locacionDao.insert(new Locacion("Riasco", null, 5000));
            locacionDao.insert(new Locacion("Ensencida J 23", null, 5000));
            locacionDao.insert(new Locacion("San Francisco", null, 5000));
            locacionDao.insert(new Locacion("Fresseniuss", null, 5000));
            locacionDao.insert(new Locacion("7 de Agosto", null, 5000));
            locacionDao.insert(new Locacion("Tayrona City", null, 5000));
            locacionDao.insert(new Locacion("Alto Vento", null, 5000));
            //los de 6k
            locacionDao.insert(new Locacion("Almendros", null, 6000));
            locacionDao.insert(new Locacion("20 Julio", null, 6000));
            locacionDao.insert(new Locacion("Postobon", null, 6000));
            locacionDao.insert(new Locacion("Alcazares", null, 6000));
            locacionDao.insert(new Locacion("Centro", null, 6000));
            locacionDao.insert(new Locacion("Pescaito", null, 6000));
            locacionDao.insert(new Locacion("Estatua El Pibe", null, 6000));
            locacionDao.insert(new Locacion("Naranjos", null, 6000));
            locacionDao.insert(new Locacion("Miraflores", null, 6000));
            locacionDao.insert(new Locacion("San Fernando", null, 6000));
            locacionDao.insert(new Locacion("Malvinas", null, 6000));
            locacionDao.insert(new Locacion("Santa Catalina", null, 6000));
            locacionDao.insert(new Locacion("El Reposo", null, 6000));
            locacionDao.insert(new Locacion("Palma", null, 6000));
            locacionDao.insert(new Locacion("Trevol", null, 6000));
            locacionDao.insert(new Locacion("Ed. Torre", null, 6000));
            locacionDao.insert(new Locacion("Ed. Santa Ana", null, 6000));
            locacionDao.insert(new Locacion("Clinica Salud", null, 6000));
            locacionDao.insert(new Locacion("Porvenir", null, 6000));
            locacionDao.insert(new Locacion("El Mayor", null, 6000));
            locacionDao.insert(new Locacion("1 de Mayor", null, 6000));
            locacionDao.insert(new Locacion("Cun", null, 6000));
            locacionDao.insert(new Locacion("ocean Mall", null, 6000));
            locacionDao.insert(new Locacion("8 de Febrero", null, 6000));
            locacionDao.insert(new Locacion("Nacho Vives", null, 6000));
            locacionDao.insert(new Locacion("Altos Santa Rita", null, 6000));
            locacionDao.insert(new Locacion("Boulevard Las Rosas", null, 6000));
            locacionDao.insert(new Locacion("Villa Sara", null, 6000));
            // los de 7k
            locacionDao.insert(new Locacion("Libano", null, 7000));
            locacionDao.insert(new Locacion("Pando", null, 7000));
            locacionDao.insert(new Locacion("Santa Cruz", null, 7000));
            locacionDao.insert(new Locacion("Santa Elena", null, 7000));
            locacionDao.insert(new Locacion("Bastidas", null, 7000));
            locacionDao.insert(new Locacion("Bavaria", null, 7000));
            locacionDao.insert(new Locacion("Galicia", null, 7000));
            locacionDao.insert(new Locacion("Tejares", null, 7000));
            locacionDao.insert(new Locacion("Andrea Carolina", null, 7000));
            locacionDao.insert(new Locacion("Concepcion", null, 7000));
            locacionDao.insert(new Locacion("Parque", null, 7000));
            locacionDao.insert(new Locacion("Manzanares", null, 7000));
            locacionDao.insert(new Locacion("Ciudadela", null, 7000));
            locacionDao.insert(new Locacion("La U", null, 7000));
            locacionDao.insert(new Locacion("Laureles", null, 7000));
            locacionDao.insert(new Locacion("V. Marbella", null, 7000));
            locacionDao.insert(new Locacion("V. Cacho", null, 7000));
            locacionDao.insert(new Locacion("San Jorge", null, 7000));
            locacionDao.insert(new Locacion("Americas", null, 7000));
            locacionDao.insert(new Locacion("Santana", null, 7000));
            locacionDao.insert(new Locacion("Puerto", null, 7000));
            locacionDao.insert(new Locacion("C. Campestre", null, 7000));
            locacionDao.insert(new Locacion("Nogal", null, 7000));
            locacionDao.insert(new Locacion("Paraiso", null, 7000));
            locacionDao.insert(new Locacion("San Pedro", null, 7000));
            locacionDao.insert(new Locacion("Boston", null, 7000));
            locacionDao.insert(new Locacion("Sierra Dentro", null, 7000));
            locacionDao.insert(new Locacion("Tayrona", null, 7000));
            locacionDao.insert(new Locacion("P. Tesoro", null, 7000));
            locacionDao.insert(new Locacion("Ciudad del Sol", null, 7000));
            locacionDao.insert(new Locacion("E. Tayrona", null, 7000));
            locacionDao.insert(new Locacion("C. Sierra Mar", null, 7000));
            locacionDao.insert(new Locacion("V. Bella", null, 7000));
            locacionDao.insert(new Locacion("Taminaka", null, 7000));
            locacionDao.insert(new Locacion("Mamatoco", null, 7000));
            locacionDao.insert(new Locacion("Divino Niño", null, 7000));
            locacionDao.insert(new Locacion("17 de Diciembre", null, 7000));
            locacionDao.insert(new Locacion("Chimila", null, 7000));
            locacionDao.insert(new Locacion("Luz del Mundo", null, 7000));
            //los de 8k
            locacionDao.insert(new Locacion("T. de Venecia", null, 8000));
            locacionDao.insert(new Locacion("R. de Curinca", null, 8000));
            //a los de 12k
            locacionDao.insert(new Locacion("Cisne", null, 12000));
            locacionDao.insert(new Locacion("Gaira", null, 12000));
            locacionDao.insert(new Locacion("yuca", null, 12000));
            locacionDao.insert(new Locacion("P. de Bolivar", null, 12000));
            locacionDao.insert(new Locacion("V. Mercedes", null, 12000));
            locacionDao.insert(new Locacion("Rodadero", null, 12000));
            // los de 15k
            locacionDao.insert(new Locacion("Taganga", null, 15000));
            locacionDao.insert(new Locacion("V. Concha", null, 15000));

        }

        // Insertar Métodos de Pago
        if(metodoPagoDao.count() == 0){
            metodoPagoDao.insert(new MetodoPago("efectivo"));
            metodoPagoDao.insert(new MetodoPago("transferencia"));
        }

        // Insertar Tipos de Producto
        if (tipoProductoDao.count() == 0){
            tipoProductoDao.insert(new TipoProducto("combo")); // id 1
            tipoProductoDao.insert(new TipoProducto("picada")); // id 2
            tipoProductoDao.insert(new TipoProducto("bebida-personal")); // id 3
            tipoProductoDao.insert(new TipoProducto("bebida-familiar")); // id 4
            tipoProductoDao.insert(new TipoProducto("bebida-alcoholica"));// id 5
            tipoProductoDao.insert(new TipoProducto("personalizado")); // id 6
        }

        //Insertar Atributos de Producto
        if (atributoProductoDao.count() == 0){
            atributoProductoDao.insertAtributoProducto(new AtributoProducto("chicharrón")); // id atributoProducto 1
            atributoProductoDao.insertAtributoProducto(new AtributoProducto("chorizo"));// id atributoProducto 2
            atributoProductoDao.insertAtributoProducto(new AtributoProducto("bollo"));// id atributoProducto 3
            atributoProductoDao.insertAtributoProducto(new AtributoProducto("volumen"));// id atributoProducto 4
        }

        // Insertar Productos
        if (productoDao.count() == 0){
            productoDao.insert(new Producto("chorizo artesanal", 1,  7000)); //id 1
            productoDao.insert(new Producto("combo #1(17+1)", 1, 22000)); //id 2
            productoDao.insert(new Producto("combo #2(29+1)", 1, 34000)); //id 3
            productoDao.insert(new Producto("combo #3(38+2)", 1,  48000)); //id 4
            productoDao.insert(new Producto("combo #4(55+2)", 1, 65000)); //id 5
            productoDao.insert(new Producto("combo #5(65+3)", 1, 80000)); //id 6
            productoDao.insert(new Producto("chicharron personal", 2,  17000)); //id 7
            productoDao.insert(new Producto("picada #1(23)", 2, 23000)); //id 8
            productoDao.insert(new Producto("picada #2(34)", 2, 34000)); //id 9
            productoDao.insert(new Producto("picada #3(48)", 2, 48000)); //id 10
            productoDao.insert(new Producto("picada #4(65)", 2,65000)); //id 11
            productoDao.insert(new Producto("kilo(80)", 2, 80000)); //id 12

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

            //Personalizados (idProducto 41-42-43)
            productoDao.insert(new Producto("chicharron personalizado", 6, 0)); //id 41
            productoDao.insert(new Producto("chorizo personalizado", 6, 5000));    //id 42
            productoDao.insert(new Producto("bollo personalizado", 6, 3500));      //id 43
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
