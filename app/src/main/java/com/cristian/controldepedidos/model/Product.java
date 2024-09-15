package com.cristian.controldepedidos.model;

import java.io.Serializable;

public class Product implements Serializable {
    public int id;
    public String name;
    public String link;
    public double price;

    // Constructor
    public Product(int id, String name, String link, double price) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.price = price;
    }

    public Product() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

