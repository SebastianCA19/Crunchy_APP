package com.example.crunchy_app.productos;

import com.example.crunchy_app.productos.model.Producto;

public interface OnProductsSelectedListener {
    default void onFoodSelected(int foodId){

    }

    default void onDrinkSelected(int dinkId){

    }

    default void sendToCart(Producto producto){

    }

    void showInfoDialog(String productName);
}
