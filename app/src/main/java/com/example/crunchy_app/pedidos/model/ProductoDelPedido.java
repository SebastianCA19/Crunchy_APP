package com.example.crunchy_app.pedidos.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.crunchy_app.productos.model.Producto;

@Entity(tableName = "productos_del_pedido", foreignKeys = {
        @ForeignKey(
                entity = Pedido.class,
                parentColumns = "id_pedido",
                childColumns = "id_pedido",
                onDelete = ForeignKey.RESTRICT,
                onUpdate = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = Producto.class,
                parentColumns = "id_producto",
                childColumns = "id_producto",
                onDelete = ForeignKey.RESTRICT,
                onUpdate = ForeignKey.CASCADE
        )
})
public class ProductoDelPedido {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_productos_de_pedido")
    private Integer idProductosDePedido;

    @ColumnInfo(name = "id_pedido")
    @NonNull
    private Integer idPedido;

    @ColumnInfo(name = "id_producto")
    @NonNull
    private Integer idProducto;

    @ColumnInfo(name = "cantidad")
    @NonNull
    private Integer cantidad;

    public ProductoDelPedido() {
    }

    public ProductoDelPedido(@NonNull Integer idPedido, @NonNull Integer idProducto, @NonNull Integer cantidad) {
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public Integer getIdProductosDePedido() {
        return idProductosDePedido;
    }

    public void setIdProductosDePedido(Integer idProductosDePedido) {
        this.idProductosDePedido = idProductosDePedido;
    }

    @NonNull
    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(@NonNull Integer idPedido) {
        this.idPedido = idPedido;
    }

    @NonNull
    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(@NonNull Integer idProducto) {
        this.idProducto = idProducto;
    }

    @NonNull
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(@NonNull Integer cantidad) {
        this.cantidad = cantidad;
    }
}
