package com.example.crunchy_app.DBconnection;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.crunchy_app.pagos.DAO.MetodoPagoDao;
import com.example.crunchy_app.pagos.model.MetodoPago;
import com.example.crunchy_app.pedidos.DAO.EstadoPedidoDao;
import com.example.crunchy_app.pedidos.DAO.PedidoDao;
import com.example.crunchy_app.pedidos.DAO.ProductoDelPedidoDao;
import com.example.crunchy_app.pedidos.DAO.TipoPedidoDao;
import com.example.crunchy_app.pedidos.model.EstadoPedido;
import com.example.crunchy_app.pedidos.model.Pedido;
import com.example.crunchy_app.pedidos.model.ProductoDelPedido;
import com.example.crunchy_app.pedidos.model.TipoPedido;
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
MetodoPago.class ,Pedido.class, EstadoPedido.class ,TipoPedido.class,
ProductoDelPedido.class, ResumenPorDia.class}, version = 3)
@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase {
    public abstract InfoProductoDao infoProductoDao();
    public abstract ProductoDao productoDao();
    public abstract TipoProductoDao tipoProductoDao();
    public abstract PedidoDao pedidoDao();
    public abstract MetodoPagoDao metodoPagoDao();
    public abstract TipoPedidoDao tipoPedidoDao();
    public abstract EstadoPedidoDao estadoPedidoDao();
    public abstract ProductoDelPedidoDao productoDelPedidoDao();
    public abstract ResumenPorDiaDao resumenPorDiaDao();

}
