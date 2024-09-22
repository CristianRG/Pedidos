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
    public static final int STATUS_CANCELLED = 0;
    public static final int STATUS_REGISTERED = 1;
    public static final int STATUS_ORDERED = 2;
    public static final int STATUS_DELIVERED = 3;
    public static final int STATUS_PAYED = 4;
    public static final int STATUS_NOTHING = 5;

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
            case STATUS_CANCELLED: {
                return "Cancelado";
            }
            case STATUS_REGISTERED: {
                return "Registrado";
            }
            case STATUS_ORDERED: {
                return "Pedido";
            }
            case STATUS_DELIVERED: {
                return "Entregado";
            }
            case STATUS_PAYED: {
                return "Pagado";
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
        return "" + payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public double getDebt() {
        return debt;
    }
    public String getDebtString(){
        return "" + debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    public double getTotal() {
        return total;
    }
    public String getTotalString() {
        return "" + total;
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

    public static String[] getStatusList(){
        return new String[]{"Cancelado", "Registrado", "Pedido", "Entregado", "Pagado"};
    }
}

