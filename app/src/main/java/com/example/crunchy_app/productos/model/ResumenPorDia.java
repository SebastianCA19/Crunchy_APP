package com.example.crunchy_app.productos.model;


import java.time.LocalDate;

public class ResumenPorDia {
    private LocalDate fecha;
    private int cantidadChicharron;
    private int cantidadVendidaChicharron;
    private int cantidadChorizo;
    private int cantidadVendidaChorizo;
    private int dineroBebidas;
    private int dineroTotal;

    public ResumenPorDia() {

    }

    public ResumenPorDia(LocalDate fecha, int cantidadChicharron, int cantidadVendidaChicharron, int cantidadChorizo, int cantidadVendidaChorizo, int dineroBebidas, int dineroTotal) {
        this.fecha = fecha;
        this.cantidadChicharron = cantidadChicharron;
        this.cantidadVendidaChicharron = cantidadVendidaChicharron;
        this.cantidadChorizo = cantidadChorizo;
        this.cantidadVendidaChorizo = cantidadVendidaChorizo;
        this.dineroBebidas = dineroBebidas;
        this.dineroTotal = dineroTotal;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getCantidadChicharron() {
        return cantidadChicharron;
    }

    public void setCantidadChicharron(int cantidadChicharron) {
        this.cantidadChicharron = cantidadChicharron;
    }

    public int getCantidadVendidaChicharron() {
        return cantidadVendidaChicharron;
    }

    public void setCantidadVendidaChicharron(int cantidadVendidaChicharron) {
        this.cantidadVendidaChicharron = cantidadVendidaChicharron;
    }

    public int getCantidadChorizo() {
        return cantidadChorizo;
    }

    public void setCantidadChorizo(int cantidadChorizo) {
        this.cantidadChorizo = cantidadChorizo;
    }

    public int getCantidadVendidaChorizo() {
        return cantidadVendidaChorizo;
    }

    public void setCantidadVendidaChorizo(int cantidadVendidaChorizo) {
        this.cantidadVendidaChorizo = cantidadVendidaChorizo;
    }

    public int getDineroBebidas() {
        return dineroBebidas;
    }

    public void setDineroBebidas(int dineroBebidas) {
        this.dineroBebidas = dineroBebidas;
    }

    public int getDineroTotal() {
        return dineroTotal;
    }

    public void setDineroTotal(int dineroTotal) {
        this.dineroTotal = dineroTotal;
    }
}
