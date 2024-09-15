package com.cristian.controldepedidos.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cristian.controldepedidos.R;
import com.cristian.controldepedidos.controller.adapters.OrderAdapter;
import com.cristian.controldepedidos.controller.database.OrderController;
import com.cristian.controldepedidos.controller.transactions.OrderTransaction;
import com.cristian.controldepedidos.model.Article;
import com.cristian.controldepedidos.model.Customer;
import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.model.Order;
import com.cristian.controldepedidos.model.Product;
import com.cristian.controldepedidos.utils.QuickSort;

import java.text.ParseException;
import java.util.ArrayList;
import com.cristian.controldepedidos.databinding.FramentOrdersBinding;

public class OrderFragment extends Fragment {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    public ArrayList<Order> orderList;
    private DatabaseHelper dbh;
    private  FramentOrdersBinding binding;

    public OrderFragment () {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FramentOrdersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbh = new DatabaseHelper(getContext());
        // Inicializa el RecyclerView
        recyclerView = binding.ordersRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializa el ArrayList de Order
        orderList = new ArrayList<>();
        orderList = OrderTransaction.getOrdersTransaction(dbh);
        try {
            QuickSort.quickSort(orderList, 0, orderList.size() - 1);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // Configura el Adapter
        orderAdapter = new OrderAdapter(getContext(), orderList, true);
        recyclerView.setAdapter(orderAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        orderList = OrderTransaction.getOrdersTransaction(dbh);
        try {
            QuickSort.quickSort(orderList, 0, orderList.size() - 1);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        orderAdapter.updateData(orderList);
        orderAdapter.notifyDataSetChanged();
    }
}
