package com.example.crunchy_app;

import java.io.Serializable;

public class Compra implements Serializable {  // 👈 Implementamos Serializable
    private String nombreCliente;
    private String comida;
    private String bebida;
    private double total;
    private String fecha;

    public Compra(String nombreCliente, String comida, String bebida, double total, String fecha) {
        this.nombreCliente = nombreCliente;
        this.comida = comida;
        this.bebida = bebida;
        this.total = total;
        this.fecha = fecha;
    }

    public String getNombreCliente() { return nombreCliente; }
    public String getComida() { return comida; }
    public String getBebida() { return bebida; }
    public double getTotal() { return total; }
    public String getFecha() { return fecha; }
}
