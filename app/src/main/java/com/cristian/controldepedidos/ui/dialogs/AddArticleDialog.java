package com.cristian.controldepedidos.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.cristian.controldepedidos.controller.database.CustomerController;
import com.cristian.controldepedidos.databinding.DialogAddArticleBinding;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import com.cristian.controldepedidos.R;
import com.cristian.controldepedidos.model.Article;
import com.cristian.controldepedidos.model.Customer;
import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.model.Product;

import java.util.ArrayList;
import java.util.Objects;

public class AddArticleDialog extends Dialog {
    private DialogAddArticleBinding binding;
    public ListenerArticle listener;
    private DatabaseHelper dbh;
    private Customer customer;
    private Article article;

    public AddArticleDialog(@NonNull Context context, DatabaseHelper databaseHelper, Article article) {
        super(context);
        this.dbh = databaseHelper;
        this.article = article;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_add_article, null, false);
        setContentView(binding.getRoot());
        Objects.requireNonNull(getWindow()).setLayout(680, 700);

        // Obtener la lista de clientes desde el controlador
        ArrayList<Customer> customers = CustomerController.getCustomers(dbh);

        // Crear el ArrayAdapter usando la lista de clientes
        ArrayAdapter<Customer> arrayAdapterNames = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, customers);
        binding.spinnerClient.setAdapter(arrayAdapterNames);

        // set data if receive a valid article
        if(article != null){
            int indexSelectedCustomer = arrayAdapterNames.getPosition(article.getCustomer());
            binding.spinnerClient.setSelection(indexSelectedCustomer);
            binding.editTextProductName.setText(article.getProduct().getName());
            binding.editTextProductLink.setText(article.getProduct().getLink().isEmpty() ? "" : article.getProduct().getLink());
            binding.editTextNumberPriceProduct.setText(String.valueOf(article.getProduct().getPrice()));
            customer = article.getCustomer();
            binding.titleAddArticle.setText("Editar articulo");
            binding.btnAddArticle.setText("Editar");
            binding.rowStatus.setVisibility(View.VISIBLE);
            binding.rowPayment.setVisibility(View.VISIBLE);
            String[] statusLis = Article.getStatusList();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, statusLis);
            binding.spinnerStatusArticle.setAdapter(adapter);
            int selected = adapter.getPosition(article.getStatusString());
            binding.spinnerStatusArticle.setSelection(selected);
            binding.editTextProductPayment.setText(String.valueOf(article.getPayment()));
            getWindow().setLayout(680, 930);
        }

        // Configurar el Listener para el Spinner
        binding.spinnerClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Obtener el cliente seleccionado
                customer = (Customer) adapterView.getItemAtPosition(i); // Guardar el cliente seleccionado
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Si no se selecciona nada (opcional)
            }
        });

        // Configurar el botón para agregar un artículo
        binding.btnAddArticle.setOnClickListener(view -> {
            if (!String.valueOf(binding.editTextProductName.getText()).isEmpty()
                    && !String.valueOf(binding.editTextNumberPriceProduct.getText()).isEmpty()) {

                String productName = String.valueOf(binding.editTextProductName.getText());
                String link = (binding.editTextProductLink.getText() != null) ? String.valueOf(binding.editTextProductLink.getText()) : "error";
                double price = Double.parseDouble(String.valueOf(binding.editTextNumberPriceProduct.getText()));

                if(this.article!=null){
                    double payment = Double.parseDouble(binding.editTextProductPayment.getText().toString());
                    int statusPosition = binding.spinnerStatusArticle.getSelectedItemPosition();

                    if(payment == price && statusPosition != 0) article.setStatus(Article.STATUS_PAYED);
                    else {
                        article.setStatus(statusPosition);
                    }

                    article.setPayment(payment);
                    article.setProduct(new Product(article.getProduct().getId(), productName, link, price));
                    article.setCustomer(customer);
                    article.setTotal(price);
                }
                else{
                    this.article = new Article(0, new Product(0, productName, link, price),  customer, 1, 0, price, price);
                }


                if (this.listener != null) {
                    this.listener.onClickConfirm(article);
                }
                dismiss();
            }
        });

        // Configurar el botón de cancelar
        binding.btnCancel.setOnClickListener(view -> {
            if (this.listener != null) {
                this.listener.onClickCancel();
            }
            dismiss();
        });
    }
    public void setListener(ListenerArticle listener){
        this.listener = listener;
    }
}
