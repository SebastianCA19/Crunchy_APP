package com.example.crunchy_app.bebidas.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crunchy_app.bebidas.adapter.DrinksPagerAdapter;
import com.example.crunchy_app.R;
import com.example.crunchy_app.bebidas.model.Drink;

import java.util.Arrays;
import java.util.List;

public class BebidasFragment extends Fragment {
    private ViewPager2 viewPager;
    private DrinksPagerAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bebidas, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        List<Drink> drinksList = Arrays.asList(
                new Drink("Bebida 1", "Jugo de naranja natural", 5000),
                new Drink("Bebida 2", "Limonada de coco", 7000),
                new Drink("Bebida 3", "Té helado", 6000),
                new Drink("Bebida 4", "Malteada de chocolate", 10000),
                new Drink("Bebida 5", "Batido de fresa", 9000),
                new Drink("Bebida 6", "Café americano", 4000),
                new Drink("Bebida 7", "Chocolate caliente", 5500),
                new Drink("Bebida 8", "Agua mineral", 3000),
                new Drink("Bebida 9", "Gaseosa en lata", 4500),
                new Drink("Bebida 10", "Michelada con limón", 12000)
        );
        
        adapter = new DrinksPagerAdapter(requireActivity(), drinksList);
        viewPager.setAdapter(adapter);

        return view;
    }
}