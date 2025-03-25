package com.example.crunchy_app.pedidos.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(tableName = "pedidos")
public class Pedido {

    @PrimaryKey(autoGenerate = true)
    private int idPedido;

    @ColumnInfo(name = "nombre_cliente")
    @NonNull
    private String nombreCliente;

    @ColumnInfo(name = "apellido_cliente")
    private String apellidoCliente;

    @ColumnInfo(name = "id_metodo_pago")
    @NonNull
    private int idMetodoDePago;

    @ColumnInfo(name = "id_tipo_pedido")
    @NonNull
    private int idTipoPedido;

    @ColumnInfo(name = "es_personalizado")
    @NonNull
    private int esPersonalizado;

    @ColumnInfo(name = "fecha")
    @NonNull
    private LocalDate fecha;

    @ColumnInfo(name = "hora")
    @NonNull
    private LocalTime hora;

    public Pedido() {
    }

    public Pedido(String nombreCliente, String apellidoCliente, int idMetodoDePago, int idTipoPedido, int esPersonalizado, LocalDate fecha, LocalTime hora) {
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.idMetodoDePago = idMetodoDePago;
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

    public int getIdMetodoDePago() {
        return idMetodoDePago;
    }

    public void setIdMetodoDePago(int idMetodoDePago) {
        this.idMetodoDePago = idMetodoDePago;
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
