package com.cristian.controldepedidos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cristian.controldepedidos.controller.OrderAdapter;
import com.cristian.controldepedidos.model.Article;
import com.cristian.controldepedidos.model.Customer;
import com.cristian.controldepedidos.model.Order;
import com.cristian.controldepedidos.model.Product;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private ArrayList<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa el RecyclerView
        recyclerView = findViewById(R.id.orders_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa el ArrayList de Order
        orderList = new ArrayList<>();
        populateOrders();  // Llenar la lista con datos

        // Configura el Adapter
        orderAdapter = new OrderAdapter(this, orderList);
        recyclerView.setAdapter(orderAdapter);
    }

    // Método para agregar órdenes ficticias a la lista
    private void populateOrders() {
        // Artículos para la primera orden (Online)
        ArrayList<Article> articles1 = new ArrayList<>();
        articles1.add(new Article(1, new Product(1, "Laptop", "https://www.mercadolibre.com.mx/laptop-asus-vivobook-go-15-e1504-intel-ci3-8gb-512gb-ssd-color-plata/p/MLM34198177#wid%3DMLM2033664095%26sid%3Dsearch%26searchVariation%3DMLM34198177%26position%3D14%26search_layout%3Dstack%26type%3Dproduct%26tracking_id%3D51dbe73a-015d-46cc-80f8-4ba38b1fe77e", 1000.00),
                new Customer(1, "Pedro", "pedro@gmail.com", "4779082735", 1),
                "Paid", 800.00, 200.00, 1000.00));
        articles1.add(new Article(2, new Product(2, "Mouse", "link2", 50.00),
                new Customer(1, "Pedro", "pedro@gmail.com", "4779082735", 1),
                "Paid", 50.00, 0.00, 50.00));

        // Artículos para la segunda orden (In-Store)
        ArrayList<Article> articles2 = new ArrayList<>();
        articles2.add(new Article(3, new Product(3, "Keyboard", "link3", 80.00),
                new Customer(1, "Pedro", "pedro@gmail.com", "4779082735", 1),
                "Paid", 80.00, 0.00, 80.00));
        articles2.add(new Article(4, new Product(4, "Monitor", "link4", 200.00),
                new Customer(1, "Pedro", "pedro@gmail.com", "4779082735", 1),
                "Paid", 100.00, 100.00, 200.00));

        // Artículos para la tercera orden (Phone)
        ArrayList<Article> articles3 = new ArrayList<>();
        articles3.add(new Article(5, new Product(5, "Phone Case", "link5", 20.00),
                new Customer(1, "Pedro", "pedro@gmail.com", "4779082735", 1),
                "Cancelled", 0.00, 0.00, 20.00));

        // Artículos para la cuarta orden (Online)
        ArrayList<Article> articles4 = new ArrayList<>();
        articles4.add(new Article(6, new Product(6, "Tablet", "link6", 500.00),
                new Customer(1, "Pedro", "pedro@gmail.com", "4779082735", 1),
                "Paid", 500.00, 0.00, 500.00));
        articles4.add(new Article(7, new Product(7, "Charger", "link7", 25.00),
                new Customer(1, "Pedro", "pedro@gmail.com", "4779082735", 1),
                "Paid", 25.00, 0.00, 25.00));

        // Agregar las órdenes con sus artículos
        orderList.add(new Order(1, "Online", articles1, "Pending", 200.00));
        orderList.add(new Order(2, "In-Store", articles2, "Completed", 150.50));
        orderList.add(new Order(3, "Phone", articles3, "Cancelled", 80.25));
        orderList.add(new Order(4, "Online", articles4, "Shipped", 400.75));
    }

}
