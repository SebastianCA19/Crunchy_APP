package com.example.crunchy_app.pedidos.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "locacion")
public class Locacion {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_locacion")
    private int idLocacion;

    @ColumnInfo(name = "nombre_locacion")
    @NonNull
    private String nombreLocacion;

    @ColumnInfo(name = "id_padre_locacion")
    private int idPadreLocacion;

    @ColumnInfo(name = "valor_domicilio")
    private float valorDomicilio;

    public Locacion(){

    }

    public Locacion(@NonNull String nombreLocacion, int idPadreLocacion, float valorDomicilio) {
        this.nombreLocacion = nombreLocacion;
        this.idPadreLocacion = idPadreLocacion;
        this.valorDomicilio = valorDomicilio;
    }

    public int getIdLocacion() {
        return idLocacion;
    }

    public void setIdLocacion(int idLocacion) {
        this.idLocacion = idLocacion;
    }

    @NonNull
    public String getNombreLocacion() {
        return nombreLocacion;
    }

    public void setNombreLocacion(@NonNull String nombreLocacion) {
        this.nombreLocacion = nombreLocacion;
    }

    public int getIdPadreLocacion() {
        return idPadreLocacion;
    }

    public void setIdPadreLocacion(int idPadreLocacion) {
        this.idPadreLocacion = idPadreLocacion;
    }

    public float getValorDomicilio() {
        return valorDomicilio;
    }

    public void setValorDomicilio(float valorDomicilio) {
        this.valorDomicilio = valorDomicilio;
    }
}
