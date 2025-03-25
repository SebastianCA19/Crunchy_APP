package com.example.crunchy_app.productos.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.productos.model.TipoProducto;

import java.util.List;

@Dao
public interface TipoProductoDao {

    @Query("SELECT * FROM tipos_producto")
    List<TipoProducto> getAll();

    @Insert
    long insert(TipoProducto tipoProducto);
}
