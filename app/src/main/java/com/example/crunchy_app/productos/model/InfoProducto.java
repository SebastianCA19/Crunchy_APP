package com.example.crunchy_app.productos.model;

public class InfoProducto {

    private int id_info_producto;

    private  int cantidad_chicharron;
    private  int cantidad_chorizo;


    public InfoProducto() {
    }

    public InfoProducto(int cantidad_chicharron, int cantidad_chorizo) {

        this.cantidad_chicharron = cantidad_chicharron;
        this.cantidad_chorizo = cantidad_chorizo;

    }

    public int getId_info_producto() {
        return id_info_producto;

    }
    public int getCantidad_chicharron() {
        return cantidad_chicharron;
    }
    public int getCantidad_chorizo() {
        return cantidad_chorizo;
    }

    public void setId_info_producto(int id_info_producto) {
        this.id_info_producto = id_info_producto;
    }



}
