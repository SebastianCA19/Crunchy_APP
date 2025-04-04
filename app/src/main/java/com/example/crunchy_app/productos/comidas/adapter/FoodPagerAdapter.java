package com.example.crunchy_app.productos.comidas.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.crunchy_app.productos.OnProductsSelectedListener;
import com.example.crunchy_app.productos.comidas.fragment.ComidasPageFragment;
import com.example.crunchy_app.productos.model.InfoProducto;
import com.example.crunchy_app.productos.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class FoodPagerAdapter extends FragmentStateAdapter {
    private List<List<Producto>> foodPages;
    private List<InfoProducto> infoList;

    private OnProductsSelectedListener listener;

    public FoodPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Producto> foodList , List<InfoProducto> infoList, OnProductsSelectedListener listener) {
        super(fragmentActivity);
        this.foodPages = partitionList(foodList, 5);
        this.infoList = infoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ComidasPageFragment.newInstance(foodPages.get(position), infoList, listener);
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
