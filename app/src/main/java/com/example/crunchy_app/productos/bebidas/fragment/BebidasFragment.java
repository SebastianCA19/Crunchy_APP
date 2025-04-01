package com.example.crunchy_app.productos.bebidas.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.productos.bebidas.adapter.DrinksPagerAdapter;
import com.example.crunchy_app.R;
import com.example.crunchy_app.productos.model.Producto;

import java.util.List;

public class BebidasFragment extends Fragment {
    private ViewPager2 viewPager;
    private DrinksPagerAdapter adapter;

    private List<Producto> drinksList;

    private String filter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bebidas, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

        AppDataBase db = AppDataBase.getInstance(requireContext());

        new Thread(() -> {
            if(filter == null) {
                drinksList = db.productoDao().getBebidas();
            }else{
                drinksList = db.productoDao().searchBebidas(filter);
            }
            requireActivity().runOnUiThread(() -> {
                adapter = new DrinksPagerAdapter(requireActivity(), drinksList);
                viewPager.setAdapter(adapter);
            });
        }).start();

        return view;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}