package com.example.crunchy_app.productos.comidas.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crunchy_app.secciones.adapter.MenuPagerAdapter;
import com.example.crunchy_app.R;
import com.example.crunchy_app.productos.model.Producto;

import java.util.Arrays;
import java.util.List;


public class ComidasFragment extends Fragment {
    private ViewPager2 viewPager;
    private MenuPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comidas, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

        List<Producto> foodList = Arrays.asList(
                new Producto("Combo 2", "Pollo + Papas fritas", 20000),
                new Producto("Combo 3", "Carne asada + Arepa", 22000),
                new Producto("Combo 4", "Pechuga + Ensalada", 18000),
                new Producto("Combo 5", "Pizza personal + Gaseosa", 25000),
                new Producto("Combo 6", "Sushi de salmón", 30000),
                new Producto("Combo 7", "Hamburguesa doble carne", 28000),
                new Producto("Combo 8", "Pasta Alfredo", 19000),
                new Producto("Combo 9", "Arepa rellena de queso", 15000),
                new Producto("Combo 10", "Bandeja paisa", 35000)
        );

        adapter = new MenuPagerAdapter(requireActivity(), foodList);
        viewPager.setAdapter(adapter);

        return view;
    }
}

