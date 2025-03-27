package com.example.crunchy_app.pedidos.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "productos_del_pedido")
public class ProductoDelPedido {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_productos_de_pedido")
    private int idProductosDePedido;

    @ColumnInfo(name = "id_pedido")
    @NonNull
    private int idPedido;

    @ColumnInfo(name = "id_producto")
    @NonNull
    private int idProducto;

    @ColumnInfo(name = "cantidad")
    @NonNull
    private int cantidad;

    public ProductoDelPedido() {
    }

    public ProductoDelPedido( int idPedido, int idProducto, int cantidad) {

        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public int getIdProductosDePedido() {
        return idProductosDePedido;
    }

    public void setIdProductosDePedido(int idProductosDePedido) {
        this.idProductosDePedido = idProductosDePedido;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
