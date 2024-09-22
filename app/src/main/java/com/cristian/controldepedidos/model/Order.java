package com.cristian.controldepedidos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    public int id;
    public String type;
    public ArrayList<Article> articles;
    public String status;
    public double total;
    public String date;
    public static final String STATUS_CANCELLED = "Cancelado";
    public static final String STATUS_REGISTERED = "Registrado";
    public static final String STATUS_ORDERED = "Pedido";
    public static final String STATUS_DELIVERED = "Entregado";
    public static final String STATUS_PAYED = "Pagado";
    public static final String STATUS_NOTHING = "Sin estado";

    // Constructor
    public Order(int id, String type, ArrayList<Article> articles, String status, double total, String date) {
        this.id = id;
        this.type = type;
        this.articles = articles;
        this.status = status;
        this.total = total;
        this.date = date;
    }

    public Order() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }
    public String getTotalToString() {
        return "" + total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public static String[] getListStatus(){
        return new String[]{STATUS_CANCELLED, STATUS_REGISTERED, STATUS_ORDERED, STATUS_DELIVERED, STATUS_PAYED, STATUS_NOTHING};
    }
}

