package com.example.crunchy_app.carrito.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crunchy_app.R;
import com.example.crunchy_app.carrito.adapter.CartAdapter;
import com.example.crunchy_app.productos.model.Producto;

import java.util.HashMap;
import java.util.Map;

public class CartDialogFragment extends DialogFragment {

    private RecyclerView recyclerCart;
    private TextView txtSubtotal;
    private Button btnCheckout;
    private TextView btnClose;

    private Map<Producto, Integer> carrito = new HashMap<>();
    private CartAdapter adapter;

    public CartDialogFragment(Map<Producto, Integer> carrito) {
        this.carrito = carrito;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CartDialogRight);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            Window window = getDialog().getWindow();
            window.setLayout((int) (getResources().getDisplayMetrics().widthPixels * 0.4), ViewGroup.LayoutParams.MATCH_PARENT);
            window.setGravity(Gravity.END);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Eliminamos el título del DialogFragment
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        View view = inflater.inflate(R.layout.dialog_cart, container, false);

        recyclerCart = view.findViewById(R.id.recyclerCart);
        txtSubtotal = view.findViewById(R.id.txtSubtotal);
        btnCheckout = view.findViewById(R.id.btnCheckout);
        btnClose = view.findViewById(R.id.btnClose);

        setupRecyclerView();

        btnClose.setOnClickListener(v -> dismiss());

        btnCheckout.setOnClickListener(v -> {
            // Aquí puedes hacer algo con el carrito: guardar, enviar, etc.
            dismiss(); // Por ahora solo cerrar
        });

        return view;
    }

    private void setupRecyclerView() {
        adapter = new CartAdapter(carrito, this::updateSubtotal);
        recyclerCart.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerCart.setAdapter(adapter);
        updateSubtotal();
    }

    private void updateSubtotal() {
        double subtotal = 0;
        for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
            subtotal += entry.getKey().getPrecio() * entry.getValue();
        }
        txtSubtotal.setText("Subtotal: $ " + String.format("%.2f", subtotal));
    }
}

