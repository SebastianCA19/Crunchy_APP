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
import com.example.crunchy_app.productos.DAO.AtributoProductoDao;
import com.example.crunchy_app.productos.DAO.ProductoDao;
import com.example.crunchy_app.productos.DAO.TipoProductoDao;
import com.example.crunchy_app.productos.DAO.ValorAtributoProductoDao;
import com.example.crunchy_app.productos.model.AtributoProducto;
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.TipoProducto;
import com.example.crunchy_app.productos.model.ValorAtributoProducto;
import com.example.crunchy_app.reportes.Dao.ResumenPorDiaDao;
import com.example.crunchy_app.DBconnection.converters.Converters;
import com.example.crunchy_app.reportes.model.ResumenPorDia;

@Database(entities = {Producto.class, TipoProducto.class, ValorAtributoProducto.class, AtributoProducto.class,
MetodoPago.class ,Pedido.class, EstadoPedido.class , Locacion.class,
ProductoDelPedido.class, ResumenPorDia.class}, version = 13)
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

    public static void resetInstance() {
        if (INSTANCE != null) {
            INSTANCE.close();
            INSTANCE = null;
        }
    }

    public abstract ProductoDao productoDao();
    public abstract ValorAtributoProductoDao valorAtributoProductoDao();
    public abstract AtributoProductoDao atributoProductoDao();
    public abstract TipoProductoDao tipoProductoDao();
    public abstract PedidoDao pedidoDao();
    public abstract MetodoPagoDao metodoPagoDao();

    public abstract EstadoPedidoDao estadoPedidoDao();
    public abstract ProductoDelPedidoDao productoDelPedidoDao();
    public abstract ResumenPorDiaDao resumenPorDiaDao();
    public abstract LocacionDao locacionDao();

}
