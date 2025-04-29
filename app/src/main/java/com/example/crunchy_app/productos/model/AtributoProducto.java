package com.example.crunchy_app.productos.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "atributos_producto")

public class AtributoProducto {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_atributo_producto")
    private Integer idAtributoProducto;

    @ColumnInfo(name = "nombre_atributo_producto")
    @NonNull
    private String nombreAtributoProducto;

    public AtributoProducto() {
    }

    public AtributoProducto(@NonNull String nombreAtributoProducto) {
        this.nombreAtributoProducto = nombreAtributoProducto;
    }

    public Integer getIdAtributoProducto() {
        return idAtributoProducto;
    }

    public void setIdAtributoProducto(Integer idAtributoProducto) {
        this.idAtributoProducto = idAtributoProducto;
    }

    @NonNull
    public String getNombreAtributoProducto() {
        return nombreAtributoProducto;
    }

    public void setNombreAtributoProducto(@NonNull String nombreAtributoProducto) {
        this.nombreAtributoProducto = nombreAtributoProducto;
    }

}
