package com.example.crunchy_app.pedidos.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crunchy_app.DBconnection.AppDataBase;
import com.example.crunchy_app.R;
import com.example.crunchy_app.pedidos.model.EstadoPedido;
import com.example.crunchy_app.pedidos.model.Locacion;
import com.example.crunchy_app.pedidos.model.Pedido;
import com.example.crunchy_app.pedidos.model.PedidoConEstado;
import com.example.crunchy_app.pedidos.model.ProductoDelPedido;
import com.example.crunchy_app.productos.DAO.ValorAtributoProductoDao;
import com.example.crunchy_app.productos.model.Producto;
import com.example.crunchy_app.productos.model.ValorAtributoProducto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PedidoHistorialAdapter extends RecyclerView.Adapter<PedidoHistorialAdapter.PedidoViewHolder> {

    private final List<PedidoConEstado> pedidos;
    private final List<ProductoDelPedido> productosDelPedido;
    private final List<Producto> productos;
    private final List<EstadoPedido> estados;

    private final List<Locacion> locaciones;

    private final AppDataBase db;

    private final Object totalLock = new Object();

    private final Object totalFinalLock = new Object();

    private final Object totalProductosLock = new Object();

    private final int CANTIDAD_GRAMOS_CHICHARRON = 80;
    private  List<ValorAtributoProducto> chicharronQuantities;


    public PedidoHistorialAdapter(List<PedidoConEstado> pedidos,
                                  List<ProductoDelPedido> productosDelPedido,
                                  List<Producto> productos,
                                  List<EstadoPedido> estados,
                                  List<Locacion> locaciones,
                                  AppDataBase db, List<ValorAtributoProducto> chicharronQuantities) {
        this.pedidos = pedidos;
        this.productosDelPedido = productosDelPedido;
        this.productos = productos;
        this.estados = estados;
        this.locaciones = locaciones;
        this.db = db;
        this.chicharronQuantities = chicharronQuantities;

    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pedido_historial_item, parent, false);
        return new PedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        PedidoConEstado pedidoConEstado = pedidos.get(position);
        Pedido pedido = pedidoConEstado.pedido;

        String nombreCompleto = pedido.getNombreCliente();
        holder.txtNombreCliente.setText(nombreCompleto);

        holder.txtIdDomiciliario.setText("Domiciliario: " + pedido.getNombreDomiciliario());
        holder.txtIdZona.setText("Zona: " + pedido.getIdLocacion());
        holder.txtIdDireccion.setText("Dirección: " + pedido.getDireccionCliente());
        holder.txtFecha.setText(pedido.getFecha().toString());


        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("hh:mm a");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.horaEntrega.setText("Hora de Entrega: " + pedido.getHoraEntrega().format(formatter));
            holder.txtHora.setText("Hora: " + pedido.getHora().format(formatter));
        }

        List<ProductoDelPedido> productosDeEstePedido = new ArrayList<>();
        for (ProductoDelPedido pdp : productosDelPedido) {
            if (pdp.getIdPedido().equals(pedido.getIdPedido())) {
                productosDeEstePedido.add(pdp);
            }
        }


        StringBuilder productosTxt = new StringBuilder();
        double total = 0;
        for (ProductoDelPedido pdp : productosDeEstePedido) {
            Producto producto = null;
            for (Producto p : productos) {
                if (p.getIdProducto().equals(pdp.getIdProducto())) {
                    producto = p;
                    break;
                }
            }
            if (producto != null) {
                productosTxt.append(producto.getNombreProducto())
                        .append(" x").append(pdp.getCantidad())
                        .append("\n");
                if (producto.getIdProducto() == 41) {
                    for (ValorAtributoProducto chicharronQuantity : chicharronQuantities) {
                        if (chicharronQuantity.getIdProducto().equals(producto.getIdProducto())) {
                            total += chicharronQuantity.getValorAtributoProducto() * CANTIDAD_GRAMOS_CHICHARRON;
                        }
                    }
                } else if(producto.getIdProducto() == 42){

                }
                else {
                    total += producto.getValorProducto() * pdp.getCantidad();
                }

            }
        }

        holder.txtProductos.setText(productosTxt.toString().trim()); // trim para evitar \n al final
        Locacion locacion = null;
        for (Locacion l : locaciones) {
            if (l.getIdLocacion().equals(pedido.getIdLocacion())) {
                locacion = l;
                break;
            }
        }
        double totalProductos = 0;
        for (ProductoDelPedido pdp : productosDeEstePedido) {
            Producto producto = null;
            for (Producto p : productos) {
                if (p.getIdProducto().equals(pdp.getIdProducto())) {
                    producto = p;
                    break;
                }
            }
            if (producto != null) {
                if (producto.getIdProducto() == 41) {
                    for (ValorAtributoProducto chicharronQuantity : chicharronQuantities) {
                        if (chicharronQuantity.getIdProducto().equals(producto.getIdProducto())) {
                            totalProductos += chicharronQuantity.getValorAtributoProducto() * CANTIDAD_GRAMOS_CHICHARRON;
                        }
                    }
                }else{
                    totalProductos += producto.getValorProducto() * pdp.getCantidad();
                }
            }
        }


        double valorDomicilio = locacion != null ? locacion.getValorDomicilio() : 0;
        double totalFinal = totalProductos + valorDomicilio;

        holder.txtTotal.setText("Total productos: $" + String.format("%,.0f", totalProductos));
        holder.txtValorDomicilio.setText("Domicilio: $" + String.format("%,.0f", valorDomicilio));
        holder.txtTotalFinal.setText("Total: $" + String.format("%,.0f", totalFinal));



        // Estado actual
        String nombreEstado = pedidoConEstado.estado.getNombreEstadoPedido();
        holder.txtEstado.setText("Estado: " + nombreEstado.toUpperCase());

        LinearLayout container = holder.itemView.findViewById(R.id.containerPedido);
        switch (nombreEstado.toLowerCase()) {
            case "encargada":
                container.setBackgroundColor(Color.parseColor("#FFF1F1")); break;
            case "preparando":
                container.setBackgroundColor(Color.parseColor("#FFFBE0")); break;
            case "pagado":
                container.setBackgroundColor(Color.parseColor("#E8FCE8")); break;
            case "en camino":
                container.setBackgroundColor(Color.parseColor("#E0F4FF")); break;
            default:
                container.setBackgroundColor(Color.WHITE); break;
        }
        holder.btnCancelar.setOnClickListener(v -> {
            new AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle("Cancelar pedido")
                    .setMessage("¿Estás seguro de que quieres cancelar este pedido?")
                    .setPositiveButton("Sí", (dialog, which) -> new Thread(() -> {
                        List<ProductoDelPedido> productos  =db.productoDelPedidoDao().getProductosByPedido(pedido.getIdPedido());
                        SharedPreferences prefs = holder.itemView.getContext().getSharedPreferences("productos_vendidos", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        double totalAEliminar = 0;

                        for (ProductoDelPedido pdp : productos) {
                            if(pdp.getIdProducto() == 41){
                                for (ValorAtributoProducto chicharronQuantity : chicharronQuantities) {
                                    if (chicharronQuantity.getIdProducto().equals(pdp.getIdProducto())) {
                                        totalAEliminar += chicharronQuantity.getValorAtributoProducto() * CANTIDAD_GRAMOS_CHICHARRON;
                                    }
                                }
                            }else{
                                totalAEliminar += pdp.getCantidad() * db.productoDao().getProductoById(pdp.getIdProducto()).getValorProducto();
                            }
                        }


                        db.productoDelPedidoDao().eliminarPorPedido(pedido.getIdPedido().toString());
                        db.pedidoDao().delete(pedido);

                        // Ejecuta en el hilo principal:
                        holder.itemView.post(() -> {
                            pedidos.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, pedidos.size());
                        });
                    }).start())
                    .setNegativeButton("No", null)
                    .show();
        });
        holder.btnCambiarEstado.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(holder.itemView.getContext(), holder.btnCambiarEstado);
            for (EstadoPedido estado : estados) {
                popup.getMenu().add(estado.getNombreEstadoPedido());
            }

            popup.setOnMenuItemClickListener(item -> {
                new Thread(() -> {
                    try {
                        int estadoOriginalId = pedido.getIdEstadoPedido();
                        int nuevoEstadoId = -1;
                        EstadoPedido nuevoEstadoSeleccionado = null;

                        for (EstadoPedido estado : estados) {
                            if (estado.getNombreEstadoPedido().contentEquals(Objects.requireNonNull(item.getTitle()))) {
                                nuevoEstadoId = estado.getIdEstadoPedido();
                                nuevoEstadoSeleccionado = estado;
                                break;
                            }
                        }

                        if (nuevoEstadoId == -1) return;

                        List<Integer> estadosActivos = Arrays.asList(1, 2, 3, 4); // IDs de estados que consumen stock

                        boolean eraActivo = estadosActivos.contains(estadoOriginalId);
                        boolean seraActivo = estadosActivos.contains(nuevoEstadoId);

                        if (!eraActivo && seraActivo) {
                            int chorizosNecesarios = 0;
                            float chicharronNecesario = 0f;


                            for (ProductoDelPedido pdp : productosDelPedido) {
                                if (pdp.getIdPedido().equals(pedido.getIdPedido())) {
                                    if (pdp.getIdProducto() == 40) {
                                        chorizosNecesarios += pdp.getCantidad();
                                    }

                                }
                            }


                            // Obtener stock disponible total
                            SharedPreferences prefs = holder.itemView.getContext().getSharedPreferences("stock_prefs", Context.MODE_PRIVATE);
                            int chorizosDisponibles = prefs.getInt("chorizos", 0);
                            int chicharronDisponible = prefs.getInt("chicharron", 0);

                            int chorizosVendidos = db.pedidoDao().getTotalChorizosVendidos();
                            float chicharronVendido = db.pedidoDao().getTotalChicharronVendido();

                            if (chorizosVendidos + chorizosNecesarios >= chorizosDisponibles &&
                                    chicharronVendido + chicharronNecesario >= chicharronDisponible) {

                                holder.itemView.post(() ->
                                        Toast.makeText(holder.itemView.getContext(), "No hay suficiente stock para activar este pedido", Toast.LENGTH_LONG).show()
                                );
                                return;
                            }
                        }

                        pedido.setIdEstadoPedido(nuevoEstadoId);
                        pedidoConEstado.estado = nuevoEstadoSeleccionado;
                        db.pedidoDao().update(pedido);

                        holder.itemView.post(() -> {
                            notifyItemChanged(holder.getAdapterPosition());

                        });


                    } catch (Exception e) {
                        e.printStackTrace();
                        holder.itemView.post(() ->
                                Toast.makeText(holder.itemView.getContext(), "Error al cambiar el estado", Toast.LENGTH_SHORT).show()
                        );
                    }
                }).start();

                return true;
            });

            popup.show();
        });

    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    static class PedidoViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreCliente, txtFecha, txtHora,txtProductos, txtEstado, txtTotal,txtValorDomicilio, txtTotalFinal,
                txtIdDomiciliario, txtIdZona, txtIdDireccion, horaEntrega;
        Button btnCambiarEstado, btnCancelar;




        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreCliente = itemView.findViewById(R.id.txtNombreCliente);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtProductos = itemView.findViewById(R.id.txtProductos);
            txtEstado = itemView.findViewById(R.id.txtEstado);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            btnCambiarEstado = itemView.findViewById(R.id.btnCambiarEstado);
            btnCancelar = itemView.findViewById(R.id.btnCancelar);
            txtValorDomicilio = itemView.findViewById(R.id.txtValorDomicilio);
            txtTotalFinal = itemView.findViewById(R.id.txtTotalFinal);
            txtHora = itemView.findViewById(R.id.txtHora);
            horaEntrega = itemView.findViewById(R.id.horaEntrega);
            txtIdDomiciliario = itemView.findViewById(R.id.IdDomiciliario);
            txtIdZona = itemView.findViewById(R.id.idZona);
            txtIdDireccion = itemView.findViewById(R.id.idDireccion);

        }
    }
}