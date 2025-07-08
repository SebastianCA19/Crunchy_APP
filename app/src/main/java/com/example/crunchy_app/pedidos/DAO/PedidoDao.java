package com.example.crunchy_app.pedidos.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.crunchy_app.pedidos.model.Pedido;
import com.example.crunchy_app.pedidos.model.PedidoConEstado;

import java.util.List;

@Dao
public interface PedidoDao {

    @Query("SELECT count(*) FROM pedidos")
    public int count();

    @Query("SELECT * FROM pedidos")
    public List<Pedido> getAll();

    @Query("SELECT * FROM pedidos WHERE id_pedido = :idPedido")
    public Pedido getPedidoById(Integer idPedido);

    @Insert
    public long insert(Pedido pedido);

    @Query("DELETE FROM pedidos WHERE id_pedido = :idPedido")
    public int deletePedidoById(Integer idPedido);

    @Query("SELECT * FROM pedidos WHERE id_estado_pedido = :estadoId")
    List<Pedido> getPedidosPorEstado(int estadoId);

    @Query("SELECT * FROM pedidos WHERE id_estado_pedido = :estadoId AND fecha = :fecha")
    List<Pedido> getPedidosPorEstadoByFecha(String fecha, int estadoId);

    @Query("SELECT * FROM pedidos WHERE id_estado_pedido IN (:estados)")
    List<Pedido> getListaPedidosPorEstados(List<Integer> estados);

    @Query("SELECT * FROM pedidos WHERE id_estado_pedido IN (:estados) AND fecha = :fecha")
    List<Pedido> getListaPedidosPorEstadosByFecha(String fecha, List<Integer> estados);

    @Query("SELECT SUM(pdp.cantidad * IFNULL(vap.valor_atributo_producto, 1)) " +
            "FROM productos_del_pedido pdp " +
            "JOIN productos p ON pdp.id_producto = p.id_producto " +
            "LEFT JOIN valores_atributo_producto vap ON vap.id_producto = p.id_producto AND vap.id_atributo_producto = 1 " + // Atributo Chorizo = 1
            "JOIN pedidos pe ON pe.id_pedido = pdp.id_pedido " +
            "WHERE pe.id_estado_pedido IN (2, 3, 4)")
    int getTotalChorizosVendidos();

    @Query("SELECT SUM(" +
            "CASE " +
            "WHEN p.id_producto = 41 THEN IFNULL(vap.valor_atributo_producto, 0) " + // Para el chicharrón personalizado
            "ELSE vap.valor_atributo_producto * pdp.cantidad " + // Para combos
            "END" +
            ") " +
            "FROM productos_del_pedido pdp " +
            "JOIN productos p ON pdp.id_producto = p.id_producto " +
            "LEFT JOIN valores_atributo_producto vap ON vap.id_producto = p.id_producto AND vap.id_atributo_producto = 2 " + // Atributo Chicharrón = 2
            "JOIN pedidos pe ON pe.id_pedido = pdp.id_pedido " +
            "WHERE pe.id_estado_pedido IN (2, 3, 4)")
    float getTotalChicharronVendido();

    @Delete
    void delete(Pedido pedido);

    @Update
    int update(Pedido pedido);

    @Query("SELECT * FROM pedidos WHERE fecha = :fecha")
    List<Pedido> getPedidosPorFecha(String fecha);

    @Transaction
    @Query("SELECT * FROM pedidos ORDER BY fecha DESC")
    List<PedidoConEstado> getPedidosConEstado();

    @Transaction
    @Query("SELECT * FROM pedidos WHERE fecha = :fecha ORDER BY fecha DESC")
    List<PedidoConEstado> getPedidosConEstadoByFecha(String fecha);


}
