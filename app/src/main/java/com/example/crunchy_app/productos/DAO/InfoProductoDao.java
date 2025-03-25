package com.example.crunchy_app.productos.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.productos.model.InfoProducto;

import java.util.List;

@Dao
public interface InfoProductoDao {

    @Query("SELECT * FROM info_productos")
    List<InfoProducto> getAll();

    @Insert
    long insert(InfoProducto infoProducto);

}
