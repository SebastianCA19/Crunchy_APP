package com.example.crunchy_app.productos.comidas.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.productos.DAO.ProductoDao;
import com.example.crunchy_app.productos.DAO.ValorAtributoProductoDao;
import com.example.crunchy_app.productos.OnProductsSelectedListener;
import com.example.crunchy_app.productos.comidas.adapter.FoodPagerAdapter;
import com.example.crunchy_app.R;
import com.example.crunchy_app.productos.model.AtributoProducto;
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.ValorAtributoProducto;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ComidasFragment extends Fragment implements OnProductsSelectedListener {
    private ViewPager2 viewPager;
    private FoodPagerAdapter adapter;

    private List<Producto> foodList;
    private List<ValorAtributoProducto> atributosActivos;

    private int selectedFood;
    private String filter;

    private ProductoDao productoDao;
    private ValorAtributoProductoDao atributoProductoDao;

    public OnProductsSelectedListener listener;
    public ComidasFragment(OnProductsSelectedListener listener) {
        this.listener = listener;
    }

    public ComidasFragment(String filter, OnProductsSelectedListener listener) {
        this.filter = filter;
        this.listener = listener;
    }

    public ComidasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comidas, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

        AppDataBase db = AppDataBase.getInstance(requireContext());
        productoDao = db.productoDao();
        atributoProductoDao = db.valorAtributoProductoDao();

        new Thread(() -> {
            // Obtener productos
            foodList = (filter == null)
                    ? productoDao.getComidas()
                    : productoDao.searchComidas(formatFilter(filter));

            // Obtener todos los atributos activos
            atributosActivos = atributoProductoDao.getAll().stream()
                    .filter(ValorAtributoProducto::isActivo)
                    .collect(Collectors.toList());

            // Obtener los IDs dinámicamente por nombre
            Map<String, Integer> mapaIds = db.atributoProductoDao().getProductos().stream()
                    .collect(Collectors.toMap(
                            a -> a.getNombreAtributoProducto().toLowerCase().trim(),
                            AtributoProducto::getIdAtributoProducto
                    ));

            int ID_CHICHARRON = mapaIds.getOrDefault("chicharrón", -1);
            int ID_CHORIZO = mapaIds.getOrDefault("chorizo", -1);
            int ID_BOLLO = mapaIds.getOrDefault("bollo", -1);

            requireActivity().runOnUiThread(() -> {
                adapter = new FoodPagerAdapter(
                        requireActivity(),
                        foodList,
                        atributosActivos,
                        ID_CHICHARRON,
                        ID_CHORIZO,
                        ID_BOLLO,
                        this
                );
                viewPager.setAdapter(adapter);
            });
        }).start();

        return view;
    }

    @Override
    public void onFoodSelected(int foodId) {
        selectedFood = foodId;
        Producto selected = findProductById(foodId);
        listener.sendToCart(selected);

        Log.d("ComidasFragment", "Producto seleccionado: " + selectedFood);
    }

    @Override
    public void showInfoDialog(String productName) {
        Snackbar.make(getView(), "Has agregado " + productName.toUpperCase() + " a tu carrito", Snackbar.LENGTH_SHORT)
                .show();
    }

    private Producto findProductById(int foodId) {
        for (Producto producto : foodList) {
            if (producto.getIdProducto() == foodId) {
                return producto;
            }
        }
        return null;
    }

    private String formatFilter(String filter) {
        filter = filter.replace(" ", "-");
        return filter;
    }

}

