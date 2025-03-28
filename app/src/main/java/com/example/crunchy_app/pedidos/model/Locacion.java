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

    public Locacion(){

    }

    public Locacion(@NonNull String nombreLocacion, Integer idPadreLocacion, float valorDomicilio) {
        this.nombreLocacion = nombreLocacion;
        this.idPadreLocacion = idPadreLocacion;
        this.valorDomicilio = valorDomicilio;
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
}
