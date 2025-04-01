package com.example.crunchy_app.productos.comidas.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.productos.OnProductsSelectedListener;
import com.example.crunchy_app.productos.model.InfoProducto;
import com.example.crunchy_app.productos.comidas.adapter.FoodPagerAdapter;
import com.example.crunchy_app.R;
import com.example.crunchy_app.productos.model.Producto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ComidasFragment extends Fragment implements OnProductsSelectedListener {
    private ViewPager2 viewPager;
    private FoodPagerAdapter adapter;

    private List<Producto> foodList;
    private List<InfoProducto> infoList;

    private Map<Producto, Integer> selectedFoods;
    private int selectedFood;
    private String filter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comidas, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

        selectedFoods = new HashMap<>();

        AppDataBase db = AppDataBase.getInstance(requireContext());

        new Thread(() -> {
            if(filter == null) {
                infoList = db.infoProductoDao().getAll();
                foodList = db.productoDao().getComidas();
            }else{
                infoList = db.infoProductoDao().getAll();
                foodList = db.productoDao().searchComidas(filter);
            }
            requireActivity().runOnUiThread(() -> {
                adapter = new FoodPagerAdapter(requireActivity(), foodList, infoList, this);
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
        if (selected != null) {
            if (selectedFoods.containsKey(selected)) {
                selectedFoods.put(selected, selectedFoods.get(selected) + 1);
            } else {
                selectedFoods.put(selected, 1);
            }
        }

        Log.d("ComidasFragment", "Producto seleccionado: " + selectedFood);
    }

    private Producto findProductById(int foodId) {
        for (Producto producto : foodList) {
            if (producto.getIdProducto() == foodId) {
                return producto;
            }
            }
        return null;
    }

    public Map<Producto, Integer> getSelectedFoods() {
        return selectedFoods;
    }
}

