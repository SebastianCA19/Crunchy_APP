package com.example.crunchy_app.pagos.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "metodos_pago")
public class MetodoPago {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_metodo_pago")
    private int idMetodoPago;

    @ColumnInfo(name = "nombre_metodo_pago")
    @NonNull
    private String nombreMetodoPago;

    public MetodoPago() {
    }

    public MetodoPago(@NonNull String nombreMetodoPago) {
        this.nombreMetodoPago = nombreMetodoPago;
    }

    public int getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(int idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    @NonNull
    public String getNombreMetodoPago() {
        return nombreMetodoPago;
    }

    public void setNombreMetodoPago(@NonNull String nombreMetodoPago) {
        this.nombreMetodoPago = nombreMetodoPago;
    }
}
