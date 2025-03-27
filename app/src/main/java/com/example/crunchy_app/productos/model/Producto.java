package com.example.crunchy_app.productos.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "productos")
public class Producto {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_producto")
    private int idProducto;

    @ColumnInfo(name = "nombre_producto")
    @NonNull
    private String nombreProducto;

    @ColumnInfo(name = "id_tipo_producto")
    @NonNull
    private int idTipoProducto;

    @ColumnInfo(name = "precio")
    @NonNull
    private float precio;

    @ColumnInfo(name = "id_info_producto")
    private int idInfoProducto;

    public Producto() {
    }


    public Producto(String nombreProducto, int idTipoProducto, float precio, int idInfoProducto) {
        this.nombreProducto = nombreProducto;
        this.idTipoProducto = idTipoProducto;
        this.precio = precio;
        this.idInfoProducto = idInfoProducto;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getIdTipoProducto() {
        return idTipoProducto;
    }

    public float getPrecio() {
        return precio;
    }

    public int getIdInfoProducto() {
        return idInfoProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setNombreProducto(@NonNull String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setIdTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setIdInfoProducto(int idInfoProducto) {
        this.idInfoProducto = idInfoProducto;
    }
}


