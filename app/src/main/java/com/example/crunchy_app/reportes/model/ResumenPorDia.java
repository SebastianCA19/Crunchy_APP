package com.example.crunchy_app.reportes.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;


@Entity(tableName = "resumen_por_dia")
public class ResumenPorDia {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_dia")
    private Integer idDia;

    @ColumnInfo(name = "fecha")
    @NonNull
    private LocalDate fecha;

    @ColumnInfo(name = "cantidad_chicharron")
    private float cantidadChicharron;

    @ColumnInfo(name = "cantidad_vendida_chicharron")
    private float cantidadVendidaChicharron;

    @ColumnInfo(name = "cantidad_chorizo")
    private Integer cantidadChorizo;

    @ColumnInfo(name = "cantidad_vendida_chorizo")
    private Integer cantidadVendidaChorizo;

    @ColumnInfo(name = "cantidad_bollo")
    private float cantidadBollo;

    @ColumnInfo(name = "cantidad_vendida_bollo")
    private float cantidadVendidaBollo;

    @ColumnInfo(name = "dinero_bebidas")
    private float dineroBebidas;

    @ColumnInfo(name = "dinero_chicharron")
    private float dineroChicharron;

    @ColumnInfo(name = "dinero_chorizo")
    private float dineroChorizo;

    @ColumnInfo(name = "dinero_bollo")
    private float dineroBollo;

    @ColumnInfo(name = "dinero_combos")
    private float dineroCombos;

    @ColumnInfo(name = "dinero_picadas")
    private float dineroPicadas;

    public ResumenPorDia() {

    }

    public ResumenPorDia(@NonNull LocalDate fecha, float cantidadChicharron, float cantidadVendidaChicharron, Integer cantidadChorizo, Integer cantidadVendidaChorizo, float cantidadBollo, float cantidadVendidaBollo, float dineroBebidas, float dineroChicharron, float dineroChorizo, float dineroBollo, float dineroPicadas, float dineroCombos) {
        this.fecha = fecha;
        this.cantidadChicharron = cantidadChicharron;
        this.cantidadVendidaChicharron = cantidadVendidaChicharron;
        this.cantidadChorizo = cantidadChorizo;
        this.cantidadVendidaChorizo = cantidadVendidaChorizo;
        this.cantidadBollo = cantidadBollo;
        this.cantidadVendidaBollo = cantidadVendidaBollo;
        this.dineroBebidas = dineroBebidas;
        this.dineroChicharron = dineroChicharron;
        this.dineroChorizo = dineroChorizo;
        this.dineroBollo = dineroBollo;
        this.dineroPicadas = dineroPicadas;
        this.dineroCombos = dineroCombos;
    }

    public Integer getIdDia() {
        return idDia;
    }

    public void setIdDia(Integer idDia) {
        this.idDia = idDia;
    }

    @NonNull
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(@NonNull LocalDate fecha) {
        this.fecha = fecha;
    }

    public float getCantidadChicharron() {
        return cantidadChicharron;
    }

    public void setCantidadChicharron(float cantidadChicharron) {
        this.cantidadChicharron = cantidadChicharron;
    }

    public float getCantidadVendidaChicharron() {
        return cantidadVendidaChicharron;
    }

    public void setCantidadVendidaChicharron(float cantidadVendidaChicharron) {
        this.cantidadVendidaChicharron = cantidadVendidaChicharron;
    }

    public Integer getCantidadChorizo() {
        return cantidadChorizo;
    }

    public void setCantidadChorizo(Integer cantidadChorizo) {
        this.cantidadChorizo = cantidadChorizo;
    }

    public Integer getCantidadVendidaChorizo() {
        return cantidadVendidaChorizo;
    }

    public void setCantidadVendidaChorizo(Integer cantidadVendidaChorizo) {
        this.cantidadVendidaChorizo = cantidadVendidaChorizo;
    }

    public float getCantidadBollo() {
        return cantidadBollo;
    }

    public void setCantidadBollo(float cantidadBollo) {
        this.cantidadBollo = cantidadBollo;
    }

    public float getCantidadVendidaBollo() {
        return cantidadVendidaBollo;
    }

    public void setCantidadVendidaBollo(float cantidadVendidaBollo) {
        this.cantidadVendidaBollo = cantidadVendidaBollo;
    }

    public float getDineroBebidas() {
        return dineroBebidas;
    }

    public void setDineroBebidas(float dineroBebidas) {
        this.dineroBebidas = dineroBebidas;
    }

    public float getDineroChicharron() {
        return dineroChicharron;
    }

    public void setDineroChicharron(float dineroChicharron) {
        this.dineroChicharron = dineroChicharron;
    }

    public float getDineroChorizo() {
        return dineroChorizo;
    }

    public void setDineroChorizo(float dineroChorizo) {
        this.dineroChorizo = dineroChorizo;
    }

    public float getDineroBollo() {
        return dineroBollo;
    }

    public void setDineroBollo(float dineroBollo) {
        this.dineroBollo = dineroBollo;
    }

    public float getDineroCombos() {
        return dineroCombos;
    }

    public void setDineroCombos(float dineroCombos) {
        this.dineroCombos = dineroCombos;
    }

    public float getDineroPicadas() {
        return dineroPicadas;
    }

    public void setDineroPicadas(float dineroPicadas) {
        this.dineroPicadas = dineroPicadas;
    }
}
