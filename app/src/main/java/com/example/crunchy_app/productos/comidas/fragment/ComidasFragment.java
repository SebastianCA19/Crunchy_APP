package com.example.crunchy_app.productos.comidas.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.productos.model.InfoProducto;
import com.example.crunchy_app.secciones.adapter.MenuPagerAdapter;
import com.example.crunchy_app.R;
import com.example.crunchy_app.productos.model.Producto;

import java.util.Arrays;
import java.util.List;


public class ComidasFragment extends Fragment {
    private ViewPager2 viewPager;
    private MenuPagerAdapter adapter;

    private List<Producto> foodList;
    private List<InfoProducto> infoList;

    private String filter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comidas, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

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
                adapter = new MenuPagerAdapter(requireActivity(), foodList, infoList);
                viewPager.setAdapter(adapter);
            });
        }).start();


        return view;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}

