package com.cristian.controldepedidos.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cristian.controldepedidos.R;
import com.cristian.controldepedidos.controller.adapters.CustomerAdapter;
import com.cristian.controldepedidos.controller.database.CustomerController;
import com.cristian.controldepedidos.model.Customer;
import com.cristian.controldepedidos.model.DatabaseHelper;

import java.util.ArrayList;

public class CustomerFragment extends Fragment {
    private RecyclerView recyclerView;
    private CustomerAdapter adapter;
    private ArrayList<Customer> customers;
    private DatabaseHelper dbh;
    public CustomerFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customers, container, false);  // Asegúrate que sea el layout correcto
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbh = new DatabaseHelper(getContext());
        recyclerView = view.findViewById(R.id.customers_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        customers = CustomerController.getCustomers(dbh);

        //adapter
        adapter = new CustomerAdapter(getContext(), customers);
        // set adapter
        recyclerView.setAdapter(adapter);
    }

    private void populateCustomers(){
        this.customers.add(new Customer(1, "Cristian", "pedro@gmail.com", "+524779082292", 0));
        this.customers.add(new Customer(2, "Juan", "pedro@gmail.com", "45", 1));
        this.customers.add(new Customer(3, "Luis", "pedro@gmail.com", "45", 0));
        this.customers.add(new Customer(4, "Fernando", "pedro@gmail.com", "45", 1));

    }

    @Override
    public void onResume() {
        super.onResume();
        customers = CustomerController.getCustomers(dbh);
        adapter.updateData(customers);
        adapter.notifyDataSetChanged();
    }
}
