package com.cristian.controldepedidos.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cristian.controldepedidos.R;
import com.cristian.controldepedidos.controller.adapters.ArticleAdapter;
import com.cristian.controldepedidos.controller.database.OrderArticleController;
import com.cristian.controldepedidos.controller.database.OrderController;
import com.cristian.controldepedidos.controller.transactions.ArticleTransaction;
import com.cristian.controldepedidos.databinding.ActivityOrderDetailsBinding;
import com.cristian.controldepedidos.model.Article;
import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.model.Order;
import com.cristian.controldepedidos.ui.dialogs.AddArticleDialog;
import com.cristian.controldepedidos.ui.dialogs.ListenerArticle;

import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity {

    private ActivityOrderDetailsBinding binding;
    private ArticleAdapter articleAdapter;
    private DatabaseHelper dbh;
    private SQLiteDatabase db;
    private ArrayList<Article> addedArticles;
    private ArrayList<Article> modifiedArticles;
    private ArrayList<Article> deletedArticles;
    private Order order;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_details);
        dbh = new DatabaseHelper(this);
        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra("order");

        if (order != null) {
            articleAdapter = new ArticleAdapter(this, order.getArticles(), true, dbh);
            binding.rvArticles.setLayoutManager(new LinearLayoutManager(this));
            binding.rvArticles.setAdapter(articleAdapter);
        }
        else {
            throw new RuntimeException("Error to load object");
        }
        String[] types = {"Shein", "Mercado Libre", "Avon", "Otro"};
        ArrayAdapter<String> orderTypes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
        binding.spinnerTypeOrderDetails.setAdapter(orderTypes);
        int indexSelectedType = orderTypes.getPosition(order.getType());
        binding.spinnerTypeOrderDetails.setSelection(indexSelectedType);

        String[] statusList = {"Cancelado","Registrado","Entregado","Pagado"};
        ArrayAdapter<String> statusListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, statusList);
        binding.spinnerStatusOrderDetails.setAdapter(statusListAdapter);
        int indexSelectedStatus = statusListAdapter.getPosition(order.getStatus());
        binding.spinnerStatusOrderDetails.setSelection(indexSelectedStatus);

        binding.totalOrder.setText(order.getTotalToString());

        addedArticles = new ArrayList<>();
        modifiedArticles = new ArrayList<>();
        deletedArticles = new ArrayList<>();

        // button to add new articles
        binding.btnAddArticleDetails.setOnClickListener(view -> {
            AddArticleDialog dialog = new AddArticleDialog(this, dbh, null);
            dialog.setListener(new ListenerArticle() {
                @Override
                public void onClickConfirm(Article article) {
                    addedArticles.add(article);
                    order.getArticles().add(article);
                    articleAdapter.updateData(order.getArticles());
                    articleAdapter.notifyItemInserted(order.getArticles().size() - 1);
                    order.setTotal(calculateTotal(order.getArticles()));
                    binding.totalOrder.setText(order.getTotalToString());
                }

                @Override
                public void onClickCancel(Button button) {

                }
            });

            dialog.show();
        });
        // TODO: Listener from adapter. onConfirmEdit and onDeleteItem
        articleAdapter.setListener(new ArticleAdapter.ArticleListener() {
            @Override
            public void onConfirmEdit(Article article, int position) {
                int indexAdded = addedArticles.indexOf(article);
                int indexUpdated = modifiedArticles.indexOf(article);
                Log.d("debug", "E: Index of added: " + indexAdded + "- Index of modified: " + indexUpdated);
                if(indexAdded==-1 && indexUpdated!=-1){
                    modifiedArticles.set(indexUpdated, article);
                    Log.d("debug", "E: article found in mfd with id: " + article.getId());
                }
                else if(indexAdded!=-1 && indexUpdated==-1){
                    addedArticles.set(indexAdded, article);
                    Log.d("debug", "E: article found in nad with id: " + article.getId());
                }
                else {
                    modifiedArticles.add(article);
                    Log.d("debug", "E: article not found in any list with id: " + article.getId());
                }

                articleAdapter.notifyItemChanged(position);
                order.setArticles(articleAdapter.getArticleList());
                order.setTotal(calculateTotal(order.getArticles()));
                binding.totalOrder.setText(order.getTotalToString());
            }

            @Override
            public void onDeleteItem(Article article, int position) {
                int indexModified = modifiedArticles.indexOf(article);
                int indexAdded = addedArticles.indexOf(article);

                if(indexAdded==-1 && indexModified!=-1){
                    modifiedArticles.remove(indexModified);
                    Log.d("debug", "D: article found in mfd with id: " + article.getId());
                    deletedArticles.add(article);
                } else if (indexAdded!=-1 && indexModified==-1) {
                    addedArticles.remove(indexAdded);
                    Log.d("debug", "D: article found in nad with id: " + article.getId());
                } else {
                    deletedArticles.add(article);
                }
                articleAdapter.notifyItemRemoved(position);
                articleAdapter.notifyItemRangeChanged(position, articleAdapter.getItemCount());
                order.setArticles(articleAdapter.getArticleList());
                order.setTotal(calculateTotal(order.getArticles()));
                binding.totalOrder.setText(order.getTotalToString());
            }
        });
        // confirm edit
        binding.btnConfirmEdit.setOnClickListener(view -> {
            // TODO: Get response. If user accept to save data so save changes. Otherwise not save anything
            // nothing change
            //if(addedArticles.size() == 0 && modifiedArticles.size() == 0 && deletedArticles.size() == 0){finish();}
            db = dbh.getWritableDatabase();
            db.beginTransaction();
            // update general data from order
            // new articles added
            if(addedArticles.size() != 0){
                try {
                    addArticles();
                }catch (SQLException e){
                    Toast.makeText(this, "Error to insert...", Toast.LENGTH_SHORT).show();
                    db.endTransaction();
                    return;
                }
            }
            // modified
            if(modifiedArticles.size() != 0){
                try {
                    modifiedArticles();
                }catch (SQLException e){
                    Toast.makeText(this, "Error to update...", Toast.LENGTH_SHORT).show();
                    db.endTransaction();
                    return;
                }
            }
            // deleted
            if(deletedArticles.size() != 0){
                try {
                    deletedArticles();
                }catch (SQLException e){
                    Toast.makeText(this, "Error to delete...", Toast.LENGTH_SHORT).show();
                    db.endTransaction();
                    return;
                }
            }
            // TODO: In case than order is empty delete order
            if(articleAdapter.getArticleList().isEmpty()){
                boolean deleted = OrderController.deleteOrder(db, order);
                if(!deleted){
                    db.endTransaction();
                    db.close();
                    return;
                }
                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();
                finish();
                return;
            }

            // TODO: Set properties in case the order not is empty
            order.setTotal(Double.parseDouble(String.valueOf(binding.totalOrder.getText())));
            order.setStatus(binding.spinnerStatusOrderDetails.getSelectedItem().toString());
            order.setType(binding.spinnerTypeOrderDetails.getSelectedItem().toString());
            boolean updated = OrderController.updateOrder(db, order);
            if(updated){
                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();
                finish();
            }
        });
        binding.btnCancelOrder.setOnClickListener(view -> {
            if (addedArticles.size() != 0 || modifiedArticles.size() != 0 || deletedArticles.size() != 0){
                new AlertDialog.Builder(this)
                        .setTitle("¿Salir sin guardar?")
                        .setMessage("Si sales los cambios no serán aplicados")
                        .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setNegativeButton("Continuar", null)
                        .show();
            }
        });
    }

    private void addArticles(){
        Order orderAddedArticles = order;
        orderAddedArticles.setArticles(addedArticles);
        boolean response = ArticleTransaction.addArticleTransaction(db, orderAddedArticles);
        if(!response)throw new SQLException("Not articles added");
    }

    private void modifiedArticles(){
        Order orderModifiedArticles = order;
        orderModifiedArticles.setArticles(modifiedArticles);
        boolean response = ArticleTransaction.updateArticleTransaction(db, orderModifiedArticles);
        if(!response)throw new SQLException("Not updated");
    }
    private void deletedArticles(){
        Order orderDeletedArticles = order;
        orderDeletedArticles.setArticles(deletedArticles);
        boolean response = ArticleTransaction.deleteArticleTransaction(db, orderDeletedArticles);
        if(!response)throw new SQLException("Not deleted");
    }
    private double calculateTotal(ArrayList<Article> articles){
        double total = 0;
        for (Article article:
             articles) {
            total += article.getTotal();
        }
        return total;
    }

    @Override
    public void onBackPressed() {
        if (addedArticles.size() != 0 || modifiedArticles.size() != 0 || deletedArticles.size() != 0){
            new AlertDialog.Builder(this)
                    .setTitle("¿Salir sin guardar?")
                    .setMessage("Si sales los cambios no serán aplicados")
                    .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("Continuar", null)
                    .show();
        }
        else {
            super.onBackPressed();
        }
    }
}