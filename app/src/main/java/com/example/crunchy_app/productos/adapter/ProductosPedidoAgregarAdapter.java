package com.example.crunchy_app.productos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crunchy_app.R;
import com.example.crunchy_app.productos.model.Producto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductosPedidoAgregarAdapter extends RecyclerView.Adapter<ProductosPedidoAgregarAdapter.ViewHolder> {

    private List<Map.Entry<Producto, Integer>> productosLista;

    public ProductosPedidoAgregarAdapter(HashMap<Producto, Integer> productosPedido) {
        this.productosLista = new ArrayList<>(productosPedido.entrySet());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombreProducto;
        TextView txtCantidadProducto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreProducto = itemView.findViewById(R.id.txtNombreProducto);
            txtCantidadProducto = itemView.findViewById(R.id.txtCantidadProducto);
        }
    }

    @NonNull
    @Override
    public ProductosPedidoAgregarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_simple, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductosPedidoAgregarAdapter.ViewHolder holder, int position) {
        Map.Entry<Producto, Integer> entry = productosLista.get(position);
        Producto producto = entry.getKey();
        int cantidad = entry.getValue();

        holder.txtNombreProducto.setText(producto.getNombreProducto().toUpperCase());
        holder.txtCantidadProducto.setText("x" + cantidad);
    }

    @Override
    public int getItemCount() {
        return productosLista.size();
    }
}


