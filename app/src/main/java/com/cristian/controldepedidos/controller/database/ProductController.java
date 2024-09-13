package com.cristian.controldepedidos.controller.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.model.Product;

public class ProductController {

    public static Product getProduct(DatabaseHelper dbh){
        SQLiteDatabase db = dbh.getWritableDatabase();

        return null;
    }

    public static Product getProduct(SQLiteDatabase db, long idProduct){
        Product product;
        try {
            Cursor cursor = db.rawQuery(String.format("SELECT * FROM product WHERE id = %s", idProduct), null);

            if(cursor.moveToFirst()){
                 product = new Product(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        Double.parseDouble(String.valueOf(cursor.getString(3))));
                 return product;
            }
            return null;
        }catch (SQLException e){
            return null;
        }
    }



    public static long addProduct(@NonNull DatabaseHelper dbh, Product product){
        SQLiteDatabase db = dbh.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("name", product.getName());
            values.put("link", product.getLink());
            values.put("price", product.getPrice());
            long idProduct = db.insert("product", null, values);
            db.close();
            return idProduct;
        } catch (SQLException e){
            db.close();
            return 0;
        }
    }

    public static long addProductWithTransaction(SQLiteDatabase db, Product product){
        try {
            ContentValues values = new ContentValues();
            values.put("name", product.getName());
            values.put("link", product.getLink());
            values.put("price", product.getPrice());
            return db.insert("product", null, values);
        } catch (SQLException e){
            return 0;
        }
    }
}
