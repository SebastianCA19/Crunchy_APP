package com.example.crunchy_app.productos.model;

public class TipoProducto {

    private int idTipoProdcuto;
    private String nombreTipoProducto;

    // Constructor
    public TipoProducto() {
    }

    public TipoProducto(String nombreTipoProducto) {

        this.nombreTipoProducto = nombreTipoProducto;


    }

    public int getIdTipoProdcuto() {
        return idTipoProdcuto;
    }

    public String getNombreTipoProducto() {
        return nombreTipoProducto;
    }

    public void setIdTipoProdcuto(int idTipoProdcuto) {
        this.idTipoProdcuto = idTipoProdcuto;
    }

    public void setNombreTipoProducto(String nombreTipoProducto) {
        this.nombreTipoProducto = nombreTipoProducto;
    }








}
