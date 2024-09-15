package com.cristian.controldepedidos.controller.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cristian.controldepedidos.BR;
import com.cristian.controldepedidos.R;
import com.cristian.controldepedidos.model.Article;
import com.cristian.controldepedidos.databinding.ArticleItemBinding;
import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.ui.dialogs.AddArticleDialog;
import com.cristian.controldepedidos.ui.dialogs.ListenerArticle;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private ArrayList<Article> articleList;
    private Context context;
    private ArticleItemBinding itemBinding;
    private boolean showOptions;
    private ArticleListener listener;
    private DatabaseHelper dbh;

    // Constructor para el Adapter
    public ArticleAdapter(Context context, ArrayList<Article> articleList, boolean showOptions) {
        this.articleList = articleList;
        this.context = context;
        this.showOptions = showOptions;
    }
    public ArticleAdapter(Context context, ArrayList<Article> articleList, boolean showOptions, DatabaseHelper dbh) {
        this.articleList = articleList;
        this.context = context;
        this.showOptions = showOptions;
        this.dbh = dbh;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.article_item, parent, false);
        return new ArticleViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article currentArticle = articleList.get(position);
        Spannable link = new SpannableString(currentArticle.getProduct().getName());
        link.setSpan(new UnderlineSpan(), 0, link.length() ,0);
        holder.itemBinding.productName.setText(link);
        holder.bind(currentArticle);
        holder.itemBinding.btnEditArticle.setVisibility(showOptions ? View.VISIBLE : View.GONE);
        holder.itemBinding.btnDeleteArticle.setVisibility(showOptions ? View.VISIBLE : View.GONE);

        holder.itemBinding.productName.setOnClickListener(v -> {
            try {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentArticle.getProduct().getLink()));
                context.startActivity(browserIntent);
            } catch (RuntimeException e){
                Toast.makeText(this.context, "Ha ocurrido un error...", Toast.LENGTH_SHORT).show();
            }
        });
        // button to edit (listener)
        holder.itemBinding.btnEditArticle.setOnClickListener(view -> {
            AddArticleDialog dialog = new AddArticleDialog(context, dbh, currentArticle);
            dialog.setListener(new ListenerArticle() {
                @Override
                public void onClickConfirm(Article article) {
                    articleList.set(position, article);
                    if(listener!=null){
                        listener.onConfirmEdit(article, position);
                    }
                    dialog.dismiss();
                }

                @Override
                public void onClickCancel(Button button) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        });
        // button to delete (listener)
        holder.itemBinding.btnDeleteArticle.setOnClickListener(view -> {
            new AlertDialog.Builder(context)
                    .setTitle("¿Deseas eliminar este registro?")
                    .setMessage("Al eliminarlo no podras recuperarlo nuevamente")
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            articleList.remove(position);
                            listener.onDeleteItem(currentArticle, position);
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    public void updateData(ArrayList<Article> articles){
        this.articleList = articles;
    }
    public void setListener(ArticleListener listener){
        this.listener = listener;
    }
    public ArrayList<Article> getArticleList(){
        return articleList;
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {

        public ArticleItemBinding itemBinding;
        public ArticleViewHolder(@NonNull ArticleItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        public void bind(Object object){
            itemBinding.setVariable(BR.article, object);
            itemBinding.executePendingBindings();
        }
    }

    public interface ArticleListener {
        void onConfirmEdit(Article article, int position);
        void onDeleteItem(Article article, int position);
    }
}

