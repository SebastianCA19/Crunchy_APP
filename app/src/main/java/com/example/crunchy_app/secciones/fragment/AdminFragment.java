package com.example.crunchy_app.secciones.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.DBconnection.JsonExporter;
import com.example.crunchy_app.R;
import com.example.crunchy_app.pedidos.activity.HistorialPedidosActivity;
import com.example.crunchy_app.pedidos.model.Pedido;
import com.example.crunchy_app.pedidos.model.ProductoDelPedido;
import com.example.crunchy_app.productos.DAO.ValorAtributoProductoDao;
import com.example.crunchy_app.productos.activity.GestionProductosActivity;
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.ValorAtributoProducto;
import com.example.crunchy_app.reportes.activity.ReportesActivity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class AdminFragment extends Fragment {

    private TextView txtGanancias;

    private float cantidadChicharron=0;
    private int cantidadChorizos=0;

    private float cantidadChicharronVendidos=0;
    private int cantidadChorizosVendidos=0;

    private AppDataBase db;

    public AdminFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        txtGanancias = view.findViewById(R.id.gananciasValor);
        db = AppDataBase.getInstance(getContext());


        cargarGanancias(); //  llamada inicial
        SharedPreferences prefs = requireContext().getSharedPreferences("stock_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        if(!prefs.contains("valor_por_gramo")){
            mostrarValorDelGramoForm(prefsEditor);
        }
        cantidadChicharron = prefs.getFloat("chicharron", 0);
        cantidadChorizos = prefs.getInt("chorizos", 0);

        SharedPreferences productosVendidos = requireContext().getSharedPreferences("productos_vendidos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = productosVendidos.edit();
        if(!productosVendidos.contains("chicharron_vendido") && !productosVendidos.contains("chorizo_vendido")){
            editor.putFloat("chicharron_vendido", 0);
            editor.putInt("chorizo_vendido", 0);
            editor.apply();
        }

        actualizarCantidadChicharron(view);
        actualizarCantidadChorizos(view);
        Button btnHistorial = view.findViewById(R.id.btnHistorial);
        btnHistorial.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HistorialPedidosActivity.class);
            startActivity(intent);
        });

        Button btnGestionProductos = view.findViewById(R.id.btnGestionProductos);
        btnGestionProductos.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), GestionProductosActivity.class);
            startActivity(intent);
        });


        Button btnGuardarChicharron = view.findViewById(R.id.btnGuardarChicharron);

        btnGuardarChicharron.setOnClickListener(v -> {
            EditText inputMaxChicharron = view.findViewById(R.id.inputMaxChicharron);
            String valorInput = inputMaxChicharron.getText().toString().trim();
            if (!valorInput.isEmpty() && Float.parseFloat(valorInput) > 0) {
                cantidadChicharron += Float.parseFloat(valorInput);
                actualizarCantidadChicharron(view);
                inputMaxChicharron.setText("");
            } else {
                inputMaxChicharron.setError("Este campo no puede estar vacío");
            }
            prefs.edit().putFloat("chicharron", cantidadChicharron).apply();
        });

        Button btnRestarChicharron = view.findViewById(R.id.btnRestarChicharron);

        btnRestarChicharron.setOnClickListener(v -> {
            EditText inputMaxChicharron = view.findViewById(R.id.inputMaxChicharron);
            String valorInput = inputMaxChicharron.getText().toString().trim();

            if (!valorInput.isEmpty()) {
                try {
                    float cantidadInput = Float.parseFloat(valorInput);
                    if (cantidadInput > 0) {
                        cantidadChicharron = prefs.getFloat("chicharron", 0);
                        float chicharronVendido = productosVendidos.getFloat("chicharron_vendido", 0);
                        float nuevoStock = cantidadChicharron - cantidadInput;

                        if (nuevoStock >= 0 && nuevoStock >= chicharronVendido) {
                            cantidadChicharron = nuevoStock;
                            prefs.edit().putFloat("chicharron", cantidadChicharron).apply();
                            actualizarCantidadChicharron(view);
                            inputMaxChicharron.setText("");
                        } else {
                            inputMaxChicharron.setError("No se puede restar más de lo disponible o de lo ya vendido");
                        }
                    } else {
                        inputMaxChicharron.setError("Ingresa un valor mayor que cero");
                    }
                } catch (NumberFormatException e) {
                    inputMaxChicharron.setError("Valor inválido");
                }
            } else {
                inputMaxChicharron.setError("Este campo no puede estar vacío");
            }
        });


        Button btnGuardarChorizos = view.findViewById(R.id.btnGuardarChorizos);

        btnGuardarChorizos.setOnClickListener(v -> {
            EditText inputMaxChorizos = view.findViewById(R.id.inputMaxChorizos);
            String valorInput = inputMaxChorizos.getText().toString().trim();
            if (!valorInput.isEmpty() && Integer.parseInt(valorInput) > 0) {
                cantidadChorizos += Integer.parseInt(valorInput);
                actualizarCantidadChorizos(view);
                inputMaxChorizos.setText("");
            } else {
                inputMaxChorizos.setError("Este campo no puede estar vacío");
            }
            prefs.edit().putInt("chorizos", cantidadChorizos).apply();

        });

        Button btnRestarChorizos = view.findViewById(R.id.btnRestarChorizos);
        btnRestarChorizos.setOnClickListener(v -> {
            EditText inputMaxChorizos = view.findViewById(R.id.inputMaxChorizos);
            String valorInput = inputMaxChorizos.getText().toString().trim();

            if (!valorInput.isEmpty()) {
                try {
                    int cantidadInput = Integer.parseInt(valorInput);
                    if (cantidadInput > 0) {
                        cantidadChorizos = prefs.getInt("chorizos", 0);
                        int chorizoVendido = productosVendidos.getInt("chorizo_vendido", 0);
                        int nuevoStock = cantidadChorizos - cantidadInput;

                        if (nuevoStock >= 0 && nuevoStock >= chorizoVendido) {
                            cantidadChorizos = nuevoStock;
                            prefs.edit().putInt("chorizos", cantidadChorizos).apply();
                            actualizarCantidadChorizos(view);
                            inputMaxChorizos.setText("");
                        } else {
                            inputMaxChorizos.setError("No se puede restar más de lo disponible o de lo ya vendido");
                        }
                    } else {
                        inputMaxChorizos.setError("Ingresa un valor mayor que cero");
                    }
                } catch (NumberFormatException e) {
                    inputMaxChorizos.setError("Valor inválido");
                }
            } else {
                inputMaxChorizos.setError("Este campo no puede estar vacío");
            }
        });


        Button btnActualizarValorPorGramo = view.findViewById(R.id.btnValorGramo);
        btnActualizarValorPorGramo.setOnClickListener(v -> {
            mostrarValorDelGramoForm(prefsEditor);
        });

        Button btnVerReportes = view.findViewById(R.id.btnVerReportes);
        btnVerReportes.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ReportesActivity.class);
            startActivity(intent);
        });


        return view;
    }

    private void actualizarCantidadChorizos(View view) {
        TextView txtChorizos = view.findViewById(R.id.chorizosLabel);
        txtChorizos.setText("Chorizos: " + cantidadChorizosVendidos + " / " + cantidadChorizos+" Unidades");
        actualizarProgressBarChorizos(view);
        SharedPreferences productosVendidos = requireContext().getSharedPreferences("productos_vendidos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = productosVendidos.edit();
        editor.putInt("chorizo_vendido", cantidadChorizosVendidos);
        editor.apply();
    }

    private void actualizarCantidadChicharron(View view) {
        TextView txtChicharron = view.findViewById(R.id.chicharronLabel);
        txtChicharron.setText("Chicharrón: " + cantidadChicharronVendidos + " / " + cantidadChicharron+ " gr");
        actualizarProgressBarChicharron(view);
        SharedPreferences productosVendidos = requireContext().getSharedPreferences("productos_vendidos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = productosVendidos.edit();
        editor.putFloat("chicharron_vendido", cantidadChicharronVendidos);
        editor.apply();
    }

    private void actualizarProgressBarChicharron(View view) {
        int progress = (cantidadChicharron == 0) ? 0 :
                (int) ((double) cantidadChicharronVendidos / cantidadChicharron * 100);
        ProgressBar progressBar = view.findViewById(R.id.progressChicharron);
        animateProgressBar(progressBar, progress);
    }

    private void actualizarProgressBarChorizos(View view) {
        int progress = (cantidadChorizos == 0) ? 0 :
                (int) ((double) cantidadChorizosVendidos / cantidadChorizos * 100);
        ProgressBar progressBar = view.findViewById(R.id.progressChorizos);
        animateProgressBar(progressBar, progress);
    }

    private void animateProgressBar(ProgressBar progressBar, int progressTo) {
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", progressBar.getProgress(), progressTo);
        animation.setDuration(500);
        animation.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarGanancias();
        cargarCantidadChicharronYChorizos();
    }

    private void cargarGanancias() {
        new Thread(() -> {
            List<Pedido> pedidosPagados = db.pedidoDao().getPedidosPorEstado(3);
            List<ProductoDelPedido> todosProductos = db.productoDelPedidoDao().getAll();
            List<Producto> productos = db.productoDao().getAll();

            double totalGanancias = 0;

            for (Pedido pedido : pedidosPagados) {
                for (ProductoDelPedido pdp : todosProductos) {
                    if (pdp.getIdPedido().equals(pedido.getIdPedido())) {
                        for (Producto producto : productos) {
                            if (producto.getIdProducto().equals(pdp.getIdProducto())) {
                                if(producto.getIdProducto() == 41){
                                    ValorAtributoProductoDao atributoProductoDao = db.valorAtributoProductoDao();
                                    String productoIdFormat = String.format("%d%d", producto.getIdProducto(), pedido.getIdPedido());
                                    float chicharron = atributoProductoDao.getValorAtributoProductoPersonalizado(Integer.valueOf(productoIdFormat)).getValorAtributoProducto();

                                    SharedPreferences prefs = requireContext().getSharedPreferences("stock_prefs", Context.MODE_PRIVATE);
                                    int valorPorGramo = (int) prefs.getFloat("valor_por_gramo", 0.0f);

                                    totalGanancias += chicharron * valorPorGramo;
                                    break;
                                }
                                totalGanancias += producto.getValorProducto() * pdp.getCantidad();
                                break;
                            }
                        }
                    }
                }
            }

            double finalTotalGanancias = totalGanancias;
            requireActivity().runOnUiThread(() -> animarGanancias(finalTotalGanancias));
        }).start();
    }

    private void cargarCantidadChicharronYChorizos() {
        cantidadChicharronVendidos = 0;
        cantidadChorizosVendidos = 0;

        new Thread(() -> {
            List<Integer> estadosValidos = Arrays.asList(1, 2, 3);
            List<Pedido> pedidosFiltrados = db.pedidoDao().getListaPedidosPorEstados(estadosValidos);
            List<ProductoDelPedido> todosProductos = db.productoDelPedidoDao().getAll();
            List<Producto> productos = db.productoDao().getAll();
            ValorAtributoProductoDao atributoProductoDao = db.valorAtributoProductoDao();

            for (Pedido pedido : pedidosFiltrados) {
                for (ProductoDelPedido pdp : todosProductos) {
                    if (pdp.getIdPedido().equals(pedido.getIdPedido())) {
                        for (Producto producto : productos) {
                            if (producto.getIdProducto().equals(pdp.getIdProducto())) {

                                if (producto.getIdProducto() == 41) {
                                    String productoIdFormat = String.format("%d%d", producto.getIdProducto(), pedido.getIdPedido());
                                    ValorAtributoProducto valor = atributoProductoDao.getValorAtributoProductoPersonalizado(Integer.valueOf(productoIdFormat));
                                    if (valor != null) {
                                        cantidadChicharronVendidos += valor.getValorAtributoProducto();
                                    }
                                } else if (producto.getIdProducto() == 42) {
                                    cantidadChorizosVendidos += pdp.getCantidad();
                                } else {
                                    for (ValorAtributoProducto vap : atributoProductoDao.getCantidadChicharron()) {
                                        if (vap.getIdProducto().equals(producto.getIdProducto())) {
                                            cantidadChicharronVendidos += vap.getValorAtributoProducto() * pdp.getCantidad();
                                            break;
                                        }
                                    }
                                    for (ValorAtributoProducto vap : atributoProductoDao.getCantidadChorizo()) {
                                        if (vap.getIdProducto().equals(producto.getIdProducto())) {
                                            cantidadChorizosVendidos += vap.getValorAtributoProducto() * pdp.getCantidad();
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            requireActivity().runOnUiThread(() -> {
                View view = getView();
                if (view != null) {
                    actualizarCantidadChicharron(view);
                    actualizarCantidadChorizos(view);
                }
            });
        }).start();
    }

    private void animarGanancias(double gananciasFinales) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, (float) gananciasFinales);
        animator.setDuration(1000);
        animator.setInterpolator(new DecelerateInterpolator());

        animator.addUpdateListener(animation -> {
            float valorAnimado = (float) animation.getAnimatedValue();
            String texto = "$" + String.format("%,.0f", valorAnimado) + " COP";
            txtGanancias.setText(texto);
        });

        animator.start();
    }

    private void mostrarValorDelGramoForm(SharedPreferences.Editor prefsEditor) {
        new Thread(() -> {
            // 1. Consultar desde Room (en segundo plano)
            Producto chorizoActual = db.productoDao().getProductoById(42);
            Producto bolloActual = db.productoDao().getProductoById(43);
            float valorGramoActual = PreferenceManager
                    .getDefaultSharedPreferences(requireContext())
                    .getFloat("valor_por_gramo", 0f);

            // 2. Volver al hilo principal para mostrar el diálogo
            requireActivity().runOnUiThread(() -> {
                // Inputs
                final EditText inputValorGramo = new EditText(requireContext());
                final EditText inputValorBollo = new EditText(requireContext());
                final EditText inputValorChorizo = new EditText(requireContext());

                inputValorGramo.setHint("Valor del gramo de chicharrón");
                inputValorBollo.setHint("Valor del bollo");
                inputValorChorizo.setHint("Valor del chorizo");

                // Prellenar valores
                inputValorGramo.setText(valorGramoActual > 0 ? String.valueOf(valorGramoActual) : "");
                inputValorBollo.setText(bolloActual != null ? String.valueOf(bolloActual.getValorProducto()) : "");
                inputValorChorizo.setText(chorizoActual != null ? String.valueOf(chorizoActual.getValorProducto()) : "");

                // LayoutParams
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                int margin = (int) (16 * getResources().getDisplayMetrics().density);
                lp.setMargins(margin, 0, margin, 0);

                inputValorGramo.setLayoutParams(lp);
                inputValorBollo.setLayoutParams(lp);
                inputValorChorizo.setLayoutParams(lp);

                // Títulos
                TextView tituloGramo = new TextView(requireContext());
                tituloGramo.setText("💰 Valor por gramo de chicharrón");
                tituloGramo.setPadding(margin, margin, margin, 8);

                TextView tituloBollo = new TextView(requireContext());
                tituloBollo.setText("🥚 Valor del bollo");
                tituloBollo.setPadding(margin, margin, margin, 8);

                TextView tituloChorizo = new TextView(requireContext());
                tituloChorizo.setText("🌭 Valor del chorizo");
                tituloChorizo.setPadding(margin, margin, margin, 8);

                // Layout del formulario
                LinearLayout container = new LinearLayout(requireContext());
                container.setOrientation(LinearLayout.VERTICAL);
                container.addView(tituloGramo);
                container.addView(inputValorGramo);
                container.addView(tituloBollo);
                container.addView(inputValorBollo);
                container.addView(tituloChorizo);
                container.addView(inputValorChorizo);

                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Ingrese valores personalizados");
                builder.setMessage("Modifique los valores o deje los existentes.");
                builder.setView(container);

                builder.setPositiveButton("Guardar", (dialog, which) -> {
                    String valorGramoStr = inputValorGramo.getText().toString().trim();
                    String valorBolloStr = inputValorBollo.getText().toString().trim();
                    String valorChorizoStr = inputValorChorizo.getText().toString().trim();

                    if (!valorGramoStr.isEmpty() && !valorBolloStr.isEmpty() && !valorChorizoStr.isEmpty()) {
                        try {
                            float valorGramo = Float.parseFloat(valorGramoStr);
                            float valorBollo = Float.parseFloat(valorBolloStr);
                            float valorChorizo = Float.parseFloat(valorChorizoStr);

                            prefsEditor.putFloat("valor_por_gramo", valorGramo);
                            prefsEditor.apply();

                            Producto chorizoActualizado = new Producto(42, "chorizo personalizado", 6, valorChorizo);
                            Producto bolloActualizado = new Producto(43, "bollo personalizado", 6, valorBollo);

                            new Thread(() -> {
                                db.productoDao().update(bolloActualizado);
                                db.productoDao().update(chorizoActualizado);

                                JsonExporter.exportProductos(requireContext(), db.productoDao().getAll());
                                requireActivity().runOnUiThread(() -> {
                                    Toast.makeText(requireContext(), "Valores guardados correctamente", Toast.LENGTH_SHORT).show();
                                });
                            }).start();

                        } catch (NumberFormatException e) {
                            Toast.makeText(requireContext(), "Por favor, ingrese un número válido.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(requireContext(), "Todos los campos deben estar completos.", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

                builder.create().show();
            });
        }).start();
    }

}

