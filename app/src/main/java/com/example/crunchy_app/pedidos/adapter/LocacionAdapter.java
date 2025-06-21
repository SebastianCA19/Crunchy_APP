package com.example.crunchy_app.pedidos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crunchy_app.R;
import com.example.crunchy_app.pedidos.model.Locacion;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class LocacionAdapter extends RecyclerView.Adapter<LocacionAdapter.LocacionViewHolder> {

    private final List<Locacion> locaciones;
    private final OnLocacionActionListener listener;

    public interface OnLocacionActionListener {
        void onEditar(Locacion locacion);
        void onEliminar(Locacion locacion);
    }

    public LocacionAdapter(List<Locacion> locaciones, OnLocacionActionListener listener) {
        this.locaciones = locaciones;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LocacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_locacion, parent, false);
        return new LocacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocacionViewHolder holder, int position) {
        Locacion locacion = locaciones.get(position);

        holder.txtNombreLocacion.setText(locacion.getNombreLocacion());

        String valor = NumberFormat.getCurrencyInstance(new Locale("es", "CO")).format(locacion.getValorDomicilio());
        holder.txtValorDomicilio.setText("Domicilio: " + valor);

        holder.btnEditar.setOnClickListener(v -> listener.onEditar(locacion));
        holder.btnEliminar.setOnClickListener(v -> listener.onEliminar(locacion));
    }

    @Override
    public int getItemCount() {
        return locaciones.size();
    }

    static class LocacionViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreLocacion, txtValorDomicilio;
        Button btnEditar, btnEliminar;

        public LocacionViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreLocacion = itemView.findViewById(R.id.txtNombreLocacion);
            txtValorDomicilio = itemView.findViewById(R.id.txtValorDomicilio);
            btnEditar = itemView.findViewById(R.id.btnEditarLocacion);
            btnEliminar = itemView.findViewById(R.id.btnEliminarLocacion);
        }
    }
}