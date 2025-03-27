package com.example.crunchy_app.productos.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tipos_producto")
public class TipoProducto {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_tipo_producto")
    private int idTipoProdcuto;

    @ColumnInfo(name = "nombre_tipo_producto")
    @NonNull
    private String nombreTipoProducto;

    // Constructor
    public TipoProducto() {
    }

    public TipoProducto(String nombreTipoProducto) {

        this.nombreTipoProducto = nombreTipoProducto;


    }

    public int getIdTipoProdcuto() {
        return idTipoProdcuto;
    }

    public String getNombreTipoProducto() {
        return nombreTipoProducto;
    }

    public void setIdTipoProdcuto(int idTipoProdcuto) {
        this.idTipoProdcuto = idTipoProdcuto;
    }

    public void setNombreTipoProducto(String nombreTipoProducto) {
        this.nombreTipoProducto = nombreTipoProducto;
    }








}
