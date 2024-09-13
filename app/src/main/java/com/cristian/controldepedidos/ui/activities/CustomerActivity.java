package com.cristian.controldepedidos.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cristian.controldepedidos.R;
import com.cristian.controldepedidos.controller.database.CustomerController;
import com.cristian.controldepedidos.databinding.ActivityCustomerBinding;
import com.cristian.controldepedidos.model.Customer;
import com.cristian.controldepedidos.model.DatabaseHelper;

public class CustomerActivity extends AppCompatActivity {

    private ActivityCustomerBinding binding;
    private DatabaseHelper dbh;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer);
        dbh = new DatabaseHelper(this);

        binding.btnConfirmCustomer.setOnClickListener(view -> {
            String name = String.valueOf(binding.editTextCustomerName.getText());
            String email = String.valueOf(binding.editTextCustomerEmail.getText());
            String phone = String.valueOf(binding.editTextCustomerPhone.getText());

            if(!name.isEmpty() && !phone.isEmpty()){

                boolean response = CustomerController.addCustomer(dbh, new Customer(0, name, email, phone, 1));
                if(response){
                    finish();
                }
                else {
                    Toast.makeText(this, "Intente de nuevo...", Toast.LENGTH_SHORT).show();
                }


            }
            else {
                Toast.makeText(this, "No hay nombre o telefono", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnCancelCustomer.setOnClickListener(view -> {
            String name = String.valueOf(binding.editTextCustomerName.getText());
            String email = String.valueOf(binding.editTextCustomerEmail.getText());
            String phone = String.valueOf(binding.editTextCustomerPhone.getText());

            if(!name.isEmpty() || !phone.isEmpty()){

                new AlertDialog.Builder(this)
                        .setTitle("¿Seguro/a que quieres salir?")
                        .setMessage("Al salir no se guardara el cliente")
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
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        String name = String.valueOf(binding.editTextCustomerName.getText());
        String email = String.valueOf(binding.editTextCustomerEmail.getText());
        String phone = String.valueOf(binding.editTextCustomerPhone.getText());

        if(!name.isEmpty() || !phone.isEmpty()){

            new AlertDialog.Builder(this)
                    .setTitle("¿Seguro/a que quieres salir?")
                    .setMessage("Al salir no se guardara el cliente")
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
