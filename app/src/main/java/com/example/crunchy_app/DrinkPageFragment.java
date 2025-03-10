package com.example.crunchy_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DrinkPageFragment extends Fragment {
    private static final String ARG_DRINK_LIST = "drink_list";
    private List<Drink> drinkList;

    public static DrinkPageFragment newInstance(List<Drink> drinks) {
        DrinkPageFragment fragment = new DrinkPageFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DRINK_LIST, new ArrayList<>(drinks));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            drinkList = (List<Drink>) getArguments().getSerializable(ARG_DRINK_LIST);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bebidas_page, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new DrinkAdapter(drinkList));
        return view;
    }
}
