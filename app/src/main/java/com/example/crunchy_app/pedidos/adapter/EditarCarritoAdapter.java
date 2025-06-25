package com.example.crunchy_app.pedidos.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crunchy_app.R;
import com.example.crunchy_app.pedidos.model.ProductoDelPedido;
import com.example.crunchy_app.productos.model.Producto;

import java.util.List;

public class EditarCarritoAdapter extends RecyclerView.Adapter<EditarCarritoAdapter.ViewHolder> {
    private final List<ProductoDelPedido> productosDelPedido;
    private final List<Producto> productos;
    private final Context context;

    public EditarCarritoAdapter(Context context, List<ProductoDelPedido> productosDelPedido, List<Producto> productos) {
        this.context = context;
        this.productosDelPedido = productosDelPedido;
        this.productos = productos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_producto_editar, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductoDelPedido pdp = productosDelPedido.get(position);
        Producto producto = null;

        for (Producto p : productos) {
            if (p.getIdProducto().equals(pdp.getIdProducto())) {
                producto = p;
                break;
            }
        }

        if (producto != null) {
            holder.txtNombre.setText(producto.getNombreProducto());
        }

        holder.etCantidad.setText(String.valueOf(pdp.getCantidad()));

        holder.btnEliminar.setOnClickListener(v -> {
            productosDelPedido.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, productosDelPedido.size());
        });

        holder.etCantidad.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                try {
                    int cantidad = Integer.parseInt(s.toString());
                    pdp.setCantidad(cantidad);
                } catch (NumberFormatException e) {
                    pdp.setCantidad(0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productosDelPedido.size();
    }

    public List<ProductoDelPedido> getProductosEditados() {
        return productosDelPedido;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre;
        EditText etCantidad;
        Button btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombreProducto);
            etCantidad = itemView.findViewById(R.id.etCantidad);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
