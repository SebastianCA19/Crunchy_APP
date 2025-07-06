package com.example.crunchy_app.productos.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.DBconnection.JsonExporter;
import com.example.crunchy_app.R;
import com.example.crunchy_app.pedidos.adapter.LocacionAdapter;
import com.example.crunchy_app.pedidos.model.Locacion;
import com.example.crunchy_app.productos.adapter.ProductoAdapter;
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.ValorAtributoProducto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GestionProductosActivity extends AppCompatActivity {
    private Button btnComidas, btnBebidas, btnLocaciones, btnAgregarLocacion, btnAgregarComida, btnAgregarBebida;
    private RecyclerView recyclerGestion;
    private AppDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_productos);

        Toolbar toolbar = findViewById(R.id.gestionToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Gestión");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        cargarComidas();

        btnComidas = findViewById(R.id.btnProdComidas);
        btnBebidas = findViewById(R.id.btnProdBebidas);
        btnLocaciones = findViewById(R.id.btnLocaciones);
        recyclerGestion = findViewById(R.id.recyclerGestion);
        btnAgregarLocacion = findViewById(R.id.btnAgregarLocacion);
        btnAgregarComida = findViewById(R.id.btnAgregarComida);
        btnAgregarBebida = findViewById(R.id.btnAgregarBebida);

        db = AppDataBase.getInstance(getApplicationContext());
        recyclerGestion.setLayoutManager(new LinearLayoutManager(this));

        btnComidas.setOnClickListener(v -> cargarComidas());
        btnBebidas.setOnClickListener(v -> cargarBebidas());
        btnLocaciones.setOnClickListener(v -> cargarLocaciones());
        btnAgregarLocacion.setOnClickListener(v -> mostrarDialogAgregarLocacion());
        btnAgregarComida.setOnClickListener(v -> mostrarDialogAgregarProducto(1,this::cargarComidas));
        btnAgregarBebida.setOnClickListener(v -> mostrarDialogAgregarProducto(3,this::cargarBebidas));

    }
    private void cargarComidas() {
        new Thread(() -> {
            List<Producto> comidas = db.productoDao().getComidas();

            runOnUiThread(() -> {
                // TODO: Usa tu adapter personalizado aquí

                recyclerGestion.setAdapter(new ProductoAdapter(comidas, new ProductoAdapter.OnProductoActionListener() {
                    @Override
                    public void onEditar(Producto producto) {
                        mostrarDialogEditarProducto(producto, () -> {
                            cargarComidas();
                        });
                    }
                    @Override
                    public void onEliminar(Producto producto) {
                        mostrarDialogEliminarProducto(producto, () -> {
                            cargarComidas();

                        });
                    }
                }));
                recyclerGestion.setLayoutManager(new LinearLayoutManager(this));
                recyclerGestion.setHasFixedSize(true);

            });
        }).start();
    }
    private void cargarBebidas() {
        new Thread(() -> {
            List<Producto> bebidas = db.productoDao().getBebidas();
            runOnUiThread(() -> {
                // TODO: Usa tu adapter personalizado aquí
                recyclerGestion.setAdapter(new ProductoAdapter(bebidas, new ProductoAdapter.OnProductoActionListener() {
                    @Override
                    public void onEditar(Producto producto) {
                         mostrarDialogEditarProducto(producto, () -> {
                             cargarBebidas();
                         });
                    }

                    @Override
                    public void onEliminar(Producto producto) {
                         mostrarDialogEliminarProducto(producto, () -> {
                             cargarBebidas();
                         });
                    }
                }));
                recyclerGestion.setLayoutManager(new LinearLayoutManager(this));
                recyclerGestion.setHasFixedSize(true);
            });

        }).start();
    }
    private void cargarLocaciones() {
        new Thread(() -> {
            List<Locacion> locaciones = db.locacionDao().getAll();
            runOnUiThread(() -> {
                // TODO: Usa tu adapter personalizado aquí
                recyclerGestion.setAdapter(new LocacionAdapter(locaciones, new LocacionAdapter.OnLocacionActionListener() {
                    @Override
                    public void onEditar(Locacion locacion) {
                        mostrarDialogEditarLocacion(locacion);
                    }
                    @Override
                    public void onEliminar(Locacion locacion) {
                        mostrarDialogEliminarLocacion(locacion);
                    }
                }));
                recyclerGestion.setLayoutManager(new LinearLayoutManager(this));
                recyclerGestion.setHasFixedSize(true);
            });
        }).start();
    }

    /////////////////////////////////////////
    private void mostrarDialogAgregarLocacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_add_locacion, null);
        builder.setView(dialogView);
        builder.setTitle("Agregar locación");

        EditText etNombre = dialogView.findViewById(R.id.etNombreLocacion);
        EditText etValor = dialogView.findViewById(R.id.etValorDomicilio);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nombre = etNombre.getText().toString().trim();
            String valorTexto = etValor.getText().toString().trim();

            if (nombre.isEmpty() || valorTexto.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            float valor = Float.parseFloat(valorTexto);

            Locacion nuevaLocacion = new Locacion(nombre,null, valor);

            new Thread(() -> {
                db.locacionDao().insert(nuevaLocacion);
                JsonExporter.exportLocaciones(getApplicationContext(), db.locacionDao().getAll());
                // Volver a cargar locaciones (en el hilo principal)
                runOnUiThread(this::cargarLocaciones);
            }).start();
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
    private void mostrarDialogEditarLocacion(Locacion locacion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_add_locacion, null);
        builder.setView(dialogView);

        EditText etNombre = dialogView.findViewById(R.id.etNombreLocacion);
        EditText etValor = dialogView.findViewById(R.id.etValorDomicilio);
        etNombre.setText(locacion.getNombreLocacion());
        etValor.setText(String.valueOf(locacion.getValorDomicilio()));
        builder.setTitle("Editar locación");
        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nombre = etNombre.getText().toString().trim();
            String valorTexto = etValor.getText().toString().trim();
            if (nombre.isEmpty() || valorTexto.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            float valor = Float.parseFloat(valorTexto);
            locacion.setNombreLocacion(nombre);
            locacion.setValorDomicilio(valor);
            new Thread(() -> {
                db.locacionDao().update(locacion);
                JsonExporter.exportLocaciones(getApplicationContext(), db.locacionDao().getAll());
                runOnUiThread(() -> cargarLocaciones());
                }).start();
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
    private void mostrarDialogEliminarLocacion(Locacion locacion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar locación");
        builder.setMessage("¿Estás seguro de que quieres eliminar esta locación?");

        builder.setPositiveButton("Sí", (dialog, which) -> {
            new Thread(() -> {
                db.locacionDao().deleteLocacionById(locacion.getIdLocacion());
                JsonExporter.exportLocaciones(getApplicationContext(), db.locacionDao().getAll());
                runOnUiThread(() -> cargarLocaciones());
            }).start();
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    ////////////////////////////////////////
    private void mostrarDialogAgregarProducto(int tipoProductoIdInicial, Runnable accionPostGuardar) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_producto, null);
        builder.setView(dialogView);

        EditText etNombre = dialogView.findViewById(R.id.etNombreProducto);
        EditText etValor = dialogView.findViewById(R.id.etValorProducto);
        Spinner spinnerTipo = dialogView.findViewById(R.id.spinnerTipoProducto);

        EditText etCantidadBollo = dialogView.findViewById(R.id.etCantidadBollo);
        EditText etCantidadChicharron = dialogView.findViewById(R.id.etCantidadChicharron);
        EditText etCantidadChorizo = dialogView.findViewById(R.id.etCantidadChorizo);
        EditText etMililitros = dialogView.findViewById(R.id.etCantidadMililitros);

        LinearLayout layoutIngredientes = dialogView.findViewById(R.id.layoutIngredientes);
        LinearLayout layoutMililitros = dialogView.findViewById(R.id.layoutMililitros);

        String[] tiposVisibles;
        int tipoInicialIndex;

        boolean esComida = (tipoProductoIdInicial == 1 || tipoProductoIdInicial == 2);

        if (esComida) {
            tiposVisibles = new String[]{"Combo", "Picada"};
            tipoInicialIndex = (tipoProductoIdInicial == 1) ? 0 : 1;
            layoutIngredientes.setVisibility(View.VISIBLE);
            layoutMililitros.setVisibility(View.GONE);
        } else {
            tiposVisibles = new String[]{"Bebida personal", "Bebida familiar", "Bebida alcohólica"};
            tipoInicialIndex = (tipoProductoIdInicial == 3) ? 0 : (tipoProductoIdInicial == 4 ? 1 : 2);
            layoutIngredientes.setVisibility(View.GONE);
            layoutMililitros.setVisibility(View.VISIBLE);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tiposVisibles);
        spinnerTipo.setAdapter(adapter);
        spinnerTipo.setSelection(tipoInicialIndex);

        // Ocultar chorizo si elige "Picada"
        if (esComida) {
            spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    etCantidadChorizo.setVisibility(position == 0 ? View.VISIBLE : View.GONE); // 0 = Combo
                }
                @Override public void onNothingSelected(AdapterView<?> parent) {}
            });
        }

        builder.setTitle("Agregar producto");
        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nombre = etNombre.getText().toString().trim();
            String valorStr = etValor.getText().toString().trim();

            if (nombre.isEmpty() || valorStr.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            float valor;
            try {
                valor = Float.parseFloat(valorStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "El valor debe ser un número válido", Toast.LENGTH_SHORT).show();
                return;
            }

            int tipoProductoId;
            if (esComida) {
                tipoProductoId = (spinnerTipo.getSelectedItemPosition() == 0) ? 1 : 2;
            } else {
                tipoProductoId = 3 + spinnerTipo.getSelectedItemPosition(); // 3, 4 o 5
            }

            Producto nuevo = new Producto(nombre, tipoProductoId, valor);

            List<ValorAtributoProducto> atributos = new ArrayList<>();

            if (esComida) {
                float bollo = etCantidadBollo.getText().toString().trim().isEmpty() ? 0f : Float.parseFloat(etCantidadBollo.getText().toString().trim());
                float chicharron = etCantidadChicharron.getText().toString().trim().isEmpty() ? 0f : Float.parseFloat(etCantidadChicharron.getText().toString().trim());
                float chorizo = etCantidadChorizo.getText().toString().trim().isEmpty() ? 0f : Float.parseFloat(etCantidadChorizo.getText().toString().trim());

                if (chicharron > 0f) atributos.add(new ValorAtributoProducto(nuevo.getIdProducto(), 1, chicharron));
                if (tipoProductoId == 1 && chorizo > 0f) atributos.add(new ValorAtributoProducto(nuevo.getIdProducto(), 2, chorizo));
                if (bollo > 0f) atributos.add(new ValorAtributoProducto(nuevo.getIdProducto(), 3, bollo));
            } else {
                float ml = etMililitros.getText().toString().trim().isEmpty() ? 0f : Float.parseFloat(etMililitros.getText().toString().trim());
                if (ml > 0f) {
                    atributos.add(new ValorAtributoProducto(nuevo.getIdProducto(), 4, ml)); // Atributo 4 = mililitros
                }
            }

            new Thread(() -> {
                Long idProducto = db.productoDao().insert(nuevo);
                for (ValorAtributoProducto atributo : atributos) {
                    atributo.setIdProducto(idProducto.intValue());
                    db.valorAtributoProductoDao().insert(atributo);
                }
                JsonExporter.exportProductos(getApplicationContext(), db.productoDao().getAll());
                JsonExporter.exportValorAtributoProductos(getApplicationContext(), db.valorAtributoProductoDao().getAll());
                runOnUiThread(accionPostGuardar);
            }).start();
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
    private void mostrarDialogEditarProducto(Producto producto, Runnable accionPostGuardar) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_producto, null);
        builder.setView(dialogView);

        EditText etNombre = dialogView.findViewById(R.id.etNombreProducto);
        EditText etValor = dialogView.findViewById(R.id.etValorProducto);
        Spinner spinnerTipo = dialogView.findViewById(R.id.spinnerTipoProducto);

        EditText etCantidadBollo = dialogView.findViewById(R.id.etCantidadBollo);
        EditText etCantidadChicharron = dialogView.findViewById(R.id.etCantidadChicharron);
        EditText etCantidadChorizo = dialogView.findViewById(R.id.etCantidadChorizo);
        EditText etMililitros = dialogView.findViewById(R.id.etCantidadMililitros);

        LinearLayout layoutIngredientes = dialogView.findViewById(R.id.layoutIngredientes);
        LinearLayout layoutMililitros = dialogView.findViewById(R.id.layoutMililitros);

        // Inicializar campos
        etNombre.setText(producto.getNombreProducto());
        etValor.setText(String.valueOf(producto.getValorProducto()));

        int tipoProductoId = producto.getIdTipoProducto();
        boolean esComida = (tipoProductoId == 1 || tipoProductoId == 2);

        String[] tiposVisibles;
        int tipoInicialIndex;

        if (esComida) {
            tiposVisibles = new String[]{"Combo", "Picada"};
            tipoInicialIndex = (tipoProductoId == 1) ? 0 : 1;
            layoutIngredientes.setVisibility(View.VISIBLE);
            layoutMililitros.setVisibility(View.GONE);
        } else {
            tiposVisibles = new String[]{"Bebida personal", "Bebida familiar", "Bebida alcohólica"};
            tipoInicialIndex = tipoProductoId - 3;
            layoutIngredientes.setVisibility(View.GONE);
            layoutMililitros.setVisibility(View.VISIBLE);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tiposVisibles);
        spinnerTipo.setAdapter(adapter);
        spinnerTipo.setSelection(tipoInicialIndex);

        if (esComida) {
            spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    etCantidadChorizo.setVisibility(position == 0 ? View.VISIBLE : View.GONE); // 0 = Combo
                }
                @Override public void onNothingSelected(AdapterView<?> parent) {}
            });
        }

        // Obtener valores actuales de los atributos
        new Thread(() -> {
            List<ValorAtributoProducto> atributos = db.valorAtributoProductoDao().getByProductoId(producto.getIdProducto());

            runOnUiThread(() -> {
                for (ValorAtributoProducto a : atributos) {
                    switch (a.getIdAtributoProducto()) {
                        case 1: etCantidadChicharron.setText(String.valueOf(a.getValorAtributoProducto())); break;
                        case 2: etCantidadChorizo.setText(String.valueOf(a.getValorAtributoProducto())); break;
                        case 3: etCantidadBollo.setText(String.valueOf(a.getValorAtributoProducto())); break;
                        case 4: etMililitros.setText(String.valueOf(a.getValorAtributoProducto())); break;
                    }
                }
            });
        }).start();

        builder.setTitle("Editar producto");
        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nombre = etNombre.getText().toString().trim();
            String valorStr = etValor.getText().toString().trim();

            if (nombre.isEmpty() || valorStr.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            float valor = Float.parseFloat(valorStr);
            producto.setNombreProducto(nombre);
            producto.setValorProducto(valor);

            int nuevoTipoProductoId;
            if (esComida) {
                nuevoTipoProductoId = (spinnerTipo.getSelectedItemPosition() == 0) ? 1 : 2;
            } else {
                nuevoTipoProductoId = 3 + spinnerTipo.getSelectedItemPosition(); // 3, 4 o 5
            }
            producto.setIdTipoProducto(nuevoTipoProductoId);

            List<ValorAtributoProducto> nuevosAtributos = new ArrayList<>();

            if (nuevoTipoProductoId == 1 || nuevoTipoProductoId == 2) {
                float bollo = etCantidadBollo.getText().toString().trim().isEmpty() ? 0f : Float.parseFloat(etCantidadBollo.getText().toString().trim());
                float chicharron = etCantidadChicharron.getText().toString().trim().isEmpty() ? 0f : Float.parseFloat(etCantidadChicharron.getText().toString().trim());
                float chorizo = etCantidadChorizo.getText().toString().trim().isEmpty() ? 0f : Float.parseFloat(etCantidadChorizo.getText().toString().trim());

                if (chicharron > 0f) nuevosAtributos.add(new ValorAtributoProducto(producto.getIdProducto(), 1, chicharron));
                if (nuevoTipoProductoId == 1 && chorizo > 0f) nuevosAtributos.add(new ValorAtributoProducto(producto.getIdProducto(), 2, chorizo));
                if (bollo > 0f) nuevosAtributos.add(new ValorAtributoProducto(producto.getIdProducto(), 3, bollo));
            } else {
                float ml = etMililitros.getText().toString().trim().isEmpty() ? 0f : Float.parseFloat(etMililitros.getText().toString().trim());
                if (ml > 0f) {
                    nuevosAtributos.add(new ValorAtributoProducto(producto.getIdProducto(), 4, ml));
                }
            }

            new Thread(() -> {
                db.productoDao().update(producto);
                db.valorAtributoProductoDao().desactivarByProductoId(producto.getIdProducto());
                for (ValorAtributoProducto atributo : nuevosAtributos) {
                    db.valorAtributoProductoDao().insert(atributo);
                }
                JsonExporter.exportProductos(getApplicationContext(), db.productoDao().getAll());
                JsonExporter.exportValorAtributoProductos(getApplicationContext(), db.valorAtributoProductoDao().getAll());
                runOnUiThread(accionPostGuardar);
            }).start();
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void mostrarDialogEliminarProducto(Producto producto, Runnable accionPostGuardar) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar producto");
        builder.setMessage("¿Estás seguro de que quieres eliminar este producto?");
        builder.setPositiveButton("Sí", (dialog, which) -> {
            new Thread(() -> {
                db.productoDao().deleteProductoById(producto.getIdProducto());
                db.valorAtributoProductoDao().desactivarByProductoId(producto.getIdProducto());
                JsonExporter.exportProductos(getApplicationContext(), db.productoDao().getAll());
                JsonExporter.exportValorAtributoProductos(getApplicationContext(), db.valorAtributoProductoDao().getAll());
                runOnUiThread(accionPostGuardar);
            }).start();
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

}



