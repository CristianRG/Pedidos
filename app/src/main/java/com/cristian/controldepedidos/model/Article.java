package com.cristian.controldepedidos.model;

import java.io.Serializable;

public class Article implements Serializable {
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

    public Article() {

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
    public String getStatusString(){
        switch (status){
            case 0: {
                return "Cancelado";
            }
            case 1: {
                return "Registrado";
            }
            case 2: {
                return "Pedido";
            }
            case 3: {
                return "Entregado";
            }
            default: {
                return "Sin estado";
            }
        }
    }

    public double getPayment() {
        return payment;
    }
    public String getPaymentString(){
        return "Pagado: $" + payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public double getDebt() {
        return debt;
    }
    public String getDebtString(){
        return "Faltante: $" + debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    public double getTotal() {
        return total;
    }
    public String getTotalString() {
        return "Total: $" + total;
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

