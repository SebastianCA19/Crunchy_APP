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

    @Transaction
    @Query("SELECT * FROM pedidos ORDER BY fecha DESC")
    List<PedidoConEstado> getPedidosConEstado();

    @Query("SELECT * FROM pedidos WHERE id_estado_pedido = :estadoId")
    List<Pedido> getPedidosPorEstado(int estadoId);


    @Delete
    void delete(Pedido pedido);

    @Update
    int update(Pedido pedido);
}
