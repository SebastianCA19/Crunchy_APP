package com.example.crunchy_app.pedidos.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.pedidos.model.TipoPedido;

import java.util.List;

@Dao
public interface TipoPedidoDao {

    @Query("SELECT * FROM tipos_de_pedido")
    List<TipoPedido> getAll();

    @Insert
    long insert(TipoPedido tipoPedido);
}
