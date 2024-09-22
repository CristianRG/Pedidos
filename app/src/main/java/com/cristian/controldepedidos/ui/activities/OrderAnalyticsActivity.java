package com.cristian.controldepedidos.ui.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cristian.controldepedidos.R;
import com.cristian.controldepedidos.controller.adapters.CustomerArticleAdapter;
import com.cristian.controldepedidos.controller.database.OrderController;
import com.cristian.controldepedidos.databinding.ActivityOrderAnalyticsBinding;
import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.model.Order;
import com.cristian.controldepedidos.utils.UtilMethods;

import java.util.ArrayList;

public class OrderAnalyticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper dbh = new DatabaseHelper(this);
        SQLiteDatabase db = dbh.getWritableDatabase();
        com.cristian.controldepedidos.databinding.ActivityOrderAnalyticsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_order_analytics);
        Intent intent = getIntent();
        Order order = (Order) intent.getSerializableExtra("order");
        assert order != null;
        ArrayList<Order> orders = UtilMethods.sortOrder(order);

        // set recyclerView
        CustomerArticleAdapter adapter = new CustomerArticleAdapter(this, orders);
        binding.customerArticlesRv.setLayoutManager(new LinearLayoutManager(this));
        binding.customerArticlesRv.setAdapter(adapter);

        adapter.setInterface(o -> {
            double payment = 0;
            double debt = 0;
            for (Order or: orders) {
                payment += UtilMethods.calculatePayment(or.getArticles());
                debt += UtilMethods.calculateDebt(or.getArticles());
            }

            if(order.getTotal() == payment && debt == 0){
                order.setStatus(Order.STATUS_PAYED);
                OrderController.updateOrder(db, order);
            }
        });
    }
}
