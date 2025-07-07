package com.example.crunchy_app.reportes.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.crunchy_app.reportes.model.ResumenPorDia;

import java.util.List;

@Dao
public interface ResumenPorDiaDao {

    // 🔢 Contar cuántos resúmenes hay
    @Query("SELECT count(*) FROM resumen_por_dia")
    int count();

    // 📋 Obtener todos los resúmenes
    @Query("SELECT * FROM resumen_por_dia")
    List<ResumenPorDia> getAll();

    // 🔍 Obtener resumen por fecha específica
    @Query("SELECT * FROM resumen_por_dia WHERE fecha = :fecha")
    ResumenPorDia getResumenPorFecha(String fecha);

    // 📆 Obtener resumenes entre fechas (útil para filtros)
    @Query("SELECT * FROM resumen_por_dia WHERE fecha BETWEEN :fechaInicio AND :fechaFin ORDER BY fecha DESC")
    List<ResumenPorDia> getResumenesEntreFechas(String fechaInicio, String fechaFin);

    // ⬇️ Insertar nuevo resumen del día
    @Insert
    long insertarResumen(ResumenPorDia resumenPorDia);

    // ❌ Eliminar resumen por fecha
    @Query("DELETE FROM resumen_por_dia WHERE fecha = :fecha")
    int eliminarResumenPorFecha(String fecha);

    // ✏️ Actualizar un resumen (opcional)
    @Update
    int actualizarResumen(ResumenPorDia resumen);

    // 🆕 Obtener el resumen más reciente
    @Query("SELECT * FROM resumen_por_dia ORDER BY fecha DESC LIMIT 1")
    ResumenPorDia getResumenMasReciente();
}
