package com.example.crunchy_app.reportes.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crunchy_app.reportes.model.ResumenPorDia;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface ResumenPorDiaDao {

    @Query("SELECT count(*) FROM resumen_por_dia")
    public int count();

    @Query("SELECT * FROM resumen_por_dia")
    public List<ResumenPorDia> getAll();

    @Query("SELECT * FROM resumen_por_dia WHERE fecha = :fecha")
    public ResumenPorDia getResumenPorDiaById(String fecha);

    @Query("SELECT * FROM resumen_por_dia WHERE fecha BETWEEN :fechaInicio AND :fechaFin")
    public List<ResumenPorDia> getResumenesPorDiaBetweenDates(String fechaInicio, String fechaFin);

    @Insert
    public long insert(ResumenPorDia resumenPorDia);

    @Query("DELETE FROM resumen_por_dia WHERE fecha = :fecha")
    public int deleteResumenPorDiaById(String fecha);

}
