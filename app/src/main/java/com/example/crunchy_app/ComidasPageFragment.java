package com.example.crunchy_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ComidasPageFragment extends Fragment {
    private static final String ARG_FOOD_LIST = "food_list";
    private List<Food> foodList;

    public static ComidasPageFragment newInstance(List<Food> foods) {
        ComidasPageFragment fragment = new ComidasPageFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FOOD_LIST, new ArrayList<>(foods));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            foodList = (List<Food>) getArguments().getSerializable(ARG_FOOD_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comidas_page, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new FoodAdapter(foodList));
        return view;
    }
}

