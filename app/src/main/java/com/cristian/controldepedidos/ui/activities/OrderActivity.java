package com.cristian.controldepedidos.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cristian.controldepedidos.R;
import com.cristian.controldepedidos.controller.adapters.ArticleAdapter;
import com.cristian.controldepedidos.controller.database.OrderController;
import com.cristian.controldepedidos.controller.transactions.OrderTransaction;
import com.cristian.controldepedidos.databinding.ActivityOrderBinding;
import com.cristian.controldepedidos.model.Article;
import com.cristian.controldepedidos.model.Customer;
import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.model.Order;
import com.cristian.controldepedidos.model.Product;
import com.cristian.controldepedidos.ui.dialogs.AddArticleDialog;
import com.cristian.controldepedidos.ui.dialogs.ListenerArticle;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    private ActivityOrderBinding binding;
    ArticleAdapter adapter;
    ArrayList<Article> articles;
    private DatabaseHelper dbh;
    private double total;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbh = new DatabaseHelper(this);

        // vincular el layout usando databinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order);
        articles = new ArrayList<>();

        // acceder a elementos
        String[] types = {"Shein", "Mercado Libre", "Avon", "Otro"};
        ArrayAdapter<String> arrayAdapterTypes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
        binding.spinnerTypeOrder.setAdapter(arrayAdapterTypes);

        String[] statusList = {"Registrado"};
        ArrayAdapter<String> arrayAdapterStatus = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, statusList);
        binding.spinnerStatusOrder.setAdapter(arrayAdapterStatus);
        total = calculateTotal(articles);
        binding.totalOrder.setText(String.format("Total: $%.2f", total));

        binding.rvArticles.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ArticleAdapter(this, articles, false);
        binding.rvArticles.setAdapter(adapter);

        binding.btnAddArticle.setOnClickListener(view -> {
            AddArticleDialog dialog = new AddArticleDialog(this, dbh, null);
            dialog.setListener(new ListenerArticle() {
                @Override
                public void onClickConfirm(Article article) {
                    articles.add(article);
                    total = calculateTotal(articles);
                    binding.totalOrder.setText(String.format("Total: $%.2f", total));
                    adapter.notifyItemInserted(articles.size() - 1);
                }

                @Override
                public void onClickCancel(Button button) {

                }
            });
            dialog.show();
        });

        binding.btnConfirmOrder.setOnClickListener(view -> {
            if(this.articles.size() > 0){

                // get data
                String type = binding.spinnerTypeOrder.getSelectedItem().toString();
                String status = binding.spinnerStatusOrder.getSelectedItem().toString();

                // format date to make the order
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate localDate = LocalDate.now();
                String date = localDate.format(format);

                // execute query and see the response
                boolean response = OrderTransaction.addOrderTransaction(dbh, new Order(0, type, articles, status, total, date));
                if(response){
                    finish();
                } else {
                    Toast.makeText(this, "Error al crear el pedido...", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "No hay articulos", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnCancelOrder.setOnClickListener(view -> {
            if(this.articles.size() > 0){
                new AlertDialog.Builder(this)
                        .setTitle("¿Estas seguro/a de querer salir?")
                        .setMessage("Al salir no se guardara el pedido")
                        .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setNegativeButton("Continuar", null)
                        .show();
            } else {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(this.articles.size() > 0){
            new AlertDialog.Builder(this)
                    .setTitle("¿Estas seguro/a de querer salir?")
                    .setMessage("Al salir no se guardara el pedido")
                    .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("Continuar", null)
                    .show();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if(this.articles.size() > 0){
            new AlertDialog.Builder(this)
                    .setTitle("¿Estas seguro/a de querer salir?")
                    .setMessage("Al salir no se guardara el pedido")
                    .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("Continuar", null)
                    .show();
        } else {
            super.onBackPressed();
        }
    }

    private double calculateTotal(ArrayList<Article> articles){
        double total = 0;
        for (Article article:
             articles) {
            total += article.getTotal();
        }
        return total;
    }
}
