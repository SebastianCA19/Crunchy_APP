package com.example.crunchy_app.pedidos.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.crunchy_app.pedidos.model.Locacion;

import java.util.List;

@Dao
public interface LocacionDao {

    @Query("SELECT count(*) FROM locaciones WHERE activo = 1")
    public int count();

    @Query("SELECT * FROM locaciones WHERE activo = 1")
    public List<Locacion> getAll();

    @Query("SELECT * FROM locaciones WHERE id_locacion = :idLocacion AND activo = 1")
    public Locacion getLocacionById(Integer idLocacion);

    @Query("SELECT * FROM locaciones WHERE id_padre_locacion = :idPadreLocacion AND activo = 1")
    public List<Locacion> getLocacionesByPadreLocacion(Integer idPadreLocacion);

    @Insert
    public long insert(Locacion locacion);

    @Query("UPDATE locaciones SET activo = 0 WHERE id_locacion = :idLocacion")
    public int deleteLocacionById(Integer idLocacion);

    @Update
    public void update(Locacion locacion);


    @Insert
    void insertAll(List<Locacion> locacions);

    @Query("SELECT * FROM locaciones WHERE activo = 1")
    List<Locacion> getAllActivas();

}
