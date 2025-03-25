package com.example.crunchy_app.DBconnection;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.crunchy_app.productos.DAO.InfoProductoDao;
import com.example.crunchy_app.productos.DAO.ProductoDao;
import com.example.crunchy_app.productos.DAO.TipoProductoDao;
import com.example.crunchy_app.productos.model.InfoProducto;
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.TipoProducto;

@Database(entities = {InfoProducto.class, Producto.class, TipoProducto.class}, version = 2)
public abstract class AppDataBase extends RoomDatabase {
    public abstract InfoProductoDao infoProductoDao();
    public abstract ProductoDao productoDao();
    public abstract TipoProductoDao tipoProductoDao();
}
