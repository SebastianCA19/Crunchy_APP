package com.example.crunchy_app.productos.adapter;

import android.icu.text.NumberFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crunchy_app.R;
import com.example.crunchy_app.productos.model.Producto;

import java.util.List;
import java.util.Locale;

public class ProductoAdapter extends  RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>{
    private final List<Producto> productos;
    private final OnProductoActionListener listener;

    public interface OnProductoActionListener {
        void onEditar(Producto producto);
        void onEliminar(Producto producto);
    }

    public ProductoAdapter(List<Producto> productos, OnProductoActionListener listener) {
        this.productos = productos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = productos.get(position);

        holder.txtNombre.setText(producto.getNombreProducto());
        holder.txtTipo.setText(getNombreTipo(producto.getIdTipoProducto()));

        String precio = NumberFormat.getCurrencyInstance(new Locale("es", "CO")).format(producto.getValorProducto());
        holder.txtPrecio.setText("Precio: " + precio);

        holder.btnEditar.setOnClickListener(v -> listener.onEditar(producto));
        holder.btnEliminar.setOnClickListener(v -> listener.onEliminar(producto));
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    static class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtTipo, txtPrecio;
        Button btnEditar, btnEliminar;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombreProducto);
            txtTipo = itemView.findViewById(R.id.txtTipoProducto);
            txtPrecio = itemView.findViewById(R.id.txtPrecioProducto);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }

    private String getNombreTipo(int tipoId) {
        switch (tipoId) {
            case 1: return "Combo";
            case 2: return "Picada";
            case 3: return "bebida personal";
            case 4: return "Bebida familiar";
            case 5: return "Bebida alcoholica";
            case 6: return "personalizado";
            default: return "Desconocido";
        }
    }
}
