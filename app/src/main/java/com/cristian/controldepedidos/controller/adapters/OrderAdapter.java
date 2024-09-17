package com.cristian.controldepedidos.controller.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cristian.controldepedidos.BR;
import com.cristian.controldepedidos.databinding.OrderItemBinding;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cristian.controldepedidos.R;
import com.cristian.controldepedidos.model.Order;
import com.cristian.controldepedidos.ui.activities.OrderDetailsActivity;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private ArrayList<Order> orderList;
    private boolean[] expandedStatus;  // Controla el estado expandido de cada orden
    private Context context;
    private OrderItemBinding binding;
    private boolean showOptions;

    public OrderAdapter(Context context, ArrayList<Order> orderList, boolean showOptions) {
        this.orderList = orderList;
        this.context = context;
        this.expandedStatus = new boolean[orderList.size()];  // Inicializamos el array de expansión
        this.showOptions = showOptions;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.order_item, parent, false
        );
        return new OrderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order currentOrder = orderList.get(position);

        holder.bind(currentOrder);
        holder.itemBinding.btnEditOrder.setVisibility(showOptions ? View.VISIBLE : View.GONE);
        holder.itemBinding.btnDetailsOrder.setVisibility(showOptions ? View.VISIBLE : View.GONE);

        // Configurar el RecyclerView de Artículos
        ArticleAdapter articleAdapter = new ArticleAdapter(context, currentOrder.articles, false);
        holder.itemBinding.articlesRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.itemBinding.articlesRecyclerView.setAdapter(articleAdapter);

        // Actualizar el estado de expansión según expandedStatus
        boolean isExpanded = expandedStatus[position];
        holder.itemBinding.articlesRecyclerView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        // Manejar clic para expandir/colapsar los artículos
        holder.itemView.setOnClickListener(v -> {
            expandedStatus[position] = !expandedStatus[position];  // Cambiamos el estado de expansión
            notifyItemChanged(position);  // Actualizar el elemento
        });

        holder.itemBinding.btnEditOrder.setOnClickListener(view -> {
            try {
                Intent detailsActivity = new Intent(context, OrderDetailsActivity.class);
                detailsActivity.putExtra("order", currentOrder);
                context.startActivity(detailsActivity);
            }catch (RuntimeException e){
                Toast.makeText(this.context, "Ha ocurrido un error...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateData(ArrayList<Order> orders){
        this.orderList = orders;
        this.expandedStatus = new boolean[this.orderList.size()];
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        public OrderItemBinding itemBinding;

        public OrderViewHolder(@NonNull OrderItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        public void bind(Object object){
            itemBinding.setVariable(BR.order, object);
            itemBinding.executePendingBindings();
        }
    }
}
