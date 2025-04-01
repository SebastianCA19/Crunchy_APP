package com.example.crunchy_app.productos;

public interface OnProductsSelectedListener {
    default void onFoodSelected(int foodId){

    }

    default void onDrinkSelected(int dinkId){

    }

    void showInfoDialog(String productName);
}
