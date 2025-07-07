package com.example.crunchy_app.DBconnection;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.crunchy_app.pedidos.model.Locacion;
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.ValorAtributoProducto;
import com.example.crunchy_app.reportes.model.ResumenPorDia;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class JsonExporter {

    private static final String TAG = "JsonExporter";

    // Obtiene o crea la carpeta /Documents/CrunchyBackup/
    private static File getJsonFile(String fileName) {
        File documentsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File backupFolder = new File(documentsDir, "CrunchyBackup");

        if (!backupFolder.exists() && !backupFolder.mkdirs()) {
            Log.e(TAG, "❌ No se pudo crear la carpeta CrunchyBackup");
            return null;
        }

        return new File(backupFolder, fileName);
    }

    // Exportar productos
    public static void exportProductos(Context context, List<Producto> productos) {
        File file = getJsonFile("productos.json");
        if (file == null) return;

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(productos, writer);
            Log.d(TAG, "✅ Productos exportados a: " + file.getAbsolutePath());
        } catch (IOException e) {
            Log.e(TAG, "❌ Error al exportar productos", e);
        }
    }

    // Importar productos
    public static List<Producto> importProductos(Context context) {
        File file = getJsonFile("productos.json");
        if (file == null || !file.exists()) return Collections.emptyList();

        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            Type listType = new TypeToken<List<Producto>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            Log.e(TAG, "❌ Error al importar productos", e);
            return Collections.emptyList();
        }
    }

    // Exportar locaciones
    public static void exportLocaciones(Context context, List<Locacion> locaciones) {
        File file = getJsonFile("locaciones.json");
        if (file == null) return;

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(locaciones, writer);
            Log.d(TAG, "✅ Locaciones exportadas a: " + file.getAbsolutePath());
        } catch (IOException e) {
            Log.e(TAG, "❌ Error al exportar locaciones", e);
        }
    }

    // Importar locaciones
    public static List<Locacion> importLocaciones(Context context) {
        File file = getJsonFile("locaciones.json");
        if (file == null || !file.exists()) return Collections.emptyList();

        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            Type listType = new TypeToken<List<Locacion>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            Log.e(TAG, "❌ Error al importar locaciones", e);
            return Collections.emptyList();
        }
    }

    // Exportar valores de atributo de producto
    public static void exportValorAtributoProductos(Context context, List<ValorAtributoProducto> valores) {
        File file = getJsonFile("valores_atributo_producto.json");
        if (file == null) return;

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(valores, writer);
            Log.d(TAG, "✅ Valores de atributo exportados a: " + file.getAbsolutePath());
        } catch (IOException e) {
            Log.e(TAG, "❌ Error al exportar valores atributo producto", e);
        }
    }

    // Importar valores de atributo de producto
    public static List<ValorAtributoProducto> importValorAtributoProductos(Context context) {
        File file = getJsonFile("valores_atributo_producto.json");
        if (file == null || !file.exists()) return Collections.emptyList();

        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            Type listType = new TypeToken<List<ValorAtributoProducto>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            Log.e(TAG, "❌ Error al importar valores atributo producto", e);
            return Collections.emptyList();
        }
    }

    // Exportar productos
    public static void exportResumenPorDia(Context context, List<ResumenPorDia> reportes) {
        File file = getJsonFile("reportes.json");
        if (file == null) return;

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(reportes, writer);
            Log.d(TAG, "✅ reportes exportados a: " + file.getAbsolutePath());
        } catch (IOException e) {
            Log.e(TAG, "❌ Error al exportar reportes", e);
        }
    }

    // Importar productos
    public static List<ResumenPorDia> importResumenPorDia(Context context) {
        File file = getJsonFile("reportes.json");
        if (file == null || !file.exists()) return Collections.emptyList();

        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            Type listType = new TypeToken<List<ResumenPorDia>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            Log.e(TAG, "❌ Error al importar reportes", e);
            return Collections.emptyList();
        }
    }
}
