package com.example.crunchy_app.pedidos.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Pedido {
    private int idPedido;
    private String nombreCliente;
    private String apellidoCliente;
    private int metodoDePago;
    private int idTipoPedido;
    private int esPersonalizado;
    private LocalDate fecha;
    private LocalTime hora;

    public Pedido() {
    }

    public Pedido(String nombreCliente, String apellidoCliente, int metodoDePago, int idTipoPedido, int esPersonalizado, LocalDate fecha, LocalTime hora) {
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.metodoDePago = metodoDePago;
        this.idTipoPedido = idTipoPedido;
        this.esPersonalizado = esPersonalizado;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public int getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(int metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public int getIdTipoPedido() {
        return idTipoPedido;
    }

    public void setIdTipoPedido(int idTipoPedido) {
        this.idTipoPedido = idTipoPedido;
    }

    public int getEsPersonalizado() {
        return esPersonalizado;
    }

    public void setEsPersonalizado(int esPersonalizado) {
        this.esPersonalizado = esPersonalizado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
}
