package com.example.crunchy_app.productos.model;

public class InfoProducto {

    private int idInfoProducto;

    private  int cantidadChicharron;
    private  int cantidadChorizo;


    public InfoProducto() {
    }

    public InfoProducto(int cantidadChicharron, int cantidadChorizo) {

        this.cantidadChicharron = cantidadChicharron;
        this.cantidadChorizo = cantidadChorizo;

    }

    public int getIdInfoProducto() {
        return idInfoProducto;

    }
    public int getCantidadChicharron() {
        return cantidadChicharron;
    }
    public int getCantidadChorizo() {
        return cantidadChorizo;
    }

    public void setIdInfoProducto(int idInfoProducto) {
        this.idInfoProducto = idInfoProducto;
    }



}
