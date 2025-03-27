package com.example.crunchy_app.productos.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "infos_producto")
public class InfoProducto {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_info_producto")
    private int idInfoProducto;

    @ColumnInfo(name = "cantidad_chicharron_gramos")
    private  float cantidadChicharronGramos;

    @ColumnInfo(name = "cantidad_chorizo")
    private  int cantidadChorizo;

    @ColumnInfo(name = "cantidad_bollo")
    private  float cantidadBollo;

    public InfoProducto() {
    }

    public InfoProducto(float cantidadChicharronGramos, int cantidadChorizo, float cantidadBollo) {
        this.cantidadChicharronGramos = cantidadChicharronGramos;
        this.cantidadChorizo = cantidadChorizo;
        this.cantidadBollo = cantidadBollo;
    }

    public int getIdInfoProducto() {
        return idInfoProducto;
    }

    public void setIdInfoProducto(int idInfoProducto) {
        this.idInfoProducto = idInfoProducto;
    }

    public float getCantidadChicharronGramos() {
        return cantidadChicharronGramos;
    }

    public void setCantidadChicharronGramos(float cantidadChicharronGramos) {
        this.cantidadChicharronGramos = cantidadChicharronGramos;
    }

    public int getCantidadChorizo() {
        return cantidadChorizo;
    }

    public void setCantidadChorizo(int cantidadChorizo) {
        this.cantidadChorizo = cantidadChorizo;
    }

    public float getCantidadBollo() {
        return cantidadBollo;
    }

    public void setCantidadBollo(float cantidadBollo) {
        this.cantidadBollo = cantidadBollo;
    }
}


