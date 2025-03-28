package com.example.crunchy_app.productos.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tipos_producto")
public class TipoProducto {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_tipo_producto")
    private Integer idTipoProdcuto;

    @ColumnInfo(name = "nombre_tipo_producto")
    @NonNull
    private String nombreTipoProducto;


    public TipoProducto() {
    }

    public TipoProducto(@NonNull String nombreTipoProducto) {
        this.nombreTipoProducto = nombreTipoProducto;
    }

    public Integer getIdTipoProdcuto() {
        return idTipoProdcuto;
    }

    public void setIdTipoProdcuto(Integer idTipoProdcuto) {
        this.idTipoProdcuto = idTipoProdcuto;
    }

    @NonNull
    public String getNombreTipoProducto() {
        return nombreTipoProducto;
    }

    public void setNombreTipoProducto(@NonNull String nombreTipoProducto) {
        this.nombreTipoProducto = nombreTipoProducto;
    }
}
