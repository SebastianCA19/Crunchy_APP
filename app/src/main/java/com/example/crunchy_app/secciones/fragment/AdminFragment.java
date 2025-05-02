package com.example.crunchy_app.secciones.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.R;
import com.example.crunchy_app.pedidos.activity.HistorialPedidosActivity;
import com.example.crunchy_app.pedidos.model.Pedido;
import com.example.crunchy_app.pedidos.model.ProductoDelPedido;
import com.example.crunchy_app.productos.model.Producto;

import java.util.List;


public class AdminFragment extends Fragment {

    private TextView txtGanancias;
    private AppDataBase db;

    public AdminFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        txtGanancias = view.findViewById(R.id.gananciasValor);
        db = AppDataBase.getInstance(getContext());

        cargarGanancias(); //  llamada inicial

        Button btnHistorial = view.findViewById(R.id.btnHistorial);
        btnHistorial.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HistorialPedidosActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarGanancias();
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
}

