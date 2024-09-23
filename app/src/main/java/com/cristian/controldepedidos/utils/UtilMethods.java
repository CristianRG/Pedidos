package com.cristian.controldepedidos.utils;

import android.util.Log;

import com.cristian.controldepedidos.model.Article;
import com.cristian.controldepedidos.model.Customer;
import com.cristian.controldepedidos.model.Order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UtilMethods {
    public static double calculateTotal(ArrayList<Article> articles){
        double total = 0;
        for (Article article:
                articles) {
            total += article.getTotal();
        }
        return total;
    }
    public static double calculatePayment(ArrayList<Article> articles){
        double payment = 0;
        for (Article article: articles) {
            payment += article.getPayment();
        }
        return payment;
    }
    public static double calculateDebt(ArrayList<Article> articles){
        double debt = 0;
        for (Article article: articles) {
            debt += article.getDebt();
        }
        return debt;
    }
    public static ArrayList<Order> sortOrder(Order order) {
        // Lista para almacenar órdenes separadas por cliente
        ArrayList<Order> orders = new ArrayList<>();

        // Crear un conjunto de clientes únicos de los artículos
        HashSet<Customer> customers = new HashSet<>();
        for (Article article : order.getArticles()) {
            customers.add(article.getCustomer());
        }

        // Para cada cliente, crear una nueva orden con sus artículos
        for (Customer customer : customers) {
            // Crear una nueva orden para el cliente
            Order customerArticles = new Order();
            customerArticles.setId(order.getId());
            customerArticles.setType(order.getType());
            customerArticles.setStatus(order.getStatus());
            customerArticles.setDate(order.getDate());
            customerArticles.setArticles(new ArrayList<>());

            // Añadir los artículos del cliente actual a su orden
            for (Article article : order.getArticles()) {
                if (article.getCustomer().getId() == customer.getId()) {
                    customerArticles.getArticles().add(article);
                }
            }

            // Calcular y establecer el total de la orden del cliente
            customerArticles.setTotal(calculateTotal(customerArticles.getArticles()));
            orders.add(customerArticles);
        }

        return orders;
    }

    public static StringBuilder shareInfo(Order order, Customer customer){
        StringBuilder message = new StringBuilder("¡Hola, " + customer.getName() + "! Te envio tu cuenta del pedido de: " + order.getType() +
                "\n\nProductos: \n");

        for (Article article: order.getArticles()) {
            String row = "*" + article.getProduct().getName() + "* - *$" + article.getProduct().getPrice() + "*\n";
            message.append(row);
        }
        message.append("\n*Total $").append(order.getTotal()).append("*");

        return message;
    }

    public static String getCurrentDate(){
        DateTimeFormatter format = null;
        String date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        }
        LocalDate localDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            localDate = LocalDate.now();
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = localDate.format(format);
        }
        return date;
    }
}
