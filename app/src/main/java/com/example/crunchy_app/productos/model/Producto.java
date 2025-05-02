package com.example.crunchy_app.productos.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "productos", foreignKeys = {
        @ForeignKey(
                entity = TipoProducto.class,
                parentColumns = "id_tipo_producto",
                childColumns = "id_tipo_producto",
                onDelete = ForeignKey.RESTRICT,
                onUpdate = ForeignKey.CASCADE
        )
})
public class Producto implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_producto")
    private Integer idProducto;

    @ColumnInfo(name = "nombre_producto")
    @NonNull
    private String nombreProducto;

    @ColumnInfo(name = "id_tipo_producto")
    @NonNull
    private Integer idTipoProducto;

    @ColumnInfo(name = "valor_producto")
    @NonNull
    private float valorProducto;

    @Ignore
    private int cantidadChicharron;

    @Ignore
    private int cantidadChorizo;

    @Ignore
    private int cantidadBollo;

    @Ignore
    private float volumenMl;

    @Ignore
    private String infoString;

    @Ignore
    private boolean fixed;


    public Producto() {
    }


    public Producto(@NonNull String nombreProducto, @NonNull Integer idTipoProducto, @NonNull float valorProducto){
        this.nombreProducto = nombreProducto;
        this.idTipoProducto = idTipoProducto;
        this.valorProducto = valorProducto;
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


    public float getValorProducto() {
        return valorProducto;
    }

    public void setCantidadBollo(int cantidadBollo) {
        this.cantidadBollo = cantidadBollo;
    }

    public void setCantidadChorizo(int cantidadChorizo) {
        this.cantidadChorizo = cantidadChorizo;
    }

    public void setCantidadChicharron(int cantidadChicharron) {
        this.cantidadChicharron = cantidadChicharron;
    }

    public void setVolumenMl(float volumenMl) {
        this.volumenMl = volumenMl;
    }

    public float getVolumenMl() {
        return volumenMl;
    }

    public int getCantidadBollo() {
        return cantidadBollo;
    }

    public int getCantidadChicharron() {
        return cantidadChicharron;
    }

    public int getCantidadChorizo() {
        return cantidadChorizo;
    }

    public void setValorProducto(float valorProducto) {
        this.valorProducto = valorProducto;
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


