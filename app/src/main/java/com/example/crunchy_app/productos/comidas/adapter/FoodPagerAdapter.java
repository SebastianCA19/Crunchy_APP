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

    private final List<List<Producto>> foodPages;
    private final List<ValorAtributoProducto> atributosActivos;
    private final int ID_CHICHARRON;
    private final int ID_CHORIZO;
    private final int ID_BOLLO;
    private final OnProductsSelectedListener listener;

    public FoodPagerAdapter(
            @NonNull FragmentActivity fragmentActivity,
            List<Producto> foodList,
            List<ValorAtributoProducto> atributosActivos,
            int ID_CHICHARRON,
            int ID_CHORIZO,
            int ID_BOLLO,
            OnProductsSelectedListener listener
    ) {
        super(fragmentActivity);
        this.foodPages = partitionList(foodList, 5);
        this.atributosActivos = atributosActivos;
        this.ID_CHICHARRON = ID_CHICHARRON;
        this.ID_CHORIZO = ID_CHORIZO;
        this.ID_BOLLO = ID_BOLLO;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ComidasPageFragment.newInstance(
                foodPages.get(position),
                atributosActivos,
                ID_CHICHARRON,
                ID_CHORIZO,
                ID_BOLLO,
                listener
        );
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
