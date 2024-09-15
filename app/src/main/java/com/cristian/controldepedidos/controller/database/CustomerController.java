package com.cristian.controldepedidos.controller.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.cristian.controldepedidos.model.Article;
import com.cristian.controldepedidos.model.ContractDB;
import com.cristian.controldepedidos.model.Customer;
import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.model.Order;

import java.util.ArrayList;

public class CustomerController {
    public static Customer getCustomer(SQLiteDatabase db, Customer customer){
        String selection = ContractDB.CUSTOMER_COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(customer.getId())};
        Cursor cursor = db.query(ContractDB.CUSTOMER_TABLE_NAME, null, selection, selectionArgs, null, null, null);

        if(cursor.moveToFirst()){
            customer = new Customer(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4));
            return customer;
        }
        return null;
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
    public static long addCustomer(SQLiteDatabase db, Customer customer){
        ContentValues values = new ContentValues();
        values.put(ContractDB.CUSTOMER_COLUMN_EMAIL, customer.getEmail());
        values.put(ContractDB.CUSTOMER_COLUMN_NAME, customer.getName());
        values.put(ContractDB.CUSTOMER_COLUMN_PHONE, customer.getPhone());
        values.put(ContractDB.CUSTOMER_COLUMN_STATUS, customer.getStatus());
        return db.insert(ContractDB.CUSTOMER_TABLE_NAME, null, values);
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

    public static boolean deleteCustomer(SQLiteDatabase db, Customer customer){
        String selection = ContractDB.CUSTOMER_COLUMN_ID + "= ?";
        String[] selectionArgs = {String.valueOf(customer.getId())};

        int count = db.delete(ContractDB.PRODUCT_TABLE_NAME, selection, selectionArgs);
        return count == 1;
    }

    public static boolean updateCustomer(SQLiteDatabase db, Customer customer){
        // new vales from customer
        ContentValues values = new ContentValues();
        values.put(ContractDB.CUSTOMER_COLUMN_EMAIL, customer.getEmail());
        values.put(ContractDB.CUSTOMER_COLUMN_NAME, customer.getName());
        values.put(ContractDB.CUSTOMER_COLUMN_PHONE, customer.getPhone());
        values.put(ContractDB.CUSTOMER_COLUMN_STATUS, customer.getStatus());
        String selection = ContractDB.CUSTOMER_COLUMN_ID + "= ?";
        String[] selectionArgs = {String.valueOf(customer.getId())};

        int count = db.update(ContractDB.CUSTOMER_TABLE_NAME, values, selection, selectionArgs);
        return count == 1;
    }
}
