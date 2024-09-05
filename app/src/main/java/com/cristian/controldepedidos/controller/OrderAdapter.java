package com.cristian.controldepedidos.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cristian.controldepedidos.R;
import com.cristian.controldepedidos.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private ArrayList<Order> orderList;
    private boolean[] expandedStatus;  // Controla el estado expandido de cada orden
    private Context context;

    public OrderAdapter(Context context, ArrayList<Order> orderList) {
        this.orderList = orderList;
        this.context = context;
        this.expandedStatus = new boolean[orderList.size()];  // Inicializamos el array de expansión
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order currentOrder = orderList.get(position);
        ArrayList<Double> totals;
        totals = (ArrayList<Double>) currentOrder.getArticles().stream().map(article -> {
           return article.total;
        });
        double totalAmount = 0;
        for (double total:
             totals) {
            totalAmount += total;
        }

        holder.orderType.setText(currentOrder.type);
        holder.orderStatus.setText(currentOrder.status);
        holder.orderTotal.setText(String.format("Total: $%.2f", totalAmount));

        // Configurar el RecyclerView de Artículos
        ArticleAdapter articleAdapter = new ArticleAdapter(context, currentOrder.articles);
        holder.articlesRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.articlesRecyclerView.setAdapter(articleAdapter);

        // Actualizar el estado de expansión según expandedStatus
        boolean isExpanded = expandedStatus[position];
        holder.articlesRecyclerView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        //holder.expandArrow.setImageResource(isExpanded ? R.drawable.ic_expand_less : R.drawable.ic_expand_more);

        // Manejar clic para expandir/colapsar los artículos
        holder.itemView.setOnClickListener(v -> {
            expandedStatus[position] = !expandedStatus[position];  // Cambiamos el estado de expansión
            notifyItemChanged(position);  // Actualizar el elemento
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        public TextView orderType;
        public TextView orderStatus;
        public TextView orderTotal;
        public RecyclerView articlesRecyclerView;
        public ImageView expandArrow;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderType = itemView.findViewById(R.id.order_type);
            orderStatus = itemView.findViewById(R.id.order_status);
            orderTotal = itemView.findViewById(R.id.order_total);
            articlesRecyclerView = itemView.findViewById(R.id.articles_recycler_view);
            expandArrow = itemView.findViewById(R.id.expand_arrow);
        }
    }
}
