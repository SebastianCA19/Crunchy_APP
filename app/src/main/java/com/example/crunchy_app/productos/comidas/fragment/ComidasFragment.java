package com.example.crunchy_app.productos.comidas.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.productos.DAO.ProductoDao;
import com.example.crunchy_app.productos.DAO.ValorAtributoProductoDao;
import com.example.crunchy_app.productos.OnProductsSelectedListener;
import com.example.crunchy_app.productos.comidas.adapter.FoodPagerAdapter;
import com.example.crunchy_app.R;
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.ValorAtributoProducto;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ComidasFragment extends Fragment implements OnProductsSelectedListener {
    private ViewPager2 viewPager;
    private FoodPagerAdapter adapter;

    private List<Producto> foodList;

    private List<ValorAtributoProducto> chicharronValues;

    private List<ValorAtributoProducto> chorizoValues;

    private List<ValorAtributoProducto> bolloValues;

    private int selectedFood;
    private String filter;

    private ProductoDao productoDao;
    private ValorAtributoProductoDao atributoProductoDao;

    public OnProductsSelectedListener listener;
    public ComidasFragment(OnProductsSelectedListener listener) {
        this.listener = listener;
    }

    public ComidasFragment(String filter, OnProductsSelectedListener listener) {
        this.filter = filter;
        this.listener = listener;
    }

    public ComidasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comidas, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

        AppDataBase db = AppDataBase.getInstance(requireContext());
        productoDao = db.productoDao();
        atributoProductoDao = db.valorAtributoProductoDao();

        new Thread(() -> {
            if(filter == null) {
                foodList = productoDao.getComidas();
            }else{
                filter = formatFilter(filter);
                foodList = productoDao.searchComidas(filter);
            }
            chicharronValues = atributoProductoDao.getCantidadChicharron();
            chorizoValues = atributoProductoDao.getCantidadChorizo();
            bolloValues = atributoProductoDao.getCantidadBollo();
            requireActivity().runOnUiThread(() -> {
                adapter = new FoodPagerAdapter(requireActivity(), foodList, chicharronValues, chorizoValues, bolloValues, this);
                viewPager.setAdapter(adapter);
            });
        }).start();

        return view;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public void onFoodSelected(int foodId) {
        selectedFood = foodId;
        Producto selected = findProductById(foodId);
        listener.sendToCart(selected);

        Log.d("ComidasFragment", "Producto seleccionado: " + selectedFood);
    }

    @Override
    public void showInfoDialog(String productName) {
        Snackbar.make(getView(), "Has agregado " + productName.toUpperCase() + " a tu carrito", Snackbar.LENGTH_SHORT)
                .show();
    }

    private Producto findProductById(int foodId) {
        for (Producto producto : foodList) {
            if (producto.getIdProducto() == foodId) {
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

