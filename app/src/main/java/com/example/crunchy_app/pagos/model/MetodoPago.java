package com.example.crunchy_app.pagos.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "metodos_pago")
public class MetodoPago {

    @PrimaryKey(autoGenerate = true)
    private int idMetodoPago;

    @ColumnInfo(name = "nombre_metodo_pago")
    @NonNull
    private String nombreMetodoPago;

    public MetodoPago() {
    }

    public MetodoPago(String nombreMetodoPago) {
        this.nombreMetodoPago = nombreMetodoPago;
    }

    public int getIdMetodoPago() {
        return idMetodoPago;
    }

    public String getNombreMetodoPago() {
        return nombreMetodoPago;
    }

    public void setIdMetodoPago(int idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public void setNombreMetodoPago(String nombreMetodoPago) {
        this.nombreMetodoPago = nombreMetodoPago;
    }

}
