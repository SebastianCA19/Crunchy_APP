package com.example.crunchy_app.pagos.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.pagos.model.MetodoPago;

import java.util.List;

@Dao
public interface MetodoPagoDao {

    @Query("SELECT count(*) FROM metodos_pago")
    public int count();

    @Query("SELECT * FROM metodos_pago")
    public List<MetodoPago> getAll();

    @Query("SELECT * FROM metodos_pago WHERE id_metodo_pago = :idMetodoPago")
    public MetodoPago getMetodoPagoById(Integer idMetodoPago);

    @Insert
    public long insert(MetodoPago metodoPago);

    @Query("DELETE FROM metodos_pago WHERE id_metodo_pago = :idMetodoPago")
    public int deleteMetodoPagoById(Integer idMetodoPago);
}
