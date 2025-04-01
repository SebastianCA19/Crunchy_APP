package com.example.crunchy_app.productos.comidas.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crunchy_app.R;
import com.example.crunchy_app.productos.comidas.adapter.FoodAdapter;
import com.example.crunchy_app.productos.comidas.adapter.OnFoodSelectedListener;
import com.example.crunchy_app.productos.model.InfoProducto;
import com.example.crunchy_app.productos.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class ComidasPageFragment extends Fragment {
    private static final String ARG_FOOD_LIST = "food_list";
    private List<Producto> foodList;
    private List<InfoProducto> infoList;

    private OnFoodSelectedListener listener;

    public ComidasPageFragment(List<InfoProducto> infoList, OnFoodSelectedListener listener) {
        this.infoList = infoList;
        this.listener = listener;
    }

    public static ComidasPageFragment newInstance(List<Producto> foods, List<InfoProducto> infoList, OnFoodSelectedListener listener) {
        ComidasPageFragment fragment = new ComidasPageFragment(infoList, listener);
        Bundle args = new Bundle();
        args.putSerializable(ARG_FOOD_LIST, new ArrayList<>(foods));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            foodList = (List<Producto>) getArguments().getSerializable(ARG_FOOD_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comidas_page, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new FoodAdapter(foodList,infoList, listener));
        return view;
    }
}

