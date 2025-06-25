package com.example.crunchy_app.carrito.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.R;
import com.example.crunchy_app.carrito.adapter.CartAdapter;
import com.example.crunchy_app.pedidos.model.Locacion;
import com.example.crunchy_app.pedidos.model.Pedido;
import com.example.crunchy_app.pedidos.model.ProductoDelPedido;
import com.example.crunchy_app.productos.DAO.ValorAtributoProductoDao;
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.ValorAtributoProducto;;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.appcompat.app.AlertDialog;

public class CartDialogFragment extends DialogFragment {

    private RecyclerView recyclerCart;
    private TextView txtSubtotal;
    private Button btnCheckout;
    private TextView btnClose;

    private TextView txtLocacion;

    private TextView txtValorDomicilio;

    private Dialog dialog;

    private List<Locacion> locations;
    private Map<Producto, Integer> carrito = new HashMap<>();
    private CartAdapter adapter;

    private double subtotal;
    private double total;

    private Locacion locacionSeleccionada;

    private TextView txtTotal;

    private RadioGroup radioGroup;

    private AppDataBase db;
    private int metodoPagoId;

    private String nameUser;
    private String directionUser;

    private String domName;
    private TextView txtNombre;
    private TextView txtDireccion;
    private TextView txtDomName;
    private EditText txtHoraEntrega;
    private String horaEntrega;

    private final NumberFormat numberFormat;

    public CartDialogFragment(Map<Producto, Integer> carrito) {
        this.carrito = carrito;
        this.numberFormat = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
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
            window.setLayout((int) (getResources().getDisplayMetrics().widthPixels * 0.7), ViewGroup.LayoutParams.MATCH_PARENT);
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
        txtLocacion = view.findViewById(R.id.txtLocation);
        txtValorDomicilio = view.findViewById(R.id.txtValorDomicilio);
        txtTotal = view.findViewById(R.id.txtTotal);
        radioGroup = view.findViewById(R.id.radioGroup);
        txtNombre = view.findViewById(R.id.inNombre);
        txtHoraEntrega = view.findViewById(R.id.editTextTime);
        txtDireccion = view.findViewById(R.id.inDireccion);
        txtDomName = view.findViewById(R.id.inDomiciliario);

        txtHoraEntrega = view.findViewById(R.id.editTextTime);

        // Listener para mostrar el selector de hora
        txtHoraEntrega.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int hora = calendar.get(Calendar.HOUR_OF_DAY);
            int minuto = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                    (view1, hourOfDay, minute1) -> {
                        String horaFormateada = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute1);
                        txtHoraEntrega.setText(horaFormateada);
                    }, hora, minuto, true);

            timePickerDialog.show();
        });


        new Thread(() -> {
            db = AppDataBase.getInstance(getActivity().getApplicationContext());
            locations = db.locacionDao().getAll();

            int savedLocacionId = getActivity()
                    .getSharedPreferences("cart_prefs", 0)
                    .getInt("locacion_id", -1);

            if (savedLocacionId != -1) {
                for (Locacion loc : locations) {
                    if (loc.getIdLocacion() == savedLocacionId) {
                        locacionSeleccionada = loc;
                        getActivity().runOnUiThread(() -> {
                            updateLocacionValue(loc.getNombreLocacion());
                        });
                        break;
                    }
                }
            }
        }).start();

        setupRecyclerView();

        btnClose.setOnClickListener(v -> dismiss());

        btnCheckout.setOnClickListener(v -> {
            if (carrito.isEmpty()) {
                Toast.makeText(getContext(), "⚠ El carrito está vacío.", Toast.LENGTH_SHORT).show();
                return;
            }
            getFields();

            boolean camposValidos = true;

            if (nameUser == null || nameUser.trim().isEmpty()) {
                txtNombre.setError("Campo obligatorio");
                camposValidos = false;
            }
            if (directionUser == null || directionUser.trim().isEmpty()) {
                txtDireccion.setError("Campo obligatorio");
                camposValidos = false;
            }
            if (domName == null || domName.trim().isEmpty()) {
                txtDomName.setError("Campo obligatorio");
                camposValidos = false;
            }
            if (metodoPagoId == 0) {
                Toast.makeText(requireContext(), "⚠ Seleccione un método de pago", Toast.LENGTH_SHORT).show();
                camposValidos = false;
            }
            if (locacionSeleccionada == null) {
                Toast.makeText(requireContext(), "⚠ Seleccione una zona de entrega", Toast.LENGTH_SHORT).show();
                camposValidos = false;
            }

            if (!camposValidos) {
                return; // No continúa si hay errores
            }


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        SharedPreferences prefs = requireContext().getSharedPreferences("stock_prefs", Context.MODE_PRIVATE);
                        int cantidadChicharron = prefs.getInt("chicharron", 0);
                        int cantidadChorizos = prefs.getInt("chorizos", 0);

                        int chorizosVendido = 0;
                        float chicharronVendido = 0;
                        int pass = 0;
                        SharedPreferences productosVendidos = requireContext().getSharedPreferences("productos_vendidos", Context.MODE_PRIVATE);
                        if(productosVendidos.contains("chicharron_vendido") && productosVendidos.contains("chorizo_vendido")){
                            chorizosVendido = productosVendidos.getInt("chorizo_vendido", 0);
                            chicharronVendido = productosVendidos.getFloat("chicharron_vendido", 0);
                            pass = 1;
                        }

                        //Verificar si la cantidad de chorizos y chicharron del pedido es permitida
                        if(pass == 1){
                            ValorAtributoProductoDao valorAtributoProductoDao = db.valorAtributoProductoDao();
                            int cantidadChorizoPedido = 0;
                            float cantidadChicharronPedido = 0;
                            for(Map.Entry<Producto, Integer> entry : carrito.entrySet()){
                                Producto producto = entry.getKey();
                                int cantidad = entry.getValue();

                                if(producto.getIdProducto() == 41){
                                    cantidadChicharronPedido += producto.getCantidadChicharron() * cantidad;;
                                }else if(producto.getIdProducto() == 42){
                                    cantidadChorizoPedido += cantidad;
                                }else{
                                    int chorizoProducto = valorAtributoProductoDao.getChorizoValue(producto.getIdProducto());
                                    float chicharronProducto = valorAtributoProductoDao.getChicharronValue(producto.getIdProducto());

                                    cantidadChorizoPedido += chorizoProducto * cantidad;
                                    cantidadChicharronPedido += chicharronProducto * cantidad;
                                }
                            }

                            if((cantidadChorizoPedido + chorizosVendido) > cantidadChorizos || (cantidadChicharronPedido + chicharronVendido) > cantidadChicharron){
                                requireActivity().runOnUiThread(() -> {
                                    new AlertDialog.Builder(requireContext())
                                            .setTitle("Stock insuficiente")
                                            .setMessage("No hay suficientes chorizos o chicharrones para completar este pedido.")
                                            .setPositiveButton("Entendido", null)
                                            .show();
                                });
                                return;
                            }
                        }

                        // Crear nuevo pedido
                        Pedido pedido = null;
                        LocalTime hora_entrega = null;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && (horaEntrega != null && !horaEntrega.trim().isEmpty())) {
                            hora_entrega = LocalTime.parse(horaEntrega);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                            pedido = new Pedido(
                                    nameUser,
                                    directionUser,
                                    domName,
                                    metodoPagoId,
                                    locacionSeleccionada.getIdLocacion(),
                                    1,         // idEstadoPedido (ej: 1 = Pendiente)
                                    LocalDate.now(),
                                    LocalTime.now(),
                                    hora_entrega
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

                            if(producto.getIdProducto() == 41){
                                ValorAtributoProductoDao valorAtributoProductoDao = db.valorAtributoProductoDao();
                                String productoIdFormat = String.format("%d%d", producto.getIdProducto(), pedidoId);
                                valorAtributoProductoDao.insert(new ValorAtributoProducto(41, 2, producto.getCantidadChicharron(), Integer.valueOf(productoIdFormat)));
                            }

                            db.productoDelPedidoDao().insert(productoDelPedido);
                        }

                        carrito.clear();
                        Activity activity = getActivity();
                        if (activity != null) {
                            activity.runOnUiThread(() ->
                                    Toast.makeText(activity, "✅ Pedido agregado correctamente.", Toast.LENGTH_LONG).show()
                            );
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Activity activity = getActivity();
                        if (activity != null) {
                            activity.runOnUiThread(() ->
                                    Toast.makeText(activity, "❌ Ocurrió un error al confirmar el pedido.", Toast.LENGTH_LONG).show()
                            );
                        }
                    }
                }
            }).start();
            dismiss(); // Por ahora solo cerrar
        });

        txtLocacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_searchable_location);
                dialog.getWindow().setLayout(1000, 900);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialog.setCanceledOnTouchOutside(true);

                EditText inLocation = dialog.findViewById(R.id.inLocation);
                ListView listLocations = dialog.findViewById(R.id.listLocations);

                ArrayAdapter<Locacion> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, locations);
                listLocations.setAdapter(adapter);

                inLocation.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                            adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listLocations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        locacionSeleccionada = (Locacion) parent.getItemAtPosition(position);
                        requireActivity().getSharedPreferences("cart_prefs", 0)
                                .edit()
                                .putInt("locacion_id", locacionSeleccionada.getIdLocacion())
                                .apply();

                        updateLocacionValue(locacionSeleccionada.getNombreLocacion());
                        updateTotal();
                        dialog.dismiss();
                    }
                });
            }
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
        subtotal = 0;
        for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
            subtotal += entry.getKey().getValorProducto() * entry.getValue();
        }
        txtSubtotal.setText("Subtotal: "+ numberFormat.format(subtotal));
        updateTotal();
    }

    private void updateLocacionValue(String locacion){
        txtValorDomicilio.setText("Valor domicilio: " + numberFormat.format(locacionSeleccionada.getValorDomicilio()));
        txtLocacion.setText(locacion.toUpperCase());
        updateTotal();
    }

    private void updateTotal(){
        if (locacionSeleccionada == null){
            total = subtotal;
        }else{
            total = subtotal + locacionSeleccionada.getValorDomicilio();
        }
        txtTotal.setText("Total: " + numberFormat.format(total));
    }

    private void getFields(){
        //Nombre y apellido del usuario
        nameUser = txtNombre.getText().toString();
        directionUser = txtDireccion.getText().toString();
        domName = txtDomName.getText().toString();

        //Obtener el metodo de pago
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = radioGroup.findViewById(selectedRadioButtonId);
        if (selectedRadioButtonId != -1) {
            String selectedText = selectedRadioButton.getText().toString();
            getMetodoPago(selectedText);
        }

        horaEntrega = txtHoraEntrega.getText().toString();
    }

    private void getMetodoPago(String selectedText) {
        switch (selectedText) {
            case "Efectivo":
                metodoPagoId = 1;
                break;
            case "Transferencia":
                metodoPagoId = 2;
                break;
            case "Mixto":
                metodoPagoId = 3;
                break;
        }
    }

}