package com.example.crunchy_app.pedidos.DAO;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.pedidos.model.EstadoPedido;

import java.util.List;

@Dao
public interface EstadoPedidoDao {

    @Query("SELECT * FROM estados_pedido")
    public List<EstadoPedido> getAll();

    @Query("SELECT * FROM estados_pedido WHERE id_estado_pedido = :idEstadoPedido")
    public EstadoPedido getEstadoPedidoById(Integer idEstadoPedido);

    @Insert
    public long insert(EstadoPedido estadoPedido);

    @Query("DELETE FROM estados_pedido WHERE id_estado_pedido = :idEstadoPedido")
    public int deleteEstadoPedidoById(Integer idEstadoPedido);

}
