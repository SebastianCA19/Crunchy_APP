package com.example.crunchy_app.productos.model;



public class Producto {
    private int id_producto;
    private String nombre_producto;

    private int id_tipo_producto;

    private float precio;

    private int id_info_producto;

    public Producto() {
    }


    public Producto(String nombre_producto, int id_tipo_producto, float precio, int id_info_producto) {
        this.nombre_producto = nombre_producto;
        this.id_tipo_producto = id_tipo_producto;
        this.precio = precio;
        this.id_info_producto = id_info_producto
    }

    public int getId_producto() {
        return id_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public int getId_tipo_producto() {
        return id_tipo_producto;
    }

    public float getPrecio() {
        return precio;
    }

    public int getId_info_producto() {
        return id_info_producto;
    }





}


