package com.example.crunchy_app.carrito.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crunchy_app.R;
import com.example.crunchy_app.productos.model.Producto;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final Map<Producto, Integer> carrito;
    private final List<Producto> productos;
    private final Runnable onCartUpdated;

    private final NumberFormat numberFormat;

    public CartAdapter(Map<Producto, Integer> carrito, Runnable onCartUpdated) {
        this.carrito = carrito;
        this.productos = new ArrayList<>(carrito.keySet());
        this.onCartUpdated = onCartUpdated;
        this.numberFormat = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        Log.d("CartAdapter", "Adapter creado con " + productos.size() + " productos");
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("CartAdapter", "onCreateViewHolder llamado");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart_product, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        if (position >= productos.size()) {
            Log.w("CartAdapter", "Posición fuera de rango: " + position);
            return;
        }

        Producto producto = productos.get(position);
        int cantidad = carrito.getOrDefault(producto, 0);

        Log.d("CartAdapter", "onBindViewHolder -> " + producto.getNombreProducto() + " x" + cantidad);

        holder.txtNombre.setText(producto.getNombreProducto().toUpperCase());
        holder.txtPrecio.setText(numberFormat.format(producto.getValorProducto()));
        holder.txtCantidad.setText(String.valueOf(cantidad));

        holder.btnSumar.setOnClickListener(v -> {
            int nuevaCantidad = cantidad + 1;
            carrito.put(producto, nuevaCantidad);
            holder.txtCantidad.setText(String.valueOf(nuevaCantidad));
            notifyItemChanged(holder.getAdapterPosition());
            onCartUpdated.run();
        });

        holder.btnRestar.setOnClickListener(v -> {
            int nuevaCantidad = cantidad - 1;
            if (nuevaCantidad <= 0) {
                carrito.remove(producto);
                productos.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, productos.size()); // ✅ Actualizar rango restante
            } else {
                carrito.put(producto, nuevaCantidad);
                holder.txtCantidad.setText(String.valueOf(nuevaCantidad));
                notifyItemChanged(position);
            }
            onCartUpdated.run();
        });
    }

    @Override
    public int getItemCount() {
        Log.d("CartAdapter", "getItemCount: " + productos.size());
        return productos.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombre, txtPrecio, txtCantidad;
        Button btnRestar, btnSumar;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);
            txtCantidad = itemView.findViewById(R.id.txtCantidad);
            btnRestar = itemView.findViewById(R.id.btnRestar);
            btnSumar = itemView.findViewById(R.id.btnSumar);
        }
    }
}
