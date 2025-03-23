package com.example.crunchy_app.connection;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getReadableDatabase();
    }

    public void mostrarTiposDePedido() {
        Cursor cursor = database.rawQuery("SELECT * FROM tipos_de_pedido", null);

        if (cursor.moveToFirst()) {
            do {
                int idTipoPedido = cursor.getInt(0);
                String nombreTipoPedido = cursor.getString(1);
                Log.d("DB_TIPOS_PEDIDO", "ID: " + idTipoPedido + ", Nombre: " + nombreTipoPedido);
            } while (cursor.moveToNext());
        } else {
            Log.d("DB_TIPOS_PEDIDO", "No hay datos en la tabla tipos_de_pedido");
        }

        cursor.close();
    }

    public void ingresarTipoDePedido(String nombreTipoPedido) {
        database.execSQL("INSERT INTO tipos_de_pedido (nombre_tipo_pedido) VALUES ('" + nombreTipoPedido + "')");
    }
}

