package com.example.crunchy_app.productos.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.crunchy_app.pedidos.model.Pedido;

@Entity(tableName = "productos", foreignKeys = {
        @ForeignKey(
                entity = TipoProducto.class,
                parentColumns = "id_tipo_producto",
                childColumns = "id_tipo_producto",
                onDelete = ForeignKey.RESTRICT,
                onUpdate = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = InfoProducto.class,
                parentColumns = "id_info_producto",
                childColumns = "id_info_producto",
                onDelete = ForeignKey.RESTRICT,
                onUpdate = ForeignKey.CASCADE
        )
})
public class Producto {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_producto")
    private Integer idProducto;

    @ColumnInfo(name = "nombre_producto")
    @NonNull
    private String nombreProducto;

    @ColumnInfo(name = "id_tipo_producto")
    @NonNull
    private Integer idTipoProducto;

    @ColumnInfo(name = "id_info_producto")
    private Integer idInfoProducto;

    @ColumnInfo(name = "precio")
    @NonNull
    private float precio;

    @Ignore
    private String infoString;

    @Ignore
    private boolean fixed;


    public Producto() {
    }


    public Producto(@NonNull String nombreProducto, @NonNull Integer idTipoProducto, Integer idInfoProducto, float precio) {
        this.nombreProducto = nombreProducto;
        this.idTipoProducto = idTipoProducto;
        this.idInfoProducto = idInfoProducto;
        this.precio = precio;
    }

    public void setInfoString(String infoString){
        this.infoString = infoString;
    }

    public String getInfoString(){
        return infoString;
    }

    public void setFixed(boolean fixed){
        this.fixed = fixed;
    }

    public boolean isFixed(){
        return fixed;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    @NonNull
    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(@NonNull String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    @NonNull
    public Integer getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(@NonNull Integer idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public Integer getIdInfoProducto() {
        return idInfoProducto;
    }

    public void setIdInfoProducto(Integer idInfoProducto) {
        this.idInfoProducto = idInfoProducto;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Producto producto = (Producto) obj;
        return idProducto == producto.idProducto;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idProducto);
    }

}


