package com.example.crunchy_app.pagos.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.pagos.model.MetodoPago;

import java.util.List;

@Dao
public interface MetodoPagoDao {

    @Query("SELECT * FROM metodos_pago")
    List<MetodoPago> getAll();

    @Insert
    long insert(MetodoPago metodoPago);
}
