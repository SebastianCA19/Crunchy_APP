package com.example.crunchy_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CompraAdapter extends RecyclerView.Adapter<CompraAdapter.CompraViewHolder> {
    private List<Compra> listaCompras;

    public CompraAdapter(List<Compra> listaCompras) {
        this.listaCompras = listaCompras;
    }

    @NonNull
    @Override
    public CompraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_compra, parent, false);
        return new CompraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompraViewHolder holder, int position) {
        Compra compra = listaCompras.get(position);
        holder.tvNombreCliente.setText(compra.getNombreCliente());
        holder.tvComida.setText("Comida: " + compra.getComida());
        holder.tvBebida.setText("Bebida: " + (compra.getBebida().isEmpty() ? "No compró" : compra.getBebida()));
        holder.tvTotal.setText("Total: $" + compra.getTotal());
        holder.tvFecha.setText(compra.getFecha());
    }

    @Override
    public int getItemCount() {
        return listaCompras.size();
    }

    static class CompraViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreCliente, tvComida, tvBebida, tvTotal, tvFecha;

        public CompraViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreCliente = itemView.findViewById(R.id.tvNombreCliente);
            tvComida = itemView.findViewById(R.id.tvComida);
            tvBebida = itemView.findViewById(R.id.tvBebida);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvFecha = itemView.findViewById(R.id.tvFecha);
        }
    }
}
