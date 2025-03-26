package com.example.crunchy_app.pedidos.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.pedidos.model.ProductoDelPedido;

import java.util.List;

@Dao
public interface ProductoDelPedidoDao {

    @Query("SELECT * FROM productos_del_pedido")
    public List<ProductoDelPedido> getAll();

    @Query("SELECT * FROM productos_del_pedido WHERE id_pedido = :idPedido")
    public List<ProductoDelPedido> getProductosByPedido(int idPedido);

    @Insert
    public long insert(ProductoDelPedido productoDelPedido);

    @Query("DELETE FROM productos_del_pedido WHERE id_pedido = :idPedido")
    public int deleteProductosByPedido(int idPedido);

    @Query("DELETE FROM productos_del_pedido WHERE id_pedido = :idPedido AND id_producto = :idProducto")
    public int deleteProductosByProducto(int idPedido, int idProducto);
}
