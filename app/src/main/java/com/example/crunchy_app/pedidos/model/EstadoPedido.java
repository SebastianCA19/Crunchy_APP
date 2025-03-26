package com.example.crunchy_app.pedidos.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "estados_pedido")
public class EstadoPedido {

    @PrimaryKey(autoGenerate = true)
    private int idEstadoPedido;

    @ColumnInfo(name = "nombre_estado_pedido")
    private String nombreEstadoPedido;

    public EstadoPedido(){

    }
    public EstadoPedido(String nombreEstadoPedido) {
        this.nombreEstadoPedido = nombreEstadoPedido;
    }

    public int getIdEstadoPedido() {
        return idEstadoPedido;
    }

    public void setIdEstadoPedido(int idEstadoPedido) {
        this.idEstadoPedido = idEstadoPedido;
    }

    public String getNombreEstadoPedido() {
        return nombreEstadoPedido;
    }

    public void setNombreEstadoPedido(String nombreEstadoPedido) {
        this.nombreEstadoPedido = nombreEstadoPedido;
    }
}
