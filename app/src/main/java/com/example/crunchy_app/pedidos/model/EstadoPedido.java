package com.example.crunchy_app.pedidos.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "estados_pedido")
public class EstadoPedido {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_estado_pedido")
    private int idEstadoPedido;

    @ColumnInfo(name = "nombre_estado_pedido")
    @NonNull
    private String nombreEstadoPedido;

    public EstadoPedido(){

    }

    public EstadoPedido(@NonNull String nombreEstadoPedido) {
        this.nombreEstadoPedido = nombreEstadoPedido;
    }

    public int getIdEstadoPedido() {
        return idEstadoPedido;
    }

    public void setIdEstadoPedido(int idEstadoPedido) {
        this.idEstadoPedido = idEstadoPedido;
    }

    @NonNull
    public String getNombreEstadoPedido() {
        return nombreEstadoPedido;
    }

    public void setNombreEstadoPedido(@NonNull String nombreEstadoPedido) {
        this.nombreEstadoPedido = nombreEstadoPedido;
    }
}
