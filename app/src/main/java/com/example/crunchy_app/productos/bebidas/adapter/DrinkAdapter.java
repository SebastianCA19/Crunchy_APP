package com.example.crunchy_app.productos.bebidas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crunchy_app.R;
import com.example.crunchy_app.productos.OnProductsSelectedListener;
import com.example.crunchy_app.productos.model.Producto;

import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {
    private List<Producto> drinkList;

    private OnProductsSelectedListener listener;

    public DrinkAdapter(List<Producto> drinkList, OnProductsSelectedListener listener){
        this.drinkList = drinkList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drink, parent, false);
        return new DrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkAdapter.DrinkViewHolder holder, int position) {
        Producto drink = drinkList.get(position);
        if(!drink.isFixed()){
            divideName(drink);
        }
        fixName(drink);
        holder.name.setText(drink.getNombreProducto());
        holder.info.setText(drink.getInfoString());
        holder.price.setText(String.format("$%.2f", drink.getPrecio()));
        holder.addButton.setTag(drink.getIdProducto());
        holder.addButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDrinkSelected(drink.getIdProducto());
            }
        });
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }

    private void divideName(Producto drink){
        if(!drink.isFixed()){
            String[] parts = drink.getNombreProducto().split(" ");

            if (parts.length > 1) {
                drink.setNombreProducto(parts[0]);
                drink.setInfoString(parts[1]);
            }else{
                drink.setNombreProducto(parts[0]);
                drink.setInfoString("");
            }

            drink.setFixed(true);
        }
    }

    private void fixName(Producto drink){
        String[] parts = drink.getNombreProducto().split("-");
        String name = "";
        for (String part : parts) {
            name += part.toUpperCase() + " ";
        }
        drink.setNombreProducto(name);
    }

    public static class DrinkViewHolder extends RecyclerView.ViewHolder {
        TextView name, info, price;
        Button addButton;
        public DrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.drink_name);
            info = itemView.findViewById(R.id.drink_info);
            price = itemView.findViewById(R.id.drink_price);
            addButton = itemView.findViewById(R.id.btn_add_chorizo);
        }
    }
}
