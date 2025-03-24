package com.example.crunchy_app.productos.bebidas.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.crunchy_app.productos.bebidas.fragment.DrinkPageFragment;
import com.example.crunchy_app.productos.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class DrinksPagerAdapter extends FragmentStateAdapter {
    private List<List<Producto>> drinkPages;

    public DrinksPagerAdapter(FragmentActivity fragmentActivity, List<Producto> drinkList) {
        super(fragmentActivity);
        this.drinkPages = partitionList(drinkList, 5);
    }


    private List<List<Producto>> partitionList(List<Producto> list, int size) {
        List<List<Producto>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return partitions;
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return DrinkPageFragment.newInstance(drinkPages.get(position));
    }

    @Override
    public int getItemCount() {
        return drinkPages.size();
    }
}
