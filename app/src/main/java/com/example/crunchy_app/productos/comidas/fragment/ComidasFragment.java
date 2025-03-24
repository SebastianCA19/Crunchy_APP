package com.example.crunchy_app.productos.comidas.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crunchy_app.MenuPagerAdapter;
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
                new Producto("Combo 1",1, 7000,  2)
        );

        adapter = new MenuPagerAdapter(requireActivity(), foodList);
        viewPager.setAdapter(adapter);

        return view;
    }
}

