package com.example.crunchy_app.productos.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "info_productos")
public class InfoProducto {

    @PrimaryKey(autoGenerate = true)
    private int idInfoProducto;

    @ColumnInfo(name = "cantidad_chicharron")
    private  int cantidadChicharron;

    @ColumnInfo(name = "cantidad_chorizo")
    private  int cantidadChorizo;

    public InfoProducto() {
    }

    public InfoProducto(int cantidadChicharron, int cantidadChorizo) {

        this.cantidadChicharron = cantidadChicharron;
        this.cantidadChorizo = cantidadChorizo;

    }

    public int getIdInfoProducto() {
        return idInfoProducto;

    }
    public int getCantidadChicharron() {
        return cantidadChicharron;
    }
    public int getCantidadChorizo() {
        return cantidadChorizo;
    }

    public void setIdInfoProducto(int idInfoProducto) {
        this.idInfoProducto = idInfoProducto;
    }
    public void setCantidadChicharron(int cantidadChicharron) {
        this.cantidadChicharron = cantidadChicharron;
    }
    public void setCantidadChorizo(int cantidadChorizo) {
        this.cantidadChorizo = cantidadChorizo;
    }
}
