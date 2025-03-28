package com.example.crunchy_app.pedidos.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.crunchy_app.pagos.model.MetodoPago;
import com.example.crunchy_app.productos.model.Producto;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(tableName = "pedidos", foreignKeys = {
        @ForeignKey(
                entity = MetodoPago.class,
                parentColumns = "id_metodo_pago",
                childColumns = "id_metodo_pago",
                onDelete = ForeignKey.RESTRICT,
                onUpdate = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = Locacion.class,
                parentColumns = "id_locacion",
                childColumns = "id_locacion",
                onDelete = ForeignKey.RESTRICT,
                onUpdate = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = EstadoPedido.class,
                parentColumns = "id_estado_pedido",
                childColumns = "id_estado_pedido",
                onDelete = ForeignKey.RESTRICT,
                onUpdate = ForeignKey.CASCADE
        )
})
public class Pedido {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_pedido")
    private Integer idPedido;

    @ColumnInfo(name = "nombre_cliente")
    @NonNull
    private String nombreCliente;

    @ColumnInfo(name = "apellido_cliente")
    private String apellidoCliente;

    @ColumnInfo(name = "id_metodo_pago")
    @NonNull
    private Integer idMetodoPago;

    @ColumnInfo(name = "id_locacion")
    @NonNull
    private Integer idLocacion;

    @ColumnInfo(name = "id_estado_pedido")
    @NonNull
    private Integer idEstadoPedido;

    @ColumnInfo(name = "fecha")
    @NonNull
    private LocalDate fecha;

    @ColumnInfo(name = "hora")
    @NonNull
    private LocalTime hora;

    public Pedido() {
    }

    public Pedido(@NonNull String nombreCliente, String apellidoCliente, @NonNull Integer idMetodoPago, @NonNull Integer idLocacion, @NonNull Integer idEstadoPedido, @NonNull LocalDate fecha, @NonNull LocalTime hora) {
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.idMetodoPago = idMetodoPago;
        this.idLocacion = idLocacion;
        this.idEstadoPedido = idEstadoPedido;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    @NonNull
    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(@NonNull String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    @NonNull
    public Integer getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(@NonNull Integer idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    @NonNull
    public Integer getIdLocacion() {
        return idLocacion;
    }

    public void setIdLocacion(@NonNull Integer idLocacion) {
        this.idLocacion = idLocacion;
    }

    @NonNull
    public Integer getIdEstadoPedido() {
        return idEstadoPedido;
    }

    public void setIdEstadoPedido(@NonNull Integer idEstadoPedido) {
        this.idEstadoPedido = idEstadoPedido;
    }

    @NonNull
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(@NonNull LocalDate fecha) {
        this.fecha = fecha;
    }

    @NonNull
    public LocalTime getHora() {
        return hora;
    }

    public void setHora(@NonNull LocalTime hora) {
        this.hora = hora;
    }
}
