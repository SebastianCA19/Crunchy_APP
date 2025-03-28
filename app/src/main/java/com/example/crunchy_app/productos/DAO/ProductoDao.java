package com.example.crunchy_app.productos.DAO;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.productos.model.Producto;

import java.util.List;

@Dao
public interface ProductoDao {

    @Query("SELECT * FROM productos")
    public List<Producto> getAll();

    @Query("SELECT * FROM productos WHERE id_producto = :idProducto")
    public Producto getProductoById(Integer idProducto);

    @Insert
    public long insert(Producto producto);

    @Query("DELETE FROM productos WHERE id_producto = :idProducto")
    public int deleteProductoById(Integer idProducto);
}
