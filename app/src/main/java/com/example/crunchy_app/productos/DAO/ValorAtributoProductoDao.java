package com.example.crunchy_app.productos.DAO;

import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.productos.model.ValorAtributoProducto;

import java.util.List;

public interface ValorAtributoProductoDao {

    @Query("SELECT * FROM valores_atributo_producto")
    List<ValorAtributoProducto> getValoresAtributoProducto();

    @Query("SELECT * FROM valores_atributo_producto WHERE id_valor_atributo_producto = :id")
    ValorAtributoProducto getValorAtributoProductoById(int id);

    @Insert
    void insertValorAtributoProducto(ValorAtributoProducto valorAtributoProducto);




}
