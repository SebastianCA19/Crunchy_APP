package com.example.crunchy_app.pedidos.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tipos_pedido")
public class TipoPedido {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_tipo_pedido")
    private int idTipoPedido;

    @ColumnInfo(name = "nombre_tipo_pedido")
    @NonNull
    private String nombreTipoPedido;

    public TipoPedido(){

    }

    public TipoPedido(String nombreTipoPedido) {
        this.nombreTipoPedido = nombreTipoPedido;
    }

    public int getIdTipoPedido() {
        return idTipoPedido;
    }

    public void setIdTipoPedido(int idTipoPedido) {
        this.idTipoPedido = idTipoPedido;
    }

    public String getNombreTipoPedido() {
        return nombreTipoPedido;
    }

    public void setNombreTipoPedido(String nombreTipoPedido) {
        this.nombreTipoPedido = nombreTipoPedido;
    }
}
