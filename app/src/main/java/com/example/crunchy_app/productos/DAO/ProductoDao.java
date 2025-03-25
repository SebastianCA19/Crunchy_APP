package com.example.crunchy_app.productos.DAO;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.productos.model.Producto;

import java.util.List;

@Dao
public interface ProductoDao {

    @Query("SELECT * FROM productos")
    List<Producto> getAll();

    @Insert
    long insert(Producto producto);
}
