package com.cristian.controldepedidos.controller.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cristian.controldepedidos.R;
import com.cristian.controldepedidos.controller.database.OrderController;
import com.cristian.controldepedidos.databinding.CustomerArticlesItemBinding;
import com.cristian.controldepedidos.model.Article;
import com.cristian.controldepedidos.model.Customer;
import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.model.Order;
import com.cristian.controldepedidos.utils.UtilMethods;

import java.util.ArrayList;

public class CustomerArticleAdapter extends RecyclerView.Adapter<CustomerArticleAdapter.CustomerArticleViewHolder>{
    private final Context context;
    private final ArrayList<Order> orders;
    private boolean[] expandedStatus;
    private DatabaseHelper dbh;
    private CustomerArticleInterface articleInterface;

    public CustomerArticleAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
        this.expandedStatus = new boolean[this.orders.size()];
        this.dbh = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public CustomerArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        com.cristian.controldepedidos.databinding.CustomerArticlesItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.customer_articles_item, parent, false);
        return new CustomerArticleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerArticleViewHolder holder, int position) {
        Order order = orders.get(position);
        Customer customer = order.getArticles().get(0).getCustomer();
        SQLiteDatabase db = dbh.getWritableDatabase();
        holder.bind(order, customer);

        // get payment and debt from the articles
        double payment = UtilMethods.calculatePayment(order.getArticles());
        double debt = UtilMethods.calculateDebt(order.getArticles());


        // set values
        holder.itemBinding.txtPayment.setText(String.valueOf(payment));
        holder.itemBinding.txtDebt.setText(String.valueOf(debt));

        // listener from button to share
        holder.itemBinding.btnShare.setOnClickListener(view -> {
            String message = String.valueOf(UtilMethods.shareInfo(order, customer));
            try {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                context.startActivity(shareIntent);
            }catch (RuntimeException e){
                Toast.makeText(context, "Something was wrong...", Toast.LENGTH_SHORT).show();
            }
        });

        // set recyclerView from customer_article
        ArticleItemAdapter adapter = new ArticleItemAdapter(context, order.getArticles());
        holder.itemBinding.customerArticleRv.setLayoutManager(new LinearLayoutManager(context));
        holder.itemBinding.customerArticleRv.setAdapter(adapter);

        // show if click
        boolean isExpanded = expandedStatus[position];
        holder.itemBinding.customerArticleRv.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(view -> {
            expandedStatus[position] = !expandedStatus[position];
            notifyItemChanged(position);
        });

        // set listener from adapter
        adapter.setListener(new ArticleItemAdapter.ArticleItemInterface() {
            @Override
            public void onClickPay() {
                //order.getArticles().set(position, article);
                double payment = UtilMethods.calculatePayment(order.getArticles());
                double debt = UtilMethods.calculateDebt(order.getArticles());
                if(payment == order.getTotal()) order.setStatus(Order.STATUS_PAYED);
                holder.itemBinding.txtPayment.setText(String.valueOf(payment));
                holder.itemBinding.txtDebt.setText(String.valueOf(debt));
                notifyItemChanged(position);

                if(articleInterface != null) articleInterface.onUpdated(order);
            }

            @Override
            public void onClickCancel() {
                notifyItemChanged(position);
            }
        });
    }

    public void setInterface(CustomerArticleInterface articleInterface){
        this.articleInterface = articleInterface;
    }

    @Override
    public int getItemCount() {
        return this.orders.size();
    }

    public static class CustomerArticleViewHolder extends RecyclerView.ViewHolder {
        private CustomerArticlesItemBinding itemBinding;
        public CustomerArticleViewHolder(@NonNull CustomerArticlesItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
        public void bind(Object order, Object customer){
            itemBinding.setVariable(BR.order, order);
            itemBinding.setVariable(BR.customer, customer);
        }
    }

    public interface CustomerArticleInterface {
        void onUpdated(Order order);
    }
}
