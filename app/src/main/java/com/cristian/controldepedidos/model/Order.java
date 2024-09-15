package com.cristian.controldepedidos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Order implements Serializable {
    public int id;
    public String type;
    public ArrayList<Article> articles;
    public String status;
    public double total;
    public String date;

    // Constructor
    public Order(int id, String type, ArrayList<Article> articles, String status, double total, String date) {
        this.id = id;
        this.type = type;
        this.articles = articles;
        this.status = status;
        this.total = total;
        this.date = date;
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
}

