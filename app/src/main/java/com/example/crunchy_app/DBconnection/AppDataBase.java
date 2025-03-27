package com.example.crunchy_app.DBconnection;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.crunchy_app.pagos.DAO.MetodoPagoDao;
import com.example.crunchy_app.pagos.model.MetodoPago;
import com.example.crunchy_app.pedidos.DAO.EstadoPedidoDao;
import com.example.crunchy_app.pedidos.DAO.PedidoDao;
import com.example.crunchy_app.pedidos.DAO.ProductoDelPedidoDao;
import com.example.crunchy_app.pedidos.DAO.LocacionDao;
import com.example.crunchy_app.pedidos.model.EstadoPedido;
import com.example.crunchy_app.pedidos.model.Pedido;
import com.example.crunchy_app.pedidos.model.ProductoDelPedido;
import com.example.crunchy_app.pedidos.model.Locacion;
import com.example.crunchy_app.productos.DAO.InfoProductoDao;
import com.example.crunchy_app.productos.DAO.ProductoDao;
import com.example.crunchy_app.productos.DAO.TipoProductoDao;
import com.example.crunchy_app.productos.model.InfoProducto;
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.TipoProducto;
import com.example.crunchy_app.reportes.Dao.ResumenPorDiaDao;
import com.example.crunchy_app.DBconnection.converters.Converters;
import com.example.crunchy_app.reportes.model.ResumenPorDia;

@Database(entities = {InfoProducto.class, Producto.class, TipoProducto.class,
MetodoPago.class ,Pedido.class, EstadoPedido.class , Locacion.class,
ProductoDelPedido.class, ResumenPorDia.class}, version = 6)
@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase {

    private static volatile AppDataBase INSTANCE;

    public static AppDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDataBase.class, "crunchy-DB")
                            .fallbackToDestructiveMigration() // Borra y recrea si hay cambios en estructura
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    public abstract InfoProductoDao infoProductoDao();
    public abstract ProductoDao productoDao();
    public abstract TipoProductoDao tipoProductoDao();
    public abstract PedidoDao pedidoDao();
    public abstract MetodoPagoDao metodoPagoDao();
    public abstract LocacionDao tipoPedidoDao();
    public abstract EstadoPedidoDao estadoPedidoDao();
    public abstract ProductoDelPedidoDao productoDelPedidoDao();
    public abstract ResumenPorDiaDao resumenPorDiaDao();

}
