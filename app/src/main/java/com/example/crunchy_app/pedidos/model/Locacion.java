package com.example.crunchy_app.pedidos.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "locaciones")
public class Locacion {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_locacion")
    private Integer idLocacion;

    @ColumnInfo(name = "nombre_locacion")
    @NonNull
    private String nombreLocacion;

    @ColumnInfo(name = "id_padre_locacion")
    private Integer idPadreLocacion;

    @ColumnInfo(name = "valor_domicilio")
    private float valorDomicilio;

    @ColumnInfo(name = "activo")
    private boolean activo;

    public Locacion(){

    }

    public Locacion(@NonNull String nombreLocacion, Integer idPadreLocacion, float valorDomicilio) {
        this.nombreLocacion = nombreLocacion;
        this.idPadreLocacion = idPadreLocacion;
        this.valorDomicilio = valorDomicilio;
        this.activo = true;
    }

    public void setActivo(boolean activo){
        this.activo = activo;
    }

    public boolean isActivo(){
        return activo;
    }

    public Integer getIdLocacion() {
        return idLocacion;
    }

    public void setIdLocacion(Integer idLocacion) {
        this.idLocacion = idLocacion;
    }

    @NonNull
    public String getNombreLocacion() {
        return nombreLocacion;
    }

    public void setNombreLocacion(@NonNull String nombreLocacion) {
        this.nombreLocacion = nombreLocacion;
    }

    public Integer getIdPadreLocacion() {
        return idPadreLocacion;
    }

    public void setIdPadreLocacion(Integer idPadreLocacion) {
        this.idPadreLocacion = idPadreLocacion;
    }

    public float getValorDomicilio() {
        return valorDomicilio;
    }

    public void setValorDomicilio(float valorDomicilio) {
        this.valorDomicilio = valorDomicilio;
    }

    @Override
    public String toString() {
        return nombreLocacion.toUpperCase();
    }
}
