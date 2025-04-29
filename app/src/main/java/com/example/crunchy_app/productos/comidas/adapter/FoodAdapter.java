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

    private List<ValorAtributoProducto> chicharronValues;
    private List<ValorAtributoProducto> chorizoValues;
    private List<ValorAtributoProducto> bolloValues;

    private OnProductsSelectedListener listener;

    public FoodAdapter(List<Producto> foodList, List<ValorAtributoProducto> chicharronValues, List<ValorAtributoProducto> chorizoValues, List<ValorAtributoProducto> bolloValues, OnProductsSelectedListener listener) {
        this.foodList = foodList;
        this.listener = listener;
        this.chicharronValues = chicharronValues;
        this.chorizoValues = chorizoValues;
        this.bolloValues = bolloValues;
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
            }
            listener.showInfoDialog(food.getNombreProducto());
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }


    private String getInfoProduct(Producto food){
        int idProducto = food.getIdProducto();
        float chorizo = 0;
        float chicharron = 0;
        float bollo = 0;
        String chorizoString = "";
        String chicharronString = "";
        String bolloString = "";
        for(ValorAtributoProducto valorAtributoProducto : chicharronValues){
            if(valorAtributoProducto.getIdProducto() == idProducto){
                chicharron = valorAtributoProducto.getValorAtributoProducto();
                chicharronString = chicharron + "gr de chicharron";
                break;
            }
        }
        for(ValorAtributoProducto valorAtributoProducto : chorizoValues){
            if(valorAtributoProducto.getIdProducto() == idProducto){
                chorizo = valorAtributoProducto.getValorAtributoProducto();
                String valueString = chorizo == 1 ? "unidad" : "unidades";
                chorizoString = (int) chorizo + " "+ valueString +" de chorizo";
                break;
            }
        }
        for(ValorAtributoProducto valorAtributoProducto : bolloValues){
            if(valorAtributoProducto.getIdProducto() == idProducto){
                bolloString = "Bollo de queso";
                break;
            }
        }

        setValues(food, chorizo, chicharron, bollo);

        if(chorizoString.isEmpty()){
            return String.join(" + ",chicharronString, bolloString);
        } else if (chicharronString.isEmpty()) {
            return String.join(" + ",chorizoString, bolloString);
        } else if(bolloString.isEmpty()){
            return String.join(" + ",chorizoString, chicharronString);
        }
        return String.join(" + ",chicharronString, chorizoString, bolloString);
    }

    private void setValues(Producto food, float chorizo, float chicharron, float bollo){
        food.setCantidadChicharron((int) chicharron);
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

