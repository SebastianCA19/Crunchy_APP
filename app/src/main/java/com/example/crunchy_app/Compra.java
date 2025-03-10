package com.example.crunchy_app;

public class Compra {
    private String cliente;
    private String comida;
    private String bebida;
    private String total;
    private String fecha;

    public Compra(String cliente, String comida, String bebida, String total, String fecha) {
        this.cliente = cliente;
        this.comida = comida;
        this.bebida = bebida;
        this.total = total;
        this.fecha = fecha;
    }

    public String getCliente() { return cliente; }
    public String getComida() { return comida; }
    public String getBebida() { return bebida; }
    public String getTotal() { return total; }
    public String getFecha() { return fecha; }
}

