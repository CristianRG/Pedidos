package com.cristian.controldepedidos.controller.adapters;

import android.content.Context;
import android.content.Intent;
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

    public OrderAdapter(Context context, ArrayList<Order> orderList) {
        this.orderList = orderList;
        this.context = context;
        this.expandedStatus = new boolean[orderList.size()];  // Inicializamos el array de expansión
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
        AtomicReference<Double> total = new AtomicReference<>((double) 0);
        currentOrder.getArticles().forEach(article -> {
            total.updateAndGet(v -> new Double((double) (v + article.getTotal())));
        });
        currentOrder.setTotal(total.get().doubleValue());
        holder.bind(currentOrder);

        /*
        holder.orderType.setText(currentOrder.type);
        holder.orderDate.setText(currentOrder.getDate().toString());
        holder.orderStatus.setText(currentOrder.status);
        holder.orderTotal.setText(String.format("Total: $%.2f", total.get().doubleValue()));
         */

        // Configurar el RecyclerView de Artículos
        ArticleAdapter articleAdapter = new ArticleAdapter(context, currentOrder.articles);
        holder.itemBinding.articlesRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.itemBinding.articlesRecyclerView.setAdapter(articleAdapter);

        // Actualizar el estado de expansión según expandedStatus
        boolean isExpanded = expandedStatus[position];
        holder.itemBinding.articlesRecyclerView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        //holder.expandArrow.setImageResource(isExpanded ? R.drawable.ic_expand_less : R.drawable.ic_expand_more);

        // Manejar clic para expandir/colapsar los artículos
        holder.itemView.setOnClickListener(v -> {
            expandedStatus[position] = !expandedStatus[position];  // Cambiamos el estado de expansión
            notifyItemChanged(position);  // Actualizar el elemento
        });

        holder.edit.setOnClickListener(view -> {
            try {
                Intent detailsActivity = new Intent(context, OrderDetailsActivity.class);
                context.startActivity(detailsActivity);
            }catch (RuntimeException e){
                Toast.makeText(this.context, "Ha ocurrido un error...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateData(ArrayList<Order> orders){
        this.orderList = orders;
        this.expandedStatus = new boolean[this.orderList.size()];  // Inicializamos el array de expansión
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        public TextView orderType;
        public TextView orderDate;
        public TextView orderStatus;
        public TextView orderTotal;
        public ImageButton edit;
        public ImageButton details;

        public OrderItemBinding itemBinding;
        //public ImageView expandArrow;

        public OrderViewHolder(@NonNull OrderItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;

            /*
            orderType = itemView.findViewById(R.id.order_type);
            orderDate = itemView.findViewById(R.id.order_date);
            orderStatus = itemView.findViewById(R.id.order_status);
            orderTotal = itemView.findViewById(R.id.order_total);
             */
            //articlesRecyclerView = itemView.findViewById(R.id.articles_recycler_view);
            edit = itemView.findViewById(R.id.btnEditOrder);
            details = itemView.findViewById(R.id.btnDetailsOrder);
        }

        public void bind(Object object){
            itemBinding.setVariable(BR.order, object);
            itemBinding.executePendingBindings();
        }
    }
}
