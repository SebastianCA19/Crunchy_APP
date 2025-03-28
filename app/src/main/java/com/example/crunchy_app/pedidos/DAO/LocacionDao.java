package com.example.crunchy_app.pedidos.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.pedidos.model.Locacion;

import java.util.List;

@Dao
public interface LocacionDao {

    @Query("SELECT count(*) FROM locaciones")
    public int count();

    @Query("SELECT * FROM locaciones")
    public List<Locacion> getAll();

    @Query("SELECT * FROM locaciones WHERE id_locacion = :idLocacion")
    public Locacion getLocacionById(Integer idLocacion);

    @Query("SELECT * FROM locaciones WHERE id_padre_locacion = :idPadreLocacion")
    public List<Locacion> getLocacionesByPadreLocacion(Integer idPadreLocacion);

    @Insert
    public long insert(Locacion locacion);

    @Query("DELETE FROM locaciones WHERE id_locacion = :idLocacion")
    public int deleteLocacionById(Integer idLocacion);
}
