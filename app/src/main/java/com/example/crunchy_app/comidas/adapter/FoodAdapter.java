package com.example.crunchy_app.comidas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crunchy_app.R;
import com.example.crunchy_app.comidas.model.Food;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<Food> foodList;

    public FoodAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.name.setText(food.getName());
        holder.info.setText(food.getInfo());
        holder.price.setText(String.format("$%.2f", food.getPrice()));
    }

    @Override
    public int getItemCount() {
        return foodList.size();
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

