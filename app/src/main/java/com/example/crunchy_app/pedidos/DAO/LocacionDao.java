package com.example.crunchy_app.pedidos.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.pedidos.model.Locacion;

import java.util.List;

@Dao
public interface LocacionDao {

    @Query("SELECT * FROM locacion")
    public List<Locacion> getAll();

    @Query("SELECT * FROM locacion WHERE id_locacion = :idLocacion")
    public Locacion getLocacionById(int idLocacion);

    @Query("SELECT * FROM locacion WHERE id_padre_locacion = :idPadreLocacion")
    public List<Locacion> getLocacionesByPadreLocacion(int idPadreLocacion);

    @Insert
    public long insert(Locacion locacion);

    @Query("DELETE FROM locacion WHERE id_locacion = :idLocacion")
    public int deleteLocacionById(int idLocacion);
}
