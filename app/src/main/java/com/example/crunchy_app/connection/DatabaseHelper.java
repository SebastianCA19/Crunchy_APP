package com.example.crunchy_app.connection;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "crunchy.db"; // Nombre del archivo en assets
    private static final int DATABASE_VERSION = 1; // Cambiar este número si hay cambios estructurales
    private static String DATABASE_PATH = "/data/data/com.example.crunchy_app/databases/"; // Ruta donde se copiará la BD

    private final Context mContext;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;

        // Definir la ruta donde se guardará la base de datos en el sistema de archivos de la app
        DATABASE_PATH = context.getApplicationInfo().dataDir + "/databases/";
        inicializarBaseDeDatos();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // No se ejecuta porque usamos una base de datos preexistente
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí puedes manejar actualizaciones sin perder datos
        Log.d("Database", "Actualizando base de datos de versión " + oldVersion + " a " + newVersion);
    }

    // Método para abrir la base de datos
    public void openDatabase() {
        String dbPath = DATABASE_PATH + DATABASE_NAME;
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    // Método para cerrar la base de datos
    @Override
    public synchronized void close() {
        if (mDatabase != null) {
            mDatabase.close();
        }
        super.close();
    }

    // Método para copiar la base de datos desde assets si no existe
    public void copiarBaseDeDatos() {
        String dbPath = DATABASE_PATH + DATABASE_NAME;
        File dbFile = new File(dbPath);

        // Si la base de datos ya existe, no hacemos nada
        if (dbFile.exists()) {
            Log.d("Database", "La base de datos ya existe, no se copia nuevamente.");
            return;
        }

        try {
            File databaseFolder = new File(DATABASE_PATH);
            if (!databaseFolder.exists()) {
                databaseFolder.mkdirs(); // Crear la carpeta si no existe
            }

            InputStream inputStream = mContext.getAssets().open(DATABASE_NAME);
            OutputStream outputStream = new FileOutputStream(dbPath);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();

            Log.d("Database", "Base de datos copiada correctamente desde assets.");

        } catch (IOException e) {
            Log.e("Database", "Error copiando la base de datos: " + e.getMessage());
        }
    }

    // Verifica si la base de datos ya está en el almacenamiento de la app
    private boolean checkDatabase() {
        String dbPath = DATABASE_PATH + DATABASE_NAME;
        File dbFile = new File(dbPath);
        return dbFile.exists();
    }

    // Método para inicializar la base de datos en la primera ejecución
    private void inicializarBaseDeDatos() {
        File dbFile = new File(DATABASE_PATH + DATABASE_NAME);
        if (!dbFile.exists()) {
            Log.d("DB_COPY", "Base de datos no existe, copiando...");
            copiarBaseDeDatos();
        } else {
            Log.d("DB_COPY", "Base de datos ya existe.");
            Log.d("DB_COPY", dbFile.getPath());
        }
    }

}
