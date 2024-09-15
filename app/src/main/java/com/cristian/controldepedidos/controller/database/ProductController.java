package com.cristian.controldepedidos.controller.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.cristian.controldepedidos.model.ContractDB;
import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.model.Product;

public class ProductController {

    public static Product getProduct(SQLiteDatabase db, Product product){
        String selection = ContractDB.PRODUCT_COLUMN_ID + "= ?";
        String[] selectionArgs = {String.valueOf(product.getId())};
        Cursor cursor = db.query(ContractDB.PRODUCT_TABLE_NAME, null, selection, selectionArgs, null, null, null);

        if(cursor.moveToFirst()){
            product = new Product(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    Double.parseDouble(String.valueOf(cursor.getString(3))));
            return product;
        }
        return null;
    }

    public static long addProduct(SQLiteDatabase db, Product product){
        ContentValues values = new ContentValues();
        values.put(ContractDB.PRODUCT_COLUMN_LINK, product.getLink());
        values.put(ContractDB.PRODUCT_COLUMN_NAME, product.getName());
        values.put(ContractDB.PRODUCT_COLUMN_PRICE, product.getPrice());
        return db.insert(ContractDB.PRODUCT_TABLE_NAME, null, values);
    }

    public static boolean deleteProduct(SQLiteDatabase db, Product product){
        String selection = ContractDB.PRODUCT_COLUMN_ID + "= ?";
        String[] selectionArgs = {String.valueOf(product.getId())};

        int count = db.delete(ContractDB.PRODUCT_TABLE_NAME, selection, selectionArgs);
        return count == 1;
    }

    public static boolean updateProduct(SQLiteDatabase db, Product product){
        // values from product
        ContentValues values = new ContentValues();
        values.put(ContractDB.PRODUCT_COLUMN_LINK, product.getLink());
        values.put(ContractDB.PRODUCT_COLUMN_NAME, product.getName());
        values.put(ContractDB.PRODUCT_COLUMN_PRICE, product.getPrice());
        String selection = ContractDB.PRODUCT_COLUMN_ID + "= ?";
        String[] selectionArgs = {String.valueOf(product.getId())};

        int count = db.update(ContractDB.PRODUCT_TABLE_NAME, values, selection, selectionArgs);
        return count == 1;
    }


}
