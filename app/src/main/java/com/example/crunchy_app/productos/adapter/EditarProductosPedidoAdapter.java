package com.example.crunchy_app.productos.adapter;

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

public class EditarProductosPedidoAdapter extends RecyclerView.Adapter<EditarProductosPedidoAdapter.ViewHolder> {

    public interface OnProductoEditadoListener {
        void onEliminarProducto(Producto producto);
    }

    private final List<ProductoDelPedido> productosDelPedido;
    private final List<Producto> productos;
    private final OnProductoEditadoListener listener;

    public EditarProductosPedidoAdapter(List<ProductoDelPedido> productosDelPedido,
                                        List<Producto> productos,
                                        OnProductoEditadoListener listener) {
        this.productosDelPedido = productosDelPedido;
        this.productos = productos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EditarProductosPedidoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_editar_producto_pedido, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EditarProductosPedidoAdapter.ViewHolder holder, int position) {
        ProductoDelPedido pdp = productosDelPedido.get(position);
        Producto producto = productos.stream().filter(p -> p.getIdProducto().equals(pdp.getIdProducto())).findFirst().orElse(null);

        if (producto != null) {
            holder.txtNombreProducto.setText(producto.getNombreProducto());
            holder.editCantidad.setText(String.valueOf(pdp.getCantidad()));

            holder.btnEliminar.setOnClickListener(v -> {
                productosDelPedido.remove(position);
                notifyItemRemoved(position);
                listener.onEliminarProducto(producto);
            });
        }
    }

    @Override
    public int getItemCount() {
        return productosDelPedido.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreProducto;
        EditText editCantidad;
        Button btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreProducto = itemView.findViewById(R.id.txtNombreProductoEditar);
            editCantidad = itemView.findViewById(R.id.editCantidadProducto);
            btnEliminar = itemView.findViewById(R.id.btnEliminarProducto);
        }
    }
}