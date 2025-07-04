package com.example.crunchy_app.productos.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.productos.model.ValorAtributoProducto;

import java.util.List;
@Dao

public interface ValorAtributoProductoDao {

    @Query("SELECT COUNT(*) FROM valores_atributo_producto")
    int count();

    @Query("SELECT * FROM valores_atributo_producto")
    List<ValorAtributoProducto> getValoresAtributoProducto();

    @Query("SELECT * FROM valores_atributo_producto WHERE personalizado_id = :id")
    ValorAtributoProducto getValorAtributoProductoPersonalizado(int id);

    @Insert
    void insert(ValorAtributoProducto valorAtributoProducto);

    //Get chicharron values per food
    @Query("SELECT * FROM valores_atributo_producto WHERE id_atributo_producto = 2 ORDER BY id_producto")
    List<ValorAtributoProducto> getCantidadChicharron();

    //Get chorizo values per food
    @Query("SELECT * FROM valores_atributo_producto WHERE id_atributo_producto = 1  ORDER BY id_producto")
    List<ValorAtributoProducto> getCantidadChorizo();

    //Get bollo values per food
    @Query("SELECT * FROM valores_atributo_producto WHERE id_atributo_producto = 3 ORDER BY id_producto")
    List<ValorAtributoProducto> getCantidadBollo();

    //Get ml values per drink
    @Query("SELECT * FROM valores_atributo_producto WHERE id_atributo_producto = 4 ORDER BY id_producto")
    List<ValorAtributoProducto> getVolumenMl();

    //Get chorizo value of a product
    @Query("SELECT valor_atributo_producto FROM valores_atributo_producto WHERE id_producto = :id AND id_atributo_producto = 1")
    int getChorizoValue(int id);

    //Get chicharron value of a product
    @Query("SELECT valor_atributo_producto FROM valores_atributo_producto WHERE id_producto = :id AND id_atributo_producto = 2")
    float getChicharronValue(int id);

    @Insert
    void insertAll(List<ValorAtributoProducto> valorAtributoProductos);

    @Query("SELECT * FROM valores_atributo_producto")
    List<ValorAtributoProducto> getAll();
}
