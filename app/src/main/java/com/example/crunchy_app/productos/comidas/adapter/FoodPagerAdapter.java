package com.example.crunchy_app.productos.comidas.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.crunchy_app.productos.OnProductsSelectedListener;
import com.example.crunchy_app.productos.comidas.fragment.ComidasPageFragment;
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.ValorAtributoProducto;

import java.util.ArrayList;
import java.util.List;

public class FoodPagerAdapter extends FragmentStateAdapter {
    private List<List<Producto>> foodPages;

    private List<ValorAtributoProducto> chicharronValues;

    private List<ValorAtributoProducto> chorizoValues;

    private List<ValorAtributoProducto> bolloValues;

    private OnProductsSelectedListener listener;

    public FoodPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Producto> foodList , List<ValorAtributoProducto> chicharronValues, List<ValorAtributoProducto> chorizoValues, List<ValorAtributoProducto> bolloValues, OnProductsSelectedListener listener) {
        super(fragmentActivity);
        this.foodPages = partitionList(foodList, 5);
        this.chicharronValues = chicharronValues;
        this.chorizoValues = chorizoValues;
        this.bolloValues = bolloValues;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ComidasPageFragment.newInstance(foodPages.get(position), chicharronValues, chorizoValues, bolloValues, listener);
    }

    @Override
    public int getItemCount() {
        return foodPages.size();
    }

    private List<List<Producto>> partitionList(List<Producto> list, int size) {
        List<List<Producto>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return partitions;
    }
}
