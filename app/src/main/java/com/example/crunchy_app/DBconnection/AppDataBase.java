package com.example.crunchy_app.DBconnection;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.crunchy_app.productos.DAO.InfoProductoDao;
import com.example.crunchy_app.productos.model.InfoProducto;

@Database(entities = {InfoProducto.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract InfoProductoDao infoProductoDao();
}
