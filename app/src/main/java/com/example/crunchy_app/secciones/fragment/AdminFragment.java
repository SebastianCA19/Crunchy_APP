package com.example.crunchy_app.secciones.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.R;
import com.example.crunchy_app.pedidos.activity.HistorialPedidosActivity;
import com.example.crunchy_app.pedidos.model.Pedido;
import com.example.crunchy_app.pedidos.model.ProductoDelPedido;
import com.example.crunchy_app.productos.DAO.ValorAtributoProductoDao;
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.ValorAtributoProducto;

import java.util.List;
import java.util.Objects;


public class AdminFragment extends Fragment {

    private TextView txtGanancias;

    private int cantidadChicharron=0;
    private int cantidadChorizos=0;

    private int cantidadChicharronVendidos=0;
    private int cantidadChorizosVendidos=0;

    private AppDataBase db;

    public AdminFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        txtGanancias = view.findViewById(R.id.gananciasValor);
        db = AppDataBase.getInstance(getContext());

        cargarCantidadChicharronYChorizos();
        cargarGanancias(); //  llamada inicial
        actualizarCantidadChicharron(view);
        actualizarCantidadChorizos(view);
        Button btnHistorial = view.findViewById(R.id.btnHistorial);
        btnHistorial.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HistorialPedidosActivity.class);
            startActivity(intent);
        });

        Button btnGuardarChicharron = view.findViewById(R.id.btnGuardarChicharron);

        btnGuardarChicharron.setOnClickListener(v -> {
            EditText inputMaxChicharron = view.findViewById(R.id.inputMaxChicharron);
            cantidadChicharron += Integer.parseInt(inputMaxChicharron.getText().toString());
            actualizarCantidadChicharron(view);
        });

        Button btnGuardarChorizos = view.findViewById(R.id.btnGuardarChorizos);

        btnGuardarChorizos.setOnClickListener(v -> {
            EditText inputMaxChorizos = view.findViewById(R.id.inputMaxChorizos);
            cantidadChorizos += Integer.parseInt(inputMaxChorizos.getText().toString());
            actualizarCantidadChorizos(view);
        });


        return view;
    }

    private void actualizarCantidadChorizos(View view) {
        TextView txtChorizos = view.findViewById(R.id.chorizosLabel);
        txtChorizos.setText("Chorizos: " + cantidadChorizosVendidos + " / " + cantidadChorizos+" Unidades");
        actualizarProgressBarChorizos(view);


    }

    private void actualizarCantidadChicharron(View view) {
        TextView txtChicharron = view.findViewById(R.id.chicharronLabel);
        txtChicharron.setText("Chicharrón: " + cantidadChicharronVendidos + " / " + cantidadChicharron+ " gr");
        actualizarProgressBarChicharron(view);
    }

    private void actualizarProgressBarChicharron(View view) {
        int progress = (int) ((double) cantidadChicharronVendidos / cantidadChicharron * 100);
        ProgressBar progressBar = view.findViewById(R.id.progressChicharron);
        progressBar.setProgress(progress);

    }

    private void actualizarProgressBarChorizos(View view) {
        int progress = (int) ((double) cantidadChorizosVendidos / cantidadChorizos * 100);
        ProgressBar progressBar = view.findViewById(R.id.progressChorizos);
        progressBar.setProgress(progress);
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarGanancias();
        cargarCantidadChicharronYChorizos();
    }

    private void cargarGanancias() {
        new Thread(() -> {
            List<Pedido> pedidosPagados = db.pedidoDao().getPedidosPorEstado(3);
            List<ProductoDelPedido> todosProductos = db.productoDelPedidoDao().getAll();
            List<Producto> productos = db.productoDao().getAll();

            double totalGanancias = 0;

            for (Pedido pedido : pedidosPagados) {
                for (ProductoDelPedido pdp : todosProductos) {
                    if (pdp.getIdPedido().equals(pedido.getIdPedido())) {
                        for (Producto producto : productos) {
                            if (producto.getIdProducto().equals(pdp.getIdProducto())) {
                                if(producto.getIdProducto() == 41){
                                    ValorAtributoProductoDao atributoProductoDao = db.valorAtributoProductoDao();
                                    String productoIdFormat = String.format("%d%d", producto.getIdProducto(), pedido.getIdPedido());
                                    float chicharron = atributoProductoDao.getValorAtributoProductoPersonalizado(Integer.valueOf(productoIdFormat)).getValorAtributoProducto();
                                    totalGanancias += chicharron * 80;
                                    break;
                                }
                                totalGanancias += producto.getValorProducto() * pdp.getCantidad();
                                break;
                            }
                        }
                    }
                }
            }

            double finalTotalGanancias = totalGanancias;
            requireActivity().runOnUiThread(() ->
                    txtGanancias.setText("$" + String.format("%,.0f", finalTotalGanancias)+ " COP")
            );
        }).start();
    }

    private void cargarCantidadChicharronYChorizos() {
        new Thread(() -> {
            List<Pedido> pedidosPagados = db.pedidoDao().getPedidosPorEstado(3);
            List<ProductoDelPedido> todosProductos = db.productoDelPedidoDao().getAll();
            List<Producto> productos = db.productoDao().getAll();

            cantidadChicharronVendidos=0;
            cantidadChorizosVendidos=0;

            for (Pedido pedido : pedidosPagados) {
                for (ProductoDelPedido pdp : todosProductos) {
                    if (pdp.getIdPedido().equals(pedido.getIdPedido())) {
                        for (Producto producto : productos) {
                            if (producto.getIdProducto().equals(pdp.getIdProducto())) {
                                ValorAtributoProductoDao atributoProductoDao = db.valorAtributoProductoDao();
                                if(producto.getIdProducto() == 41){

                                    String productoIdFormat = String.format("%d%d", producto.getIdProducto(), pedido.getIdPedido());
                                    float chicharron = atributoProductoDao.getValorAtributoProductoPersonalizado(Integer.valueOf(productoIdFormat)).getValorAtributoProducto();
                                    cantidadChicharronVendidos += chicharron;
                                    break;

                                }
                                if(producto.getIdProducto() == 40){
                                    cantidadChorizosVendidos += pdp.getCantidad();
                                    break;
                                }
                                List<ValorAtributoProducto> chicharrones = atributoProductoDao.getCantidadChicharron();
                                List<ValorAtributoProducto> chorizos = atributoProductoDao.getCantidadChorizo();
                                for (ValorAtributoProducto valorAtributoProducto : chicharrones) {
                                    if (valorAtributoProducto.getIdProducto().equals(producto.getIdProducto())) {
                                        cantidadChicharronVendidos += valorAtributoProducto.getValorAtributoProducto() * pdp.getCantidad();
                                        break;
                                    }

                                }
                                for (ValorAtributoProducto valorAtributoProducto : chorizos) {
                                    if (valorAtributoProducto.getIdProducto().equals(producto.getIdProducto())) {
                                        cantidadChorizosVendidos += valorAtributoProducto.getValorAtributoProducto() * pdp.getCantidad();
                                        break;
                                    }

                                }
                            }
                        }
                    }
                }
            }
            requireActivity().runOnUiThread(() -> {
                actualizarCantidadChicharron(requireView());
                actualizarCantidadChorizos(requireView());
            });

        }).start();
    }
}

