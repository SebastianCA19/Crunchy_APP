package com.example.crunchy_app.secciones.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.crunchy_app.R;
import com.example.crunchy_app.carrito.fragment.CartDialogFragment;
import com.example.crunchy_app.productos.OnProductsSelectedListener;
import com.example.crunchy_app.productos.bebidas.fragment.BebidasFragment;
import com.example.crunchy_app.productos.comidas.fragment.ComidasFragment;
import com.example.crunchy_app.productos.model.Producto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment implements OnProductsSelectedListener{

    private String input;
    private Map<Producto, Integer> carrito = new HashMap<>();
    private FloatingActionButton fabCart;

    private ComidasFragment comidasFragment;
    private BebidasFragment bebidasFragment;
    private OtrosFragment otrosFragment;

    public OrdersFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = requireContext().getSharedPreferences("stock_prefs", Context.MODE_PRIVATE);
        float valorPorGramo =  prefs.getFloat("valor_por_gramo", 0.0f);

        comidasFragment = new ComidasFragment(this);
        bebidasFragment = new BebidasFragment(this);
        otrosFragment = new OtrosFragment(valorPorGramo,this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        // Inicialización de botones
        Button btComidas = view.findViewById(R.id.btComidas);
        Button btBebidas = view.findViewById(R.id.btBebidas);
        Button btOtros = view.findViewById(R.id.btOtros);
        Button btBuscar = view.findViewById(R.id.btBuscar);
        EditText inputText = view.findViewById(R.id.inputText);
        fabCart = view.findViewById(R.id.fabCart); // Botón flotante del carrito

        // Cargar fragmento por defecto (ComidasFragment)
        replaceFragment(comidasFragment);

        // Eventos de botones
        btComidas.setOnClickListener(v -> replaceFragment(comidasFragment));
        btBebidas.setOnClickListener(v -> replaceFragment(bebidasFragment));
        btOtros.setOnClickListener(v -> replaceFragment(otrosFragment));

        // Botón de búsqueda
        btBuscar.setOnClickListener(v -> {
            input = inputText.getText().toString();
            Fragment currentFragment = getChildFragmentManager().findFragmentById(R.id.frame_layout);
            if (currentFragment instanceof ComidasFragment) {
                comidasFragment = new ComidasFragment(input, this);
                replaceFragment(comidasFragment);
            } else if (currentFragment instanceof BebidasFragment) {
                bebidasFragment = new BebidasFragment(input, this);
                replaceFragment(bebidasFragment);
            }
        });

        // Evento para abrir el carrito
        fabCart.setOnClickListener(v -> mostrarCarrito());

        return view;
    }

    // Método para cambiar de fragmento
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    // Método para mostrar el carrito
    private void mostrarCarrito() {
        CartDialogFragment cart = new CartDialogFragment(carrito);
        cart.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog_Alert);
        cart.show(getChildFragmentManager(), "cart");
    }

    @Override
    public void sendToCart(Producto producto) {
        Log.d("OrdersFragment", "Producto Enviado");
        if (carrito.containsKey(producto)) {
            int cantidadActual = carrito.get(producto);
            carrito.put(producto, cantidadActual + 1);
        } else {
            carrito.put(producto, 1);
        }
    }

    @Override
    public void showInfoDialog(String productName) {

    }
}
