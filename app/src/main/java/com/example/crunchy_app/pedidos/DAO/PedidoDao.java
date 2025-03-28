package com.example.crunchy_app.pedidos.DAO;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.pedidos.model.Pedido;

import java.util.List;

@Dao
public interface PedidoDao {

    @Query("SELECT * FROM pedidos")
    public List<Pedido> getAll();

    @Query("SELECT * FROM pedidos WHERE id_pedido = :idPedido")
    public Pedido getPedidoById(Integer idPedido);

    @Insert
    public long insert(Pedido pedido);

    @Query("DELETE FROM pedidos WHERE id_pedido = :idPedido")
    public int deletePedidoById(Integer idPedido);
}
