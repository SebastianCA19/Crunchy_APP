package com.example.crunchy_app.productos.model;



public class Producto {
    private int idProducto;
    private String nombreProducto;

    private int idTipoProducto;

    private float precio;

    private int idInfoProducto;

    public Producto() {
    }


    public Producto(String nombreProducto, int idTipoProducto, float precio, int idInfoProducto) {
        this.nombreProducto = nombreProducto;
        this.idTipoProducto = idTipoProducto;
        this.precio = precio;
        this.idInfoProducto = idInfoProducto;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getIdTipoProducto() {
        return idTipoProducto;
    }

    public float getPrecio() {
        return precio;
    }

    public int getIdInfoProducto() {
        return idInfoProducto;
    }





}


