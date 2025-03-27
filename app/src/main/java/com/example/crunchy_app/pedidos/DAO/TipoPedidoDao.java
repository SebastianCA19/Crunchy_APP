package com.example.crunchy_app.pedidos.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.pedidos.model.TipoPedido;

import java.util.List;

@Dao
public interface TipoPedidoDao {

    @Query("SELECT * FROM tipos_pedido")
    public List<TipoPedido> getAll();

    @Query("SELECT * FROM tipos_pedido WHERE id_tipo_pedido = :idTipoPedido")
    public TipoPedido getTipoPedidoById(int idTipoPedido);

    @Insert
    public long insert(TipoPedido tipoPedido);

    @Query("DELETE FROM tipos_pedido WHERE id_tipo_pedido = :idTipoPedido")
    public int deleteTipoPedidoById(int idTipoPedido);
}
