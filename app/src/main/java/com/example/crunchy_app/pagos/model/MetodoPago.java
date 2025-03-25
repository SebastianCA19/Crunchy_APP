package com.example.crunchy_app.pagos.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "metodos_pago")
public class MetodoPago {

    @PrimaryKey(autoGenerate = true)
    private int idMetodoDePago;

    @ColumnInfo(name = "nombre_metodo_pago")
    private String nombreMetodoDePago;

    public MetodoPago() {
    }

    public MetodoPago(String nombreMetodoDePago) {
        this.nombreMetodoDePago = nombreMetodoDePago;
    }

    public int getIdMetodoDePago() {
        return idMetodoDePago;
    }

    public String getNombreMetodoDePago() {
        return nombreMetodoDePago;
    }

    public void setIdMetodoDePago(int idMetodoDePago) {
        this.idMetodoDePago = idMetodoDePago;
    }

    public void setNombreMetodoDePago(String nombreMetodoDePago) {
        this.nombreMetodoDePago = nombreMetodoDePago;
    }

}
