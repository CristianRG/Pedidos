package com.cristian.controldepedidos.controller.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.cristian.controldepedidos.R;
import com.cristian.controldepedidos.controller.database.ArticleController;
import com.cristian.controldepedidos.controller.database.ProductController;
import com.cristian.controldepedidos.controller.transactions.ArticleTransaction;
import com.cristian.controldepedidos.databinding.CustomerArticleItemBinding;
import com.cristian.controldepedidos.model.Article;
import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.ui.dialogs.PayArticleDialog;

import java.util.ArrayList;

public class ArticleItemAdapter extends RecyclerView.Adapter<ArticleItemAdapter.ArticleItemViewHolder>{
    private Context context;
    private ArrayList<Article> articles;
    private CustomerArticleItemBinding itemBinding;
    private ArticleItemInterface listener;
    private DatabaseHelper dbh;
    public ArticleItemAdapter(Context context, ArrayList<Article> articles) {
        this.context = context;
        this.articles = articles;
        this.dbh = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ArticleItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.customer_article_item, parent, false);
        return new ArticleItemViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleItemViewHolder holder, int position) {
        Article article = articles.get(position);
        SQLiteDatabase db = dbh.getWritableDatabase();
        holder.bind(article);

        if(article.getStatus()!=0) {
            if(article.getPayment() == article.getTotal()){
                holder.itemBinding.btnPayArticle.setVisibility(View.GONE);
                holder.itemBinding.btnCancelArticle.setVisibility(View.GONE);
            }
            // listeners from buttons
            holder.itemBinding.btnPayArticle.setOnClickListener(view -> {
                PayArticleDialog dialog = new PayArticleDialog(context, article);
                dialog.setListener(a -> {
                    if(a.getPayment() == a.getTotal()) a.setStatus(Article.STATUS_PAYED);
                    articles.set(position, a);
                    ArticleController.updateArticle(db, a);
                    notifyItemChanged(position);
                    if (this.listener != null) listener.onClickPay();
                });
                dialog.show();
            });

            holder.itemBinding.btnCancelArticle.setOnClickListener(view -> {
                article.setStatus(Article.STATUS_CANCELLED);
                ArticleController.updateArticle(db, article);
                notifyItemChanged(position);
                if (this.listener != null) listener.onClickCancel();
            });
        }else {
            holder.itemBinding.btnPayArticle.setVisibility(View.GONE);
            holder.itemBinding.btnCancelArticle.setVisibility(View.GONE);
            holder.itemBinding.btnTurnOnArticle.setVisibility(View.VISIBLE);

            holder.itemBinding.btnTurnOnArticle.setOnClickListener(view -> {
                if(article.getStatus() == Article.STATUS_CANCELLED) article.setStatus(Article.STATUS_NOTHING);
                ArticleController.updateArticle(db, article);
                notifyItemChanged(position);
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.articles.size();
    }

    public void setListener(ArticleItemInterface listener){
        this.listener = listener;
    }


    public static class ArticleItemViewHolder extends RecyclerView.ViewHolder {

        private CustomerArticleItemBinding itemBinding;
        public ArticleItemViewHolder(@NonNull CustomerArticleItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        public void bind(Object object){
            itemBinding.setVariable(BR.article, object);
            itemBinding.executePendingBindings();
        }
    }

    public interface ArticleItemInterface {
        void onClickPay();
        void onClickCancel();
    }
}
