package com.example.crunchy_app.pedidos.activity;

import static android.app.Activity.RESULT_OK;
import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.R;
import com.example.crunchy_app.pedidos.adapter.PedidoHistorialAdapter;
import com.example.crunchy_app.pedidos.model.EstadoPedido;
import com.example.crunchy_app.pedidos.model.Locacion;
import com.example.crunchy_app.pedidos.model.PedidoConEstado;
import com.example.crunchy_app.pedidos.model.ProductoDelPedido;
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.ValorAtributoProducto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HistorialPedidosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppDataBase db;

    // filtros
    private EditText etBuscarCliente, etBuscarDomiciliario;
    private CheckBox checkEncargado, checkEntregado, checkPagado;
    private List<PedidoConEstado> listaOriginal = new ArrayList<>();
    private PedidoHistorialAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pedidos);

        // Inicializar vistas
        recyclerView = findViewById(R.id.recyclerHistorial);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        etBuscarCliente = findViewById(R.id.etBuscarCliente);
        etBuscarDomiciliario = findViewById(R.id.etBuscarDomiciliario);
        checkEncargado = findViewById(R.id.checkEncargado);
        checkEntregado = findViewById(R.id.checkEntregado);
        checkPagado = findViewById(R.id.checkEntregado_Pagado);

        db = AppDataBase.getInstance(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.historialToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Historial de pedidos");


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> finish());


        SharedPreferences prefsFecha = getSharedPreferences("fecha_actual", MODE_PRIVATE);
        String fechaActual = prefsFecha.getString("fecha", null);

        if (fechaActual == null) {
            runOnUiThread(() -> {
                new AlertDialog.Builder(this)
                        .setTitle("Fecha no seleccionada")
                        .setMessage("Por favor, selecciona una fecha desde el panel de administrador antes de ver el historial.")
                        .setPositiveButton("OK", (dialog, which) -> finish())
                        .setCancelable(false)
                        .show();
            });
        } else {
            new Thread(() -> {
                List<PedidoConEstado> pedidos = db.pedidoDao().getPedidosConEstadoByFecha(fechaActual);
                List<EstadoPedido> estados = db.estadoPedidoDao().getAll();
                List<ProductoDelPedido> productosPedido = db.productoDelPedidoDao().getAllByFecha(fechaActual);
                List<Producto> productos = db.productoDao().getAll();
                Map<Integer, Locacion> locaciones = db.locacionDao().getAll().stream()
                        .collect(Collectors.toMap(locacion -> locacion.getIdLocacion(), locacion -> locacion));
                List<ValorAtributoProducto> chicharronQuantities = db.valorAtributoProductoDao().getCantidadChicharron();

                SharedPreferences prefsStock = getSharedPreferences("stock_prefs", MODE_PRIVATE);
                int valorPorGramo = (int) prefsStock.getFloat("valor_por_gramo", 0);

                runOnUiThread(() -> {
                    listaOriginal = new ArrayList<>(pedidos);
                    adapter = new PedidoHistorialAdapter(
                            pedidos, productosPedido, productos, estados, locaciones, db, valorPorGramo, chicharronQuantities, this, agregarProductoLauncher
                    );
                    recyclerView.setAdapter(adapter);
                });
            }).start();

            Button btnLimpiarFiltros = findViewById(R.id.btnLimpiarFiltros);
            btnLimpiarFiltros.setOnClickListener(v -> {
                etBuscarCliente.setText("");
                etBuscarDomiciliario.setText("");
                checkEncargado.setChecked(false);
                checkEntregado.setChecked(false);
                checkPagado.setChecked(false);

                adapter.actualizarLista(listaOriginal); // Mostrar todo otra vez
            });


            Button btnFiltrar = findViewById(R.id.btnFiltrar);
            btnFiltrar.setOnClickListener(v -> aplicarFiltros());
        }
    }

        private void aplicarFiltros() {
            String cliente = etBuscarCliente.getText().toString().trim().toLowerCase();
            String domiciliario = etBuscarDomiciliario.getText().toString().trim().toLowerCase();

            boolean checkEnc = checkEncargado.isChecked();
            boolean checkEnt = checkEntregado.isChecked();
            boolean checkPag = checkPagado.isChecked();

            boolean hayFiltroEstado = checkEnc || checkEnt || checkPag;

            List<PedidoConEstado> filtrados = new ArrayList<>();

            for (PedidoConEstado p : listaOriginal) {
                String estado = p.estado.getNombreEstadoPedido().toLowerCase();

                // Paso 1: Filtrar por estado
                if (hayFiltroEstado) {
                    boolean coincideEstado =
                            (checkEnc && estado.equals("encargado")) ||
                                    (checkEnt && estado.equals("entregado")) ||
                                    (checkPag && estado.equals("entregado + pagado"));

                    if (!coincideEstado)
                        continue; // si no coincide con ningún filtro de estado, saltar
                }

                // Paso 2: Filtrar por nombre de cliente si hay texto
                if (!cliente.isEmpty() && !p.pedido.getNombreCliente().toLowerCase().contains(cliente)) {
                    continue;
                }

                // Paso 3: Filtrar por domiciliario si hay texto
                if (!domiciliario.isEmpty() && !p.pedido.getNombreDomiciliario().toLowerCase().contains(domiciliario)) {
                    continue;
                }

                // Si pasó todos los filtros, se agrega a la lista
                filtrados.add(p);
            }
            adapter.actualizarLista(filtrados);
        }

    public final ActivityResultLauncher<Intent> agregarProductoLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    adapter.recargarDatosDesdeDB();
                    adapter.cerrarDialog();
                }
            });
}