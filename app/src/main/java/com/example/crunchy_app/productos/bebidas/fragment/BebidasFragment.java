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
import com.example.crunchy_app.productos.OnProductsSelectedListener;
import com.example.crunchy_app.productos.bebidas.adapter.DrinksPagerAdapter;
import com.example.crunchy_app.R;
import com.example.crunchy_app.productos.model.Producto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BebidasFragment extends Fragment implements OnProductsSelectedListener {
    private ViewPager2 viewPager;
    private DrinksPagerAdapter adapter;

    private List<Producto> drinksList;

    private Map<Producto, Integer> selectedDrinks;
    private int selectedDrink;

    private String filter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bebidas, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

        selectedDrinks = new HashMap<>();

        AppDataBase db = AppDataBase.getInstance(requireContext());

        new Thread(() -> {
            if(filter == null) {
                drinksList = db.productoDao().getBebidas();
            }else{
                drinksList = db.productoDao().searchBebidas(filter);
            }
            requireActivity().runOnUiThread(() -> {
                adapter = new DrinksPagerAdapter(requireActivity(), drinksList, this);
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
        if (selected != null) {
            if (selectedDrinks.containsKey(selected)) {
                selectedDrinks.put(selected, selectedDrinks.get(selected) + 1);
            } else {
                selectedDrinks.put(selected, 1);
            }
        }
        Log.d("BebidasFragment", "Producto seleccionado: " + selectedDrink);
    }

    private Producto findProductById(int drinkId) {
        for (Producto producto : drinksList) {
            if (producto.getIdProducto() == drinkId) {
                return producto;
            }
        }
        return null;
    }

    public Map<Producto, Integer> getSelectedDrinks() {
        return selectedDrinks;
    }
}