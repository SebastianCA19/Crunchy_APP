package com.example.crunchy_app.productos.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.productos.model.InfoProducto;

import java.util.List;

@Dao
public interface InfoProductoDao {

    @Query("SELECT * FROM infos_producto")
    public List<InfoProducto> getAll();

    @Query("SELECT * FROM infos_producto WHERE id_info_producto = :idInfoProducto")
    public InfoProducto getInfoProductoById(Integer idInfoProducto);

    @Insert
    public long insert(InfoProducto infoProducto);

    @Query("DELETE FROM infos_producto WHERE id_info_producto = :idInfoProducto")
    public int deleteInfoProductoById(Integer idInfoProducto);

}
