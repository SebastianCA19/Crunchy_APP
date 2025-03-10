package com.example.crunchy_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {
    private List<Drink> drinkList;

    public DrinkAdapter(List<Drink> drinkList){
        this.drinkList = drinkList;
    }


    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drink, parent, false);
        return new DrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkAdapter.DrinkViewHolder holder, int position) {
        Drink drink = drinkList.get(position);
        holder.name.setText(drink.getName());
        holder.info.setText(drink.getInfo());
        holder.price.setText(String.format("$%.2f", drink.getPrice()));
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
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
