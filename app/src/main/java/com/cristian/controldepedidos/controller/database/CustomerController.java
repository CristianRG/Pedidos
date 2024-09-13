package com.cristian.controldepedidos.controller.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.cristian.controldepedidos.model.Article;
import com.cristian.controldepedidos.model.Customer;
import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.model.Order;

import java.util.ArrayList;

public class CustomerController {

    public static Customer getCustomer(SQLiteDatabase db, long idCustomer){
        try {
            Cursor cursor = db.rawQuery(String.format("SELECT * FROM customer WHERE id = %s", idCustomer), null);
            if(cursor.moveToFirst()){
                Customer customer = new Customer(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4));
                return customer;
            }
            return null;
        }catch (SQLException e){
            return null;
        }
    }

    public static boolean addCustomer(DatabaseHelper dbh, Customer customer){
        try {
            SQLiteDatabase db = dbh.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", customer.getName());
            values.put("email", customer.getEmail());
            values.put("phone", customer.getPhone());
            db.insert("customer", null, values);
            db.close();
            return true;
        } catch (RuntimeException e){
            return false;
        }
    }

    public static ArrayList<Customer> getCustomers(DatabaseHelper dbh) {
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = dbh.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM customer", null);

        if(cursor.moveToFirst()){
            do {
                customers.add(new Customer(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return customers;
    }
}
