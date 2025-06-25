package com.example.crunchy_app.pedidos.activity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.R;
import com.example.crunchy_app.pedidos.model.Locacion;
import com.example.crunchy_app.pedidos.model.Pedido;

import java.text.BreakIterator;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class EditarPedidoActivity extends AppCompatActivity {

    private EditText etDireccion, etHoraEntrega;
    private Spinner spinnerZona;
    private AppDataBase db;
    private Pedido pedido;
    private List<Locacion> locaciones;
    private int pedidoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pedido);
        db = AppDataBase.getInstance(this);
        pedidoId = getIntent().getIntExtra("pedidoId", -1);

        etDireccion = findViewById(R.id.etDireccion);
        etHoraEntrega = findViewById(R.id.etHoraEntrega);
        spinnerZona = findViewById(R.id.spinnerZona);

        findViewById(R.id.btnGuardarCambios).setOnClickListener(v -> guardarCambios());
        findViewById(R.id.btnEditarCarrito).setOnClickListener(v -> {
            Intent i = new Intent(this, EditarCarritoActivity.class);
            i.putExtra("pedidoId", pedidoId);
            startActivity(i);
        });

        etHoraEntrega.setOnClickListener(v -> abrirHoraPicker());
        cargarDatos();
    }

    private void abrirHoraPicker() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
            etHoraEntrega.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute1));
        }, hour, minute, true).show();
    }

    private void cargarDatos() {
        new Thread(() -> {
            pedido = db.pedidoDao().getPedidoById(pedidoId);
            locaciones = db.locacionDao().getAllActivas();

            runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>((Context) this,
                        android.R.layout.simple_spinner_item,
                        locaciones.stream().map(Locacion::getNombreLocacion).collect(Collectors.toList()));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerZona.setAdapter(adapter);

                int pos = 0;
                for (int i = 0; i < locaciones.size(); i++) {
                    if (Objects.equals(locaciones.get(i).getIdLocacion(), pedido.getIdLocacion())) {
                        pos = i;
                        break;
                    }
                }
                spinnerZona.setSelection(pos);

                etDireccion.setText(pedido.getDireccionCliente());
                etHoraEntrega.setText(pedido.getHoraEntrega().toString());
            });
        }).start();
    }

    private void guardarCambios() {
        String direccion = etDireccion.getText().toString().trim();
        String horaEntrega = etHoraEntrega.getText().toString().trim();
        Locacion locacionSeleccionada = locaciones.get(spinnerZona.getSelectedItemPosition());

        if (direccion.isEmpty() || horaEntrega.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        pedido.setDireccionCliente(direccion);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pedido.setHoraEntrega(LocalTime.parse(horaEntrega));
        }
        pedido.setIdLocacion(locacionSeleccionada.getIdLocacion());

        new Thread(() -> {
            db.pedidoDao().update(pedido);
            runOnUiThread(() -> {
                Toast.makeText(this, "Pedido actualizado", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}
