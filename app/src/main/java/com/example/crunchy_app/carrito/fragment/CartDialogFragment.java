package com.example.crunchy_app.carrito.fragment;

import android.os.Build;
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
import androidx.room.Room;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.R;
import com.example.crunchy_app.carrito.adapter.CartAdapter;
import com.example.crunchy_app.pedidos.model.Pedido;
import com.example.crunchy_app.pedidos.model.ProductoDelPedido;
import com.example.crunchy_app.productos.model.Producto;

import java.time.LocalDate;
import java.time.LocalTime;
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
            if (carrito.isEmpty()) {
                Log.w("CartDialogFragment", "El carrito está vacío. No se puede confirmar pedido.");
                return;
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Instancia de Room
                        AppDataBase db = Room.databaseBuilder(
                                getContext().getApplicationContext(),
                                AppDataBase.class,

                                "crunchy-DB" // 👈 Pon aquí tu nombre real de la DB
                        ).build();

                        // Crear nuevo pedido
                        Pedido pedido = null;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            pedido = new Pedido(
                                    "Cliente", // Puedes pasar nombre real si lo tienes
                                    "Anonimo", // o dejarlo vacío si no capturas apellido
                                    1,         // idMetodoPago (puedes cambiarlo según lo que tengas)
                                    1,         // idLocacion
                                    1,         // idEstadoPedido (ej: 1 = Pendiente)
                                    LocalDate.now(),
                                    LocalTime.now()

                            );
                        }

                        // Insertar pedido
                        long pedidoId = db.pedidoDao().insert(pedido);

                        // Insertar productos del pedido
                        for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
                            Producto producto = entry.getKey();
                            int cantidad = entry.getValue();

                            ProductoDelPedido productoDelPedido = new ProductoDelPedido(
                                    (int) pedidoId,
                                    producto.getIdProducto(),
                                    cantidad
                            );

                            db.productoDelPedidoDao().insert(productoDelPedido);
                        }

                        Log.d("CartDialogFragment", "Pedido confirmado con ID: " + pedidoId);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
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

