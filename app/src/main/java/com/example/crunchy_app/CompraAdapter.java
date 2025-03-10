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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historial, parent, false);
        return new CompraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompraViewHolder holder, int position) {
        Compra compra = listaCompras.get(position);
        holder.tvCliente.setText("Cliente: " + compra.getCliente());
        holder.tvComida.setText("Comida: " + compra.getComida());
        holder.tvBebida.setText("Bebida: " + compra.getBebida());
        holder.tvTotal.setText("Total: " + compra.getTotal());
        holder.tvFecha.setText("Fecha: " + compra.getFecha());
    }

    @Override
    public int getItemCount() {
        return listaCompras.size();
    }

    public static class CompraViewHolder extends RecyclerView.ViewHolder {
        TextView tvCliente, tvComida, tvBebida, tvTotal, tvFecha;

        public CompraViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCliente = itemView.findViewById(R.id.tvCliente);
            tvComida = itemView.findViewById(R.id.tvComida);
            tvBebida = itemView.findViewById(R.id.tvBebida);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvFecha = itemView.findViewById(R.id.tvFecha);
        }
    }
}
