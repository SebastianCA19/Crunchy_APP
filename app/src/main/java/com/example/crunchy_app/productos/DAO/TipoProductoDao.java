package com.example.crunchy_app.productos.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.productos.model.TipoProducto;

import java.util.List;

@Dao
public interface TipoProductoDao {

    @Query("SELECT count(*) FROM tipos_producto")
    public int count();

    @Query("SELECT * FROM tipos_producto")
    public List<TipoProducto> getAll();

    @Query("SELECT * FROM tipos_producto WHERE id_tipo_producto = :idTipoProducto")
    public TipoProducto getTipoProductoById(Integer idTipoProducto);

    @Insert
    public long insert(TipoProducto tipoProducto);

    @Query("DELETE FROM tipos_producto WHERE id_tipo_producto = :idTipoProducto")
    public int deleteTipoProductoById(Integer idTipoProducto);
}
