package com.example.crunchy_app.pedidos.DAO;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.pedidos.model.Pedido;

import java.util.List;

@Dao
public interface PedidoDao {

    @Query("SELECT * FROM pedidos")
    List<Pedido> getAll();

    @Insert
    long insert(Pedido pedido);
}
