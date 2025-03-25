package com.example.crunchy_app.pedidos.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tipos_de_pedido")
public class TipoPedido {

    @PrimaryKey(autoGenerate = true)
    private int idTipoPedido;

    @ColumnInfo(name = "nombre_tipo_pedido")
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
