package com.cristian.controldepedidos.model;

public class Article {
    public int id;
    public Product product;
    public Customer customer;
    public int status;
    public double payment;
    public double debt;
    public double total;

    // Constructor
    public Article(int id, Product product, Customer customer, int status, double payment, double debt, double total) {
        this.id = id;
        this.product = product;
        this.customer = customer;
        this.status = status;
        this.payment = payment;
        this.debt = debt;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

