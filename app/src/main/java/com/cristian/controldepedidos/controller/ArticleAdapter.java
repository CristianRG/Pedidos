package com.cristian.controldepedidos.controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cristian.controldepedidos.R;
import com.cristian.controldepedidos.model.Article;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private ArrayList<Article> articleList;
    private Context context;

    // Constructor para el Adapter
    public ArticleAdapter(Context context, ArrayList<Article> articleList) {
        this.articleList = articleList;
        this.context = context;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_item, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article currentArticle = articleList.get(position);
        Spannable link = new SpannableString(currentArticle.getProduct().getName());
        link.setSpan(new UnderlineSpan(), 0, link.length() ,0);
        holder.productName.setText(link);
        holder.customerName.setText(String.format("Customer: %s", currentArticle.customer.getName()));
        holder.articleStatus.setText(String.format("Status: %s", currentArticle.customer.getStatus()));
        holder.articlePayment.setText(String.format("Payment: $%.2f", currentArticle.payment));
        holder.articleDebt.setText(String.format("Debt: $%.2f", currentArticle.debt));
        holder.articleTotal.setText(String.format("Total: $%.2f", currentArticle.total));

        holder.productName.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentArticle.product.getLink()));
            context.startActivity(browserIntent);
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        public TextView productName;
        public TextView customerName;
        public TextView articleStatus;
        public TextView articlePayment;
        public TextView articleDebt;
        public TextView articleTotal;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            customerName = itemView.findViewById(R.id.customer_name);
            articleStatus = itemView.findViewById(R.id.article_status);
            articlePayment = itemView.findViewById(R.id.article_payment);
            articleDebt = itemView.findViewById(R.id.article_debt);
            articleTotal = itemView.findViewById(R.id.article_total);
        }
    }
}

