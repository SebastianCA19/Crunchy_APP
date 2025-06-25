package com.example.crunchy_app.secciones.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.R;
import com.example.crunchy_app.productos.DAO.ProductoDao;
import com.example.crunchy_app.productos.DAO.ValorAtributoProductoDao;
import com.example.crunchy_app.productos.OnProductsSelectedListener;
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.ValorAtributoProducto;
import com.google.android.material.snackbar.Snackbar;

import java.text.NumberFormat;
import java.util.Locale;

public class OtrosFragment extends Fragment implements OnProductsSelectedListener {

    private final int VALOR_POR_GRAMO;

    private Button btnConvert;

    private Button btnAddChicharron;

    private Button btnAddChorizo;

    private Button btnAddBollo;

    private ProductoDao productoDao;

    double gramosNum;

    double dineroNum;

    private byte completado;

    private OnProductsSelectedListener listener;

    private TextView tvChorizo;

    private TextView tvBollo;

    private Locale locale;

    private NumberFormat numberFormat;

    public OtrosFragment(int valorPorGramo, OnProductsSelectedListener listener) {
        VALOR_POR_GRAMO = valorPorGramo;
        this.listener = listener;
        locale = new Locale("es", "CO");
        numberFormat = NumberFormat.getCurrencyInstance(locale);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_otros, container, false);
        tvChorizo = view.findViewById(R.id.tvPriceChorizo);

        new Thread(() -> {
            Producto chorizo = productoDao.getProductoById(42);
            tvChorizo.setText(numberFormat.format(chorizo.getValorProducto()));
            tvBollo = view.findViewById(R.id.tvPriceBollo);
            Producto bollo = productoDao.getProductoById(43);
            tvBollo.setText(numberFormat.format(bollo.getValorProducto()));
        }).start();

        //Asignar cada elemento de la view a una variable
        btnConvert = view.findViewById(R.id.btn_convert);
        btnAddChicharron = view.findViewById(R.id.btn_add_chicharron);
        btnAddChorizo = view.findViewById(R.id.btn_add_chorizo);
        btnAddBollo = view.findViewById(R.id.btn_add_bollo);

        AppDataBase db = AppDataBase.getInstance(requireContext());
        productoDao = db.productoDao();

        btnConvert.setOnClickListener(this::convertValues);
        btnAddChicharron.setOnClickListener(v -> {
            completado = addChicharron();
            if(completado == 1){
                showInfoDialog("Chicharron");
                completado = 0;
            }
        });
        btnAddChorizo.setOnClickListener(v -> {
            completado = addChorizo();
            if(completado == 1){
                showInfoDialog("Chorizo");
                completado = 0;
            }
        });
        btnAddBollo.setOnClickListener(v -> {
            completado = addBollo();
            if(completado == 1){
                showInfoDialog("Bollo");
                completado = 0;
                }
        });
        return view;
    }

    public void convertValues(View view) {
        // Obtener la vista raíz del fragmento
        View rootView = getView();
        if (rootView == null) return;

        // Obtener los valores de los campos de entrada
        EditText etGramos = rootView.findViewById(R.id.inGramos);
        EditText etDinero = rootView.findViewById(R.id.inDinero);

        String gramos = etGramos.getText().toString();
        String dinero = etDinero.getText().toString();

        if (gramos.isEmpty() && dinero.isEmpty()) {
            return;
        }

        try {
            if (!gramos.isEmpty() && dinero.isEmpty()) {
                gramosNum = Double.parseDouble(gramos);
                dineroNum = gramosNum * VALOR_POR_GRAMO;
                etDinero.setText("$" + dineroNum);
            } else if (!dinero.isEmpty() && gramos.isEmpty()) {
                dineroNum = Double.parseDouble(dinero);
                gramosNum = dineroNum / VALOR_POR_GRAMO;
                etGramos.setText(gramosNum + "gr");
            }

        } catch (NumberFormatException e) {
            // Manejar un posible error de conversión
            e.printStackTrace();
        }
    }

    private byte addChicharron() {
        if(gramosNum != 0 && dineroNum != 0){
            //Obtenemos el producto chicharron personalizado
            new Thread(() -> {
                Producto chicharron = productoDao.getProductoById(41);
                chicharron.setValorProducto((float) dineroNum);
                chicharron.setCantidadChicharron((int) gramosNum);

                Log.d("Chicharron", "Datos: "+ chicharron.getValorProducto() + " " + chicharron.getCantidadChicharron());
                listener.sendToCart(chicharron);
            }).start();
            return 1;
        }
        return 0;
    }

    private byte addChorizo(){
        new Thread(() -> {
            Producto chorizo = productoDao.getProductoById(42);
            listener.sendToCart(chorizo);
        }).start();
        return 1;
    }

    private byte addBollo(){
        new Thread(() -> {
            Producto bollo = productoDao.getProductoById(43);
            listener.sendToCart(bollo);
        }).start();
        return 1;
    }

    @Override
    public void showInfoDialog(String productName) {
        Snackbar.make(getView(), "Has agregado " + productName.toUpperCase() + " a tu carrito", Snackbar.LENGTH_SHORT)
                .show();
    }
}