package com.cristian.controldepedidos.controller.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cristian.controldepedidos.model.Article;
import com.cristian.controldepedidos.model.ContractDB;
import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.model.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class OrderController {

    public static ArrayList<Order> getOrders(@NonNull SQLiteDatabase db){
        ArrayList<Order> orders = new ArrayList<>();
        Cursor cursor = db.query(ContractDB.ORDER_TABLE_NAME, null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do {
                Order order = new Order(cursor.getInt(0), cursor.getString(1), new ArrayList<Article>(), cursor.getString(2),
                        Double.parseDouble(cursor.getString(3)), cursor.getString(4));
                orders.add(order);

            }while (cursor.moveToNext());
        }
        return orders;
    }

    public static long addOrder(SQLiteDatabase db, Order order){
        // values to insert
        ContentValues values = new ContentValues();
        values.put(ContractDB.ORDER_COLUMN_DATE, order.getDate());
        values.put(ContractDB.ORDER_COLUMN_STATUS, order.getStatus());
        values.put(ContractDB.ORDER_COLUMN_TOTAL, order.getTotal());
        values.put(ContractDB.ORDER_COLUMN_TYPE, order.getType());
        return db.insert(ContractDB.ORDER_TABLE_NAME, null, values);
    }

    public static boolean updateOrder(SQLiteDatabase db, Order order){
        ContentValues values = new ContentValues();
        values.put(ContractDB.ORDER_COLUMN_DATE, order.getDate());
        values.put(ContractDB.ORDER_COLUMN_STATUS, order.getStatus());
        values.put(ContractDB.ORDER_COLUMN_TOTAL, order.getTotal());
        values.put(ContractDB.ORDER_COLUMN_TYPE, order.getType());

        String selection = ContractDB.ORDER_COLUMN_ID + "= ?";
        String[] selectionArgs = {String.valueOf(order.getId())};
        int count = db.update(ContractDB.ORDER_TABLE_NAME, values, selection, selectionArgs);
        return count == 1;
    }

    public static boolean deleteOrder(SQLiteDatabase db, Order order){
        String selection = ContractDB.ORDER_COLUMN_ID + "= ?";
        String[] selectionArgs = {String.valueOf(order.getId())};
        int count = db.delete(ContractDB.ORDER_TABLE_NAME, selection, selectionArgs);
        return count == 1;
    }
}
