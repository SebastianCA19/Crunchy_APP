package com.example.crunchy_app.pedidos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crunchy_app.R;
import com.example.crunchy_app.pedidos.model.Pedido;

import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {
    private List<Pedido> listaPedidos;

    public PedidoAdapter(List<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido, parent, false);
        return new PedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        Pedido pedido = listaPedidos.get(position);

        holder.tvNombreCliente.setText(pedido.getNombreCliente() + " " + pedido.getApellidoCliente() );
        holder.tvComida.setText("Comida: " + pedido.getIdTipoPedido());
        holder.tvBebida.setText("Bebida: " + (pedido.getBebida().isEmpty() ? "No compró" : pedido.getBebida()));
        holder.tvTotal.setText("Total: $" + pedido);
        holder.tvFecha.setText("Fecha: " + pedido.getFecha()+ "/ Hora: " + pedido.getHora());
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    static class PedidoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreCliente, tvComida, tvBebida, tvTotal, tvFecha;

        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreCliente = itemView.findViewById(R.id.tvNombreCliente);
            tvComida = itemView.findViewById(R.id.tvComida);
            tvBebida = itemView.findViewById(R.id.tvBebida);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvFecha = itemView.findViewById(R.id.tvFecha);
        }
    }
}
