package com.example.crunchy_app.productos.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.productos.model.AtributoProducto;
import com.example.crunchy_app.productos.model.Producto;

import java.util.List;
@Dao
public interface AtributoProductoDao {
    @Query("SELECT * FROM atributos_producto")
    List<AtributoProducto> getProductos();
    @Query("SELECT * FROM atributos_producto WHERE id_atributo_producto = :id")
    AtributoProducto getAtributoProducto(int id);
    @Insert
    void insertAtributoProducto(AtributoProducto atributoProducto);

    @Query("SELECT COUNT(*) FROM atributos_producto")
    int count();
}
