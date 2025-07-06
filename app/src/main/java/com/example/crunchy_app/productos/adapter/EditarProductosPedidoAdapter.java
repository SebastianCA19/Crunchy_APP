package com.example.crunchy_app.productos.adapter;

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

public class EditarProductosPedidoAdapter extends RecyclerView.Adapter<EditarProductosPedidoAdapter.ViewHolder> {

    public interface OnProductoEditadoListener {
        void onEliminarProducto(Producto producto);
    }

    private final List<ProductoDelPedido> productosDelPedido;
    private final List<Producto> productos;
    private final OnProductoEditadoListener listener;

    private RecyclerView recyclerView;

    public EditarProductosPedidoAdapter(List<ProductoDelPedido> productosDelPedido,
                                        List<Producto> productos,
                                        OnProductoEditadoListener listener) {
        this.productosDelPedido = productosDelPedido;
        this.productos = productos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_editar_producto_pedido, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductoDelPedido pdp = productosDelPedido.get(position);
        Producto producto = productos.stream()
                .filter(p -> p.getIdProducto().equals(pdp.getIdProducto()))
                .findFirst()
                .orElse(null);



        if (producto != null) {
            holder.txtNombreProducto.setText(producto.getNombreProducto());
            holder.editCantidad.setText(String.valueOf(pdp.getCantidad()));

            // ✅ Escuchar cambios en el EditText para actualizar cantidad en tiempo real
            holder.editCantidad.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        int cantidad = Integer.parseInt(s.toString());
                        pdp.setCantidad(cantidad);
                    } catch (NumberFormatException e) {
                        pdp.setCantidad(1);
                    }
                }

                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void afterTextChanged(Editable s) {}
            });

            // ✅ Eliminar producto (correctamente usando posición del adapter)
            holder.btnEliminar.setOnClickListener(v -> {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    ProductoDelPedido eliminado = productosDelPedido.remove(currentPosition);
                    notifyItemRemoved(currentPosition);
                    listener.onEliminarProducto(producto);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return productosDelPedido.size();
    }
    public List<ProductoDelPedido> getProductosActualizados() {
        for (int i = 0; i < productosDelPedido.size(); i++) {
            ProductoDelPedido pdp = productosDelPedido.get(i);
            try {
                // Busca la ViewHolder visible
                RecyclerView.ViewHolder vh = recyclerView.findViewHolderForAdapterPosition(i);
                if (vh instanceof ViewHolder) {
                    EditText editCantidad = ((ViewHolder) vh).editCantidad;
                    int nuevaCantidad = Integer.parseInt(editCantidad.getText().toString().trim());
                    pdp.setCantidad(nuevaCantidad);
                }
            } catch (Exception ignored) {}
        }
        return productosDelPedido;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
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
