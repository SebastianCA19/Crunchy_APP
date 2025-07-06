package com.example.crunchy_app.productos.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.productos.model.ValorAtributoProducto;

import java.util.List;
@Dao

public interface ValorAtributoProductoDao {

    // === Inserciones ===

    @Insert
    void insert(ValorAtributoProducto valorAtributoProducto);

    @Insert
    void insertAll(List<ValorAtributoProducto> valorAtributoProductos);

    // === Consultas generales ===

    @Query("SELECT COUNT(*) FROM valores_atributo_producto WHERE activo = 1")
    int count();

    @Query("SELECT * FROM valores_atributo_producto WHERE activo = 1")
    List<ValorAtributoProducto> getValoresAtributoProducto();

    @Query("SELECT * FROM valores_atributo_producto WHERE activo = 1")
    List<ValorAtributoProducto> getAll();

    @Query("SELECT * FROM valores_atributo_producto WHERE id_producto = :idProducto AND activo = 1")
    List<ValorAtributoProducto> getByProductoId(Integer idProducto);

    // === Desactivación (en vez de eliminar) ===

    @Query("UPDATE valores_atributo_producto SET activo = 0 WHERE id_producto = :idProducto")
    void desactivarByProductoId(Integer idProducto);

    // === Consultas por atributo específico (solo activos) ===

    @Query("SELECT * FROM valores_atributo_producto WHERE id_atributo_producto = 1 AND activo = 1 ORDER BY id_producto")
    List<ValorAtributoProducto> getCantidadChorizo(); // Chorizo

    @Query("SELECT * FROM valores_atributo_producto WHERE id_atributo_producto = 2 AND activo = 1 ORDER BY id_producto")
    List<ValorAtributoProducto> getCantidadChicharron(); // Chicharrón

    @Query("SELECT * FROM valores_atributo_producto WHERE id_atributo_producto = 3 AND activo = 1 ORDER BY id_producto")
    List<ValorAtributoProducto> getCantidadBollo(); // Bollo

    @Query("SELECT * FROM valores_atributo_producto WHERE id_atributo_producto = 4 AND activo = 1 ORDER BY id_producto")
    List<ValorAtributoProducto> getVolumenMl(); // Mililitros (para bebidas)

    // === Consultas por producto específico ===

    @Query("SELECT valor_atributo_producto FROM valores_atributo_producto WHERE id_producto = :id AND id_atributo_producto = 1 AND activo = 1")
    int getChorizoValue(int id);

    @Query("SELECT valor_atributo_producto FROM valores_atributo_producto WHERE id_producto = :id AND id_atributo_producto = 2 AND activo = 1")
    float getChicharronValue(int id);

    // === Personalizados ===

    @Query("SELECT * FROM valores_atributo_producto WHERE personalizado_id = :id AND activo = 1")
    ValorAtributoProducto getValorAtributoProductoPersonalizado(int id);
}
