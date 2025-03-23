package com.example.crunchy_app;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.crunchy_app.comidas.fragment.ComidasPageFragment;
import com.example.crunchy_app.comidas.model.Food;

import java.util.ArrayList;
import java.util.List;

public class MenuPagerAdapter extends FragmentStateAdapter {
    private List<List<Food>> foodPages;

    public MenuPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Food> foodList) {
        super(fragmentActivity);
        this.foodPages = partitionList(foodList, 5);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ComidasPageFragment.newInstance(foodPages.get(position));
    }

    @Override
    public int getItemCount() {
        return foodPages.size();
    }

    private List<List<Food>> partitionList(List<Food> list, int size) {
        List<List<Food>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return partitions;
    }
}
