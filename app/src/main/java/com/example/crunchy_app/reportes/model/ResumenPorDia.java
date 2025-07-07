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

    @NonNull
    @ColumnInfo(name = "fecha")
    private LocalDate fecha;

    // Ingresos por método de pago
    @ColumnInfo(name = "dinero_efectivo")
    private float dineroEfectivo;

    @ColumnInfo(name = "dinero_transferencia")
    private float dineroTransferencia;

    // Cantidades vendidas
    @ColumnInfo(name = "cantidad_vendida_chicharron")
    private float cantidadVendidaChicharron;

    @ColumnInfo(name = "cantidad_vendida_chorizo")
    private int cantidadVendidaChorizo;

    @ColumnInfo(name = "cantidad_vendida_bollo")
    private float cantidadVendidaBollo;

    // Ingresos por tipo de producto
    @ColumnInfo(name = "dinero_bebida_personal")
    private float dineroBebidaPersonal;

    @ColumnInfo(name = "dinero_bebida_familiar")
    private float dineroBebidaFamiliar;

    @ColumnInfo(name = "dinero_bebida_alcoholica")
    private float dineroBebidaAlcoholica;

    @ColumnInfo(name = "dinero_combos")
    private float dineroCombos;

    @ColumnInfo(name = "dinero_picadas")
    private float dineroPicadas;

    // Top 3 productos más vendidos
    @ColumnInfo(name = "top_combo")
    private String topCombo;

    @ColumnInfo(name = "top_picada")
    private String topPicada;

    @ColumnInfo(name = "top_bebida_personal")
    private String topBebidaPersonal;

    @ColumnInfo(name = "top_bebida_familiar")
    private String topBebidaFamiliar;

    @ColumnInfo(name = "top_bebida_alcoholica")
    private String topBebidaAlcoholica;


    // Total de pedidos
    @ColumnInfo(name = "total_pedidos_dia")
    private int totalPedidosDia;

    public ResumenPorDia() {}

    public ResumenPorDia(@NonNull LocalDate fecha, float dineroEfectivo, float dineroTransferencia, float cantidadVendidaChicharron, int cantidadVendidaChorizo, float cantidadVendidaBollo, float dineroBebidaPersonal, float dineroBebidaFamiliar, float dineroBebidaAlcoholica, float dineroCombos, float dineroPicadas, String topCombo, String topPicada, String topBebidaPersonal, String topBebidaFamiliar, String topBebidaAlcoholica, int totalPedidosDia) {
        this.fecha = fecha;
        this.dineroEfectivo = dineroEfectivo;
        this.dineroTransferencia = dineroTransferencia;
        this.cantidadVendidaChicharron = cantidadVendidaChicharron;
        this.cantidadVendidaChorizo = cantidadVendidaChorizo;
        this.cantidadVendidaBollo = cantidadVendidaBollo;
        this.dineroBebidaPersonal = dineroBebidaPersonal;
        this.dineroBebidaFamiliar = dineroBebidaFamiliar;
        this.dineroBebidaAlcoholica = dineroBebidaAlcoholica;
        this.dineroCombos = dineroCombos;
        this.dineroPicadas = dineroPicadas;
        this.topCombo = topCombo;
        this.topPicada = topPicada;
        this.topBebidaPersonal = topBebidaPersonal;
        this.topBebidaFamiliar = topBebidaFamiliar;
        this.topBebidaAlcoholica = topBebidaAlcoholica;
        this.totalPedidosDia = totalPedidosDia;
    }

    // Getters y Setters

    public Integer getIdDia() {
        return idDia;
    }

    public void setIdDia(Integer idDia) {
        this.idDia = idDia;
    }

    public String getTopCombo() {
        return topCombo;
    }

    public void setTopCombo(String topCombo) {
        this.topCombo = topCombo;
    }

    public String getTopPicada() {
        return topPicada;
    }

    public void setTopPicada(String topPicada) {
        this.topPicada = topPicada;
    }

    public String getTopBebidaPersonal() {
        return topBebidaPersonal;
    }

    public void setTopBebidaPersonal(String topBebidaPersonal) {
        this.topBebidaPersonal = topBebidaPersonal;
    }

    public String getTopBebidaFamiliar() {
        return topBebidaFamiliar;
    }

    public void setTopBebidaFamiliar(String topBebidaFamiliar) {
        this.topBebidaFamiliar = topBebidaFamiliar;
    }

    public String getTopBebidaAlcoholica() {
        return topBebidaAlcoholica;
    }

    public void setTopBebidaAlcoholica(String topBebidaAlcoholica) {
        this.topBebidaAlcoholica = topBebidaAlcoholica;
    }

    @NonNull
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(@NonNull LocalDate fecha) {
        this.fecha = fecha;
    }

    public float getDineroEfectivo() {
        return dineroEfectivo;
    }

    public void setDineroEfectivo(float dineroEfectivo) {
        this.dineroEfectivo = dineroEfectivo;
    }

    public float getDineroTransferencia() {
        return dineroTransferencia;
    }

    public void setDineroTransferencia(float dineroTransferencia) {
        this.dineroTransferencia = dineroTransferencia;
    }

    public float getCantidadVendidaChicharron() {
        return cantidadVendidaChicharron;
    }

    public void setCantidadVendidaChicharron(float cantidadVendidaChicharron) {
        this.cantidadVendidaChicharron = cantidadVendidaChicharron;
    }

    public int getCantidadVendidaChorizo() {
        return cantidadVendidaChorizo;
    }

    public void setCantidadVendidaChorizo(int cantidadVendidaChorizo) {
        this.cantidadVendidaChorizo = cantidadVendidaChorizo;
    }

    public float getCantidadVendidaBollo() {
        return cantidadVendidaBollo;
    }

    public void setCantidadVendidaBollo(float cantidadVendidaBollo) {
        this.cantidadVendidaBollo = cantidadVendidaBollo;
    }

    public float getDineroBebidaPersonal() {
        return dineroBebidaPersonal;
    }

    public void setDineroBebidaPersonal(float dineroBebidaPersonal) {
        this.dineroBebidaPersonal = dineroBebidaPersonal;
    }

    public float getDineroBebidaFamiliar() {
        return dineroBebidaFamiliar;
    }

    public void setDineroBebidaFamiliar(float dineroBebidaFamiliar) {
        this.dineroBebidaFamiliar = dineroBebidaFamiliar;
    }

    public float getDineroBebidaAlcoholica() {
        return dineroBebidaAlcoholica;
    }

    public void setDineroBebidaAlcoholica(float dineroBebidaAlcoholica) {
        this.dineroBebidaAlcoholica = dineroBebidaAlcoholica;
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

    public int getTotalPedidosDia() {
        return totalPedidosDia;
    }

    public void setTotalPedidosDia(int totalPedidosDia) {
        this.totalPedidosDia = totalPedidosDia;
    }
}
