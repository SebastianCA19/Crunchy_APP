package com.example.crunchy_app.productos.DAO;

import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.productos.model.Producto;

import java.util.List;

public interface AtributoProductoDao {
    @Query("SELECT * FROM productos")
    List<Producto> getProductos();
    @Query("SELECT * FROM productos WHERE id_producto = :id")
    Producto getProductoById(int id);
    @Insert
    void insertProducto(Producto producto);


}
