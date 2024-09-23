package com.cristian.controldepedidos.model;

public class Transaction {
    private int id;
    private int type;
    private String details;
    private String date;
    public static final int TYPE_ADDED = 0;
    public static final int TYPE_EDITED = 1;
    public static final int TYPE_REMOVED = 2;


    public Transaction(int id, int type, String details, String date) {
        this.id = id;
        this.type = type;
        this.details = details;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTypeToString(){
        switch (type){
            case TYPE_ADDED:
                return "Agregado";
            case TYPE_EDITED:
                return "Editado";
            case TYPE_REMOVED:
                return "Eliminado";
            default:
                return "Sin estado";
        }
    }
}
