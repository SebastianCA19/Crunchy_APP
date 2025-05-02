package com.example.crunchy_app.pedidos.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class PedidoConEstado {
    @Embedded
    public Pedido pedido;

    @Relation(
            parentColumn = "id_estado_pedido",
            entityColumn = "id_estado_pedido"
    )
    public EstadoPedido estado;
}