package com.example.crunchy_app.pedidos.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.pedidos.model.ProductoDelPedido;

import java.util.List;

@Dao
public interface ProductoDelPedidoDao {

    @Query("SELECT count(*) FROM productos_del_pedido")
    public int count();

    @Query("SELECT * FROM productos_del_pedido")
    public List<ProductoDelPedido> getAll();

    @Query("SELECT * FROM productos_del_pedido WHERE id_pedido = :idPedido")
    public List<ProductoDelPedido> getProductosByPedido(Integer idPedido);

    @Insert
    public long insert(ProductoDelPedido productoDelPedido);

    @Query("DELETE FROM productos_del_pedido WHERE id_pedido = :idPedido")
    public int deleteProductosByPedido(Integer idPedido);

    @Query("DELETE FROM productos_del_pedido WHERE id_pedido = :idPedido AND id_producto = :idProducto")
    public int deleteProductosByProducto(Integer idPedido, Integer idProducto);

   //funcion para eliminar todos los productos de un pedido
    @Query("DELETE FROM productos_del_pedido WHERE id_pedido = :idPedido")
    public void eliminarPorPedido(String idPedido);
}
