package com.example.crunchy_app.reportes.activity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.R;
import com.example.crunchy_app.reportes.model.ResumenPorDia;
import com.example.crunchy_app.reportes.util.GeneradorResumen;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ReportesActivity extends AppCompatActivity {

    private TextView txtFechaSeleccionada, txtResumen;
    private Button btnSeleccionarFecha, btnCargarResumen, btnGenerarResumen;
    private AppDataBase db;
    private LocalDate fechaSeleccionada = LocalDate.now();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);

        Toolbar toolbar = findViewById(R.id.toolbarReportes);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Reportes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        db = AppDataBase.getInstance(getApplicationContext());
        txtFechaSeleccionada = findViewById(R.id.txtFechaSeleccionada);
        txtResumen = findViewById(R.id.txtResumen);
        btnSeleccionarFecha = findViewById(R.id.btnSeleccionarFecha);
        btnCargarResumen = findViewById(R.id.btnCargarResumen);
        btnGenerarResumen = findViewById(R.id.btnGenerarResumen); // 👈 asegúrate de haberlo agregado al XML

        actualizarTextoFecha();

        btnSeleccionarFecha.setOnClickListener(v -> mostrarDatePicker());
        btnCargarResumen.setOnClickListener(v -> cargarResumenDesdeDB());
        btnGenerarResumen.setOnClickListener(v -> verificarYGenerarResumen());
    }

    private void actualizarTextoFecha() {
        txtFechaSeleccionada.setText("📅 Fecha seleccionada: " + fechaSeleccionada.toString());
    }

    private void mostrarDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePicker view, int year, int month, int dayOfMonth) -> {
                    fechaSeleccionada = LocalDate.of(year, month + 1, dayOfMonth);
                    actualizarTextoFecha();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void cargarResumenDesdeDB() {
        new Thread(() -> {
            ResumenPorDia resumen = db.resumenPorDiaDao().getResumenPorFecha(fechaSeleccionada.toString());

            runOnUiThread(() -> {
                if (resumen == null) {
                    txtResumen.setText("❌ No se encontró un resumen guardado para la fecha: " + fechaSeleccionada.toString() + ".\n\nPresiona 'Generar resumen del día' para crearlo.");
                } else {
                    txtResumen.setText(formatearResumen(resumen));
                }
            });
        }).start();
    }

    private void verificarYGenerarResumen() {
        new Thread(() -> {
            ResumenPorDia existente = db.resumenPorDiaDao().getResumenPorFecha(fechaSeleccionada.toString());

            runOnUiThread(() -> {
                if (existente != null) {
                    new AlertDialog.Builder(ReportesActivity.this)
                            .setTitle("Resumen ya existente")
                            .setMessage("Ya existe un resumen guardado para esta fecha. ¿Deseas reemplazarlo?")
                            .setPositiveButton("Sí, reemplazar", (dialog, which) -> generarResumen(true))
                            .setNegativeButton("Cancelar", null)
                            .show();
                } else {
                    generarResumen(false);
                }
            });
        }).start();
    }

    private void generarResumen(boolean eliminarPrevio) {
        new Thread(() -> {
            if (eliminarPrevio) {
                db.resumenPorDiaDao().eliminarResumenPorFecha(fechaSeleccionada.toString());
            }

            ResumenPorDia nuevoResumen = GeneradorResumen.generar(getApplicationContext(), fechaSeleccionada.toString());
            db.resumenPorDiaDao().insertarResumen(nuevoResumen);

            runOnUiThread(() -> {
                txtResumen.setText(formatearResumen(nuevoResumen));
            });
        }).start();
    }

    private String formatearResumen(ResumenPorDia r) {
        return "🧾 Resumen del día: " + r.getFecha() + "\n\n" +

                "💵 Ingresos por método de pago:\n" +
                "   • Efectivo: $" + String.format("%,.0f", r.getDineroEfectivo()) + " COP" + "\n" +
                "   • Transferencia: $" +  String.format("%,.0f", r.getDineroTransferencia()) + " COP" + "\n\n" +

                "🛒 Cantidades vendidas:\n" +
                "   • Chicharrón: " + r.getCantidadVendidaChicharron() + " gr\n" +
                "   • Chorizo: " + r.getCantidadVendidaChorizo() + " unidades\n" +
                "   • Bollo: " + r.getCantidadVendidaBollo() + " unidades\n\n" +

                "🥤 Ingresos por tipo de producto:\n" +
                "   • Bebida personal: $" + String.format("%,.0f", r.getDineroBebidaPersonal()) + " COP" + "\n" +
                "   • Bebida familiar: $" + String.format("%,.0f", r.getDineroBebidaFamiliar()) + " COP" + "\n" +
                "   • Bebida alcohólica: $" + String.format("%,.0f", r.getDineroBebidaAlcoholica()) + " COP" + "\n" +
                "   • Combos: $" + String.format("%,.0f", r.getDineroCombos()) + " COP" + "\n" +
                "   • Picadas: $" + String.format("%,.0f", r.getDineroPicadas()) + " COP" + "\n\n" +

                "📋 Número total de pedidos: " + r.getTotalPedidosDia() + "\n\n" +

                "📦 Top productos por tipo:\n\n" +
                "👉 Combos:\n" + r.getTopCombo() + "\n\n" +
                "👉 Picadas:\n" + r.getTopPicada() + "\n\n" +
                "👉 Bebida personal:\n" + r.getTopBebidaPersonal() + "\n\n" +
                "👉 Bebida familiar:\n" + r.getTopBebidaFamiliar() + "\n\n" +
                "👉 Bebida alcohólica:\n" + r.getTopBebidaAlcoholica();
    }
}
