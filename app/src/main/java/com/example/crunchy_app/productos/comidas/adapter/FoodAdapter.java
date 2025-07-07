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
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.ValorAtributoProducto;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<Producto> foodList;
    private List<ValorAtributoProducto> atributosActivos;
    private int ID_CHICHARRON;
    private int ID_CHORIZO;
    private int ID_BOLLO;
    private OnProductsSelectedListener listener;

    public FoodAdapter(List<Producto> foodList, List<ValorAtributoProducto> atributosActivos,
                       int ID_CHICHARRON, int ID_CHORIZO, int ID_BOLLO,
                       OnProductsSelectedListener listener) {
        this.foodList = foodList;
        this.atributosActivos = atributosActivos;
        this.ID_CHICHARRON = ID_CHICHARRON;
        this.ID_CHORIZO = ID_CHORIZO;
        this.ID_BOLLO = ID_BOLLO;
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
        holder.name.setText(food.getNombreProducto().toUpperCase());
        holder.info.setText(getInfoProduct(food));
        holder.price.setText(String.format("$%.2f", food.getValorProducto()));
        holder.addButton.setTag(food.getIdProducto());
        holder.addButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFoodSelected(food.getIdProducto());
                listener.showInfoDialog(food.getNombreProducto());
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    private String getInfoProduct(Producto food) {
        int idProducto = food.getIdProducto();
        float chicharron = buscarValor(idProducto, ID_CHICHARRON);
        float chorizo = buscarValor(idProducto, ID_CHORIZO);
        float bollo = buscarValor(idProducto, ID_BOLLO);

        String chicharronStr = chicharron > 0 ? chicharron + "gr de chicharrón" : "";
        String chorizoStr = chorizo > 0 ? (int) chorizo + ((chorizo == 1) ? " unidad de chorizo" : " unidades de chorizo") : "";
        String bolloStr = bollo > 0 ? "Bollo de queso" : "";

        setValues(food, chorizo, chicharron, bollo);

        // Construir string dinámico
        if (chicharronStr.isEmpty() && chorizoStr.isEmpty()) {
            return bolloStr;
        } else if (chicharronStr.isEmpty() && bolloStr.isEmpty()) {
            return chorizoStr;
        } else if (chorizoStr.isEmpty() && bolloStr.isEmpty()) {
            return chicharronStr;
        } else if (chicharronStr.isEmpty()) {
            return String.join(" + ", chorizoStr, bolloStr);
        } else if (chorizoStr.isEmpty()) {
            return String.join(" + ", chicharronStr, bolloStr);
        } else if (bolloStr.isEmpty()) {
            return String.join(" + ", chicharronStr, chorizoStr);
        } else {
            return String.join(" + ", chicharronStr, chorizoStr, bolloStr);
        }
    }


    private float buscarValor(int idProducto, int idAtributo) {
        for (ValorAtributoProducto v : atributosActivos) {
            if (v.getIdProducto() == idProducto && v.getIdAtributoProducto() == idAtributo) {
                return v.getValorAtributoProducto();
            }
        }
        return 0f; // Valor por defecto si no se encuentra
    }


    private void setValues(Producto food, float chorizo, float chicharron, float bollo) {
        food.setCantidadChicharron(chicharron);
        food.setCantidadChorizo((int) chorizo);
        food.setCantidadBollo((int) bollo);
    }


    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView name, info, price;
        Button addButton;

        public FoodViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.food_name);
            info = itemView.findViewById(R.id.food_info);
            price = itemView.findViewById(R.id.food_price);
            addButton = itemView.findViewById(R.id.btn_add);
        }
    }
}
