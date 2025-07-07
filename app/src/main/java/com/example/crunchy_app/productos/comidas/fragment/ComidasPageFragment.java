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
import com.example.crunchy_app.productos.OnProductsSelectedListener;
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.ValorAtributoProducto;

import java.util.ArrayList;
import java.util.List;

public class ComidasPageFragment extends Fragment {
    private static final String ARG_FOOD_LIST = "food_list";

    private List<Producto> foodList;
    private List<ValorAtributoProducto> atributosActivos;
    private int ID_CHICHARRON;
    private int ID_CHORIZO;
    private int ID_BOLLO;

    private OnProductsSelectedListener listener;

    public ComidasPageFragment(List<ValorAtributoProducto> atributosActivos, int idChicharron, int idChorizo, int idBollo, OnProductsSelectedListener listener) {
        this.atributosActivos = atributosActivos;
        this.ID_CHICHARRON = idChicharron;
        this.ID_CHORIZO = idChorizo;
        this.ID_BOLLO = idBollo;
        this.listener = listener;
    }

    public static ComidasPageFragment newInstance(
            List<Producto> foods,
            List<ValorAtributoProducto> atributosActivos,
            int idChicharron,
            int idChorizo,
            int idBollo,
            OnProductsSelectedListener listener
    ) {
        ComidasPageFragment fragment = new ComidasPageFragment(atributosActivos, idChicharron, idChorizo, idBollo, listener);
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
        recyclerView.setAdapter(new FoodAdapter(
                foodList,
                atributosActivos,
                ID_CHICHARRON,
                ID_CHORIZO,
                ID_BOLLO,
                listener
        ));
        return view;
    }
}
