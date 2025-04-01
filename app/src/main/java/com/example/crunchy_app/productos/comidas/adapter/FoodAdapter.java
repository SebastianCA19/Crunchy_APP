package com.example.crunchy_app.productos.comidas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crunchy_app.R;
import com.example.crunchy_app.productos.OnProductsSelectedListener;
import com.example.crunchy_app.productos.model.InfoProducto;
import com.example.crunchy_app.productos.model.Producto;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<Producto> foodList;
    private List<InfoProducto> infoList;

    private OnProductsSelectedListener listener;

    public FoodAdapter(List<Producto> foodList, List<InfoProducto> infoList, OnProductsSelectedListener listener) {
        this.foodList = foodList;
        this.infoList = infoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Producto food = foodList.get(position);
        InfoProducto info = getInfoById(food.getIdInfoProducto());
        holder.name.setText(food.getNombreProducto().toUpperCase());
        holder.info.setText(getInfoText(info));
        holder.price.setText(String.format("$%.2f", food.getPrecio()));
        holder.addButton.setTag(food.getIdProducto());
        holder.addButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFoodSelected(food.getIdProducto());
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    private InfoProducto getInfoById(Integer idInfoProducto) {
        for (InfoProducto info : infoList) {
            if (info.getIdInfoProducto().equals(idInfoProducto)) {
                return info;
            }
        }
        return null;
    }

    private String getInfoText(InfoProducto info) {
        StringBuilder infoText = new StringBuilder();
        int chorizo = info.getCantidadChorizo();
        float chicharron = info.getCantidadChicharronGramos();
        float bollo = info.getCantidadBollo();
        if(chicharron > 0) {
            infoText.append(String.format("%.2f gramos de chicharron", chicharron));
        }
        if(chorizo > 0) {
            if(infoText.length() > 0) {
                infoText.append(", ");
            }
            infoText.append(String.format("%d chorizos", chorizo));
        }
        if(bollo > 0) {
            if(infoText.length() > 0) {
                infoText.append(", ");
            }
            infoText.append(String.format("%.1f bollos", bollo));
        }
        return infoText.toString();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {

        TextView name, info, price;
        Button addButton;

        public FoodViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.food_name);
            info = itemView.findViewById(R.id.food_info);
            price = itemView.findViewById(R.id.food_price);
            addButton = itemView.findViewById(R.id.btn_add_chorizo);
        }
    }
}

