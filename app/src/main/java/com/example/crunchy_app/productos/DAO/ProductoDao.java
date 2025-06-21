package com.example.crunchy_app.productos.DAO;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.crunchy_app.productos.model.Producto;

import java.util.List;

@Dao
public interface ProductoDao {

    @Query("SELECT count(*) FROM productos")
    public int count();

    @Query("SELECT * FROM productos")
    public List<Producto> getAll();

    @Query("SELECT * FROM productos WHERE id_producto = :idProducto")
    public Producto getProductoById(Integer idProducto);

    @Insert
    public long insert(Producto producto);

    @Query("DELETE FROM productos WHERE id_producto = :idProducto")
    public int deleteProductoById(Integer idProducto);

    @Query("SELECT * FROM productos WHERE id_tipo_producto = 1 OR id_tipo_producto = 2")
    public List<Producto> getComidas();

    @Query("SELECT * FROM productos WHERE id_tipo_producto IN (3, 4, 5)")
    public List<Producto> getBebidas();

    @Query("SELECT * FROM productos WHERE nombre_producto LIKE :query || '%' AND (id_tipo_producto = 1 OR id_tipo_producto = 2)")
    public List<Producto> searchComidas(String query);

    @Query("SELECT * FROM productos WHERE nombre_producto LIKE :query || '%' AND id_tipo_producto IN (3, 4, 5)")
    public List<Producto> searchBebidas(String query);


    @Update
    public void update(Producto producto);

}
