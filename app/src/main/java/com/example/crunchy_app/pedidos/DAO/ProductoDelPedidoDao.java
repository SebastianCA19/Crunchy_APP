package com.example.crunchy_app.pedidos.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.crunchy_app.pedidos.model.ProductoDelPedido;

import java.util.List;

@Dao
public interface ProductoDelPedidoDao {

    @Query("SELECT count(*) FROM productos_del_pedido")
    public int count();

    @Query("SELECT * FROM productos_del_pedido")
    public List<ProductoDelPedido> getAll();

    @Query("SELECT * FROM productos_del_pedido WHERE id_pedido IN (SELECT id_pedido FROM pedidos WHERE fecha = :fecha)")
    List<ProductoDelPedido> getAllByFecha(String fecha);

    @Query("SELECT * FROM productos_del_pedido WHERE id_pedido = :idPedido")
    public List<ProductoDelPedido> getProductosByPedido(Integer idPedido);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProductoDelPedido productoDelPedido);

    @Query("DELETE FROM productos_del_pedido WHERE id_pedido = :idPedido")
    public int deleteProductosByPedido(Integer idPedido);

    @Query("DELETE FROM productos_del_pedido WHERE id_pedido = :idPedido AND id_producto = :idProducto")
    public int deleteProductosByProducto(Integer idPedido, Integer idProducto);

   //funcion para eliminar todos los productos de un pedido
    @Query("DELETE FROM productos_del_pedido WHERE id_pedido = :idPedido")
    public void eliminarPorPedido(String idPedido);
    @Query("SELECT * FROM productos_del_pedido WHERE id_pedido = :pedidoId")
    List<ProductoDelPedido> getProductosDelPedido(int pedidoId);

    @Query("SELECT pdp.* " +
            "FROM productos_del_pedido pdp " +
            "JOIN pedidos p ON pdp.id_pedido = p.id_pedido " +
            "WHERE p.fecha = :fecha")
    List<ProductoDelPedido> getProductosDelPedidoPorFecha(String fecha);

}
