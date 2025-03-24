package com.example.crunchy_app.productos.model;

public class TipoProducto {

    private int id_tipo_prodcuto;
    private String nombre_tipo_producto;

    // Constructor
    public TipoProducto() {
    }

    public TipoProducto(String nombre_tipo_producto) {

        this.nombre_tipo_producto = nombre_tipo_producto;


    }

    public int getId_tipo_prodcuto() {
        return id_tipo_prodcuto;
    }

    public String getNombre_tipo_producto() {
        return nombre_tipo_producto;
    }

    public void setId_tipo_prodcuto(int id_tipo_prodcuto) {
        this.id_tipo_prodcuto = id_tipo_prodcuto;
    }

    public void setNombre_tipo_producto(String nombre_tipo_producto) {
        this.nombre_tipo_producto = nombre_tipo_producto;
    }








}
