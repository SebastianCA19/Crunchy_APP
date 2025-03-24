package com.example.crunchy_app.pagos.model;

public class MetodoDePago {
    private int idMetodoDePago;
    private String nombreMetodoDePago;

    public MetodoDePago() {
    }

    public MetodoDePago(String nombreMetodoDePago) {
        this.nombreMetodoDePago = nombreMetodoDePago;
    }

    public int getIdMetodoDePago() {
        return idMetodoDePago;
    }

    public String getNombreMetodoDePago() {
        return nombreMetodoDePago;
    }

    public void setIdMetodoDePago(int idMetodoDePago) {
        this.idMetodoDePago = idMetodoDePago;
    }

    public void setNombreMetodoDePago(String nombreMetodoDePago) {
        this.nombreMetodoDePago = nombreMetodoDePago;
    }

}
