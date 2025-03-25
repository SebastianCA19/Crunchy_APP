package com.example.crunchy_app.DBconnection;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.crunchy_app.pagos.DAO.MetodoPagoDao;
import com.example.crunchy_app.pagos.model.MetodoPago;
import com.example.crunchy_app.pedidos.DAO.PedidoDao;
import com.example.crunchy_app.pedidos.DAO.TipoPedidoDao;
import com.example.crunchy_app.pedidos.model.Pedido;
import com.example.crunchy_app.pedidos.model.TipoPedido;
import com.example.crunchy_app.productos.DAO.InfoProductoDao;
import com.example.crunchy_app.productos.DAO.ProductoDao;
import com.example.crunchy_app.productos.DAO.TipoProductoDao;
import com.example.crunchy_app.productos.model.InfoProducto;
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.TipoProducto;

@Database(entities = {InfoProducto.class, Producto.class, TipoProducto.class,
MetodoPago.class ,Pedido.class, TipoPedido.class}, version = 3)
public abstract class AppDataBase extends RoomDatabase {
    public abstract InfoProductoDao infoProductoDao();
    public abstract ProductoDao productoDao();
    public abstract TipoProductoDao tipoProductoDao();
    public abstract MetodoPagoDao metodoPagoDao();
    public abstract PedidoDao pedidoDao();
    public abstract TipoPedidoDao tipoPedidoDao();
}
