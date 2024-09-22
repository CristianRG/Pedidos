package com.cristian.controldepedidos.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class Customer implements Serializable {
    public int id;
    public String name;
    public String email;
    public String phone;
    public int status;

    // Constructor
    public Customer(int id, String name, String email, String phone, int status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }

    public Customer() {

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
    public String getNameString(){
        return "Cliente: " + name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id;  // Compara por el ID del cliente
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);  // Usa el ID para calcular el hash
    }
}

