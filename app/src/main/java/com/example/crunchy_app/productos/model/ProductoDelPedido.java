package com.example.crunchy_app.productos.model;

public class ProductoDelPedido {
    private int idProductosDePedido;
    private int idPedido;
    private int idProducto;
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
