package com.example.crunchy_app.productos.bebidas.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.productos.DAO.ProductoDao;
import com.example.crunchy_app.productos.DAO.ValorAtributoProductoDao;
import com.example.crunchy_app.productos.OnProductsSelectedListener;
import com.example.crunchy_app.productos.bebidas.adapter.DrinksPagerAdapter;
import com.example.crunchy_app.R;
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.ValorAtributoProducto;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BebidasFragment extends Fragment implements OnProductsSelectedListener {
    private ViewPager2 viewPager;
    private DrinksPagerAdapter adapter;

    private List<Producto> drinksList;

    private List<ValorAtributoProducto> mlValues;
    private int selectedDrink;

    private OnProductsSelectedListener listener;

    private String filter;

    private ProductoDao productoDao;
    private ValorAtributoProductoDao atributoProductoDao;

    public BebidasFragment(OnProductsSelectedListener listener) {
        this.listener = listener;
    }

    public BebidasFragment(String filter, OnProductsSelectedListener listener) {
        this.filter = filter;
        this.listener = listener;
    }

    public BebidasFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bebidas, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

        AppDataBase db = AppDataBase.getInstance(requireContext());
        productoDao = db.productoDao();
        atributoProductoDao = db.valorAtributoProductoDao();

        new Thread(() -> {
            if(filter == null) {
                drinksList = productoDao.getBebidas();
            }else{
                filter = formatFilter(filter);
                Log.d("BebidasFragment", "Filter: " + filter);
                drinksList = productoDao.searchBebidas(filter);
            }
            mlValues = atributoProductoDao.getVolumenMl();
            requireActivity().runOnUiThread(() -> {
                adapter = new DrinksPagerAdapter(requireActivity(), drinksList,mlValues, this);
                viewPager.setAdapter(adapter);
            });
        }).start();

        return view;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public void onDrinkSelected(int dinkId) {
        selectedDrink = dinkId;
        Producto selected = findProductById(dinkId);
        listener.sendToCart(selected);
        Log.d("BebidasFragment", "Producto seleccionado: " + selectedDrink);
    }

    @Override
    public void showInfoDialog(String productName) {
        Snackbar.make(getView(), "Has agregado " + productName.toUpperCase() + " a tu carrito", Snackbar.LENGTH_SHORT)
                .show();
    }

    private Producto findProductById(int drinkId) {
        for (Producto producto : drinksList) {
            if (producto.getIdProducto() == drinkId) {
                return producto;
            }
        }
        return null;
    }

    private String formatFilter(String filter) {
        filter = filter.replace(" ", "-");
        return filter;
    }
}