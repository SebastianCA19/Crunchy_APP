package com.example.crunchy_app.secciones.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.example.crunchy_app.R;
import com.example.crunchy_app.productos.bebidas.fragment.BebidasFragment;
import com.example.crunchy_app.productos.comidas.fragment.ComidasFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment {

    private String input;
    private List<String> carrito = new ArrayList<>();
    private FloatingActionButton fabCart;

    public OrdersFragment() {
        // Constructor vacío requerido
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
        replaceFragment(new ComidasFragment());

        // Eventos de botones
        btComidas.setOnClickListener(v -> replaceFragment(new ComidasFragment()));
        btBebidas.setOnClickListener(v -> replaceFragment(new BebidasFragment()));
        btOtros.setOnClickListener(v -> replaceFragment(new OtrosFragment()));

        // Botón de búsqueda
        btBuscar.setOnClickListener(v -> {
            input = inputText.getText().toString();
            Fragment currentFragment = getChildFragmentManager().findFragmentById(R.id.frame_layout);

            if (currentFragment instanceof ComidasFragment) {
                ComidasFragment comidasFragment = new ComidasFragment();
                comidasFragment.setFilter(input);
                replaceFragment(comidasFragment);
            } else if (currentFragment instanceof BebidasFragment) {
                BebidasFragment bebidasFragment = new BebidasFragment();
                bebidasFragment.setFilter(input);
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

    // Método para agregar productos al carrito
    public void agregarAlCarrito(String producto) {
        carrito.add(producto);
        Toast.makeText(getContext(), producto + " añadido al carrito", Toast.LENGTH_SHORT).show();
    }

    // Método para mostrar el carrito en un AlertDialog
    private void mostrarCarrito() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Carrito de Compras");

        if (carrito.isEmpty()) {
            builder.setMessage("No hay productos en el carrito.");
        } else {
            StringBuilder carritoTexto = new StringBuilder();
            for (String item : carrito) {
                carritoTexto.append("- ").append(item).append("\n");
            }
            builder.setMessage(carritoTexto.toString());
        }

        builder.setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}
