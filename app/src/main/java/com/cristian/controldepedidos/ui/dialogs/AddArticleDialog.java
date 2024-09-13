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

public class AddArticleDialog extends Dialog {
    private DialogAddArticleBinding binding;
    public ListenerArticle listener;
    private DatabaseHelper dbh;
    private Customer customer;

    public AddArticleDialog(@NonNull Context context, DatabaseHelper databaseHelper) {
        super(context);
        this.dbh = databaseHelper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_add_article, null, false);
        setContentView(binding.getRoot());
        getWindow().setLayout(650, 700);

        // Obtener la lista de clientes desde el controlador
        ArrayList<Customer> customers = CustomerController.getCustomers(dbh);

        // Crear el ArrayAdapter usando la lista de clientes
        ArrayAdapter<Customer> arrayAdapterNames = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, customers);
        binding.spinnerClient.setAdapter(arrayAdapterNames);

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

                String articleName = String.valueOf(binding.editTextProductName.getText());
                String link = (binding.editTextProductLink.getText() != null) ? String.valueOf(binding.editTextProductLink.getText()) : "error";
                Double price = Double.parseDouble(String.valueOf(binding.editTextNumberPriceProduct.getText()));

                // Aquí usamos el cliente seleccionado en el Spinner con su ID y nombre
                Article article = new Article(0, new Product(0, articleName, link, price),  customer,
                        1, 0, 0, price);

                if (this.listener != null) {
                    this.listener.onClickConfirm(article);
                }
                dismiss();
            }
        });

        // Configurar el botón de cancelar
        binding.btnCancel.setOnClickListener(view -> {
            if (this.listener != null) {
                this.listener.onClickCancel(binding.btnCancel);
            }
            dismiss();
        });
    }
    public void setListener(ListenerArticle listener){
        this.listener = listener;
    }
}
