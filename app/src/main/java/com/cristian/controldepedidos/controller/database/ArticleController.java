package com.cristian.controldepedidos.controller.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cristian.controldepedidos.model.Article;
import com.cristian.controldepedidos.model.ContractDB;
import com.cristian.controldepedidos.model.Customer;
import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.model.Product;

public class ArticleController {

    public static Article getArticle(SQLiteDatabase db, Article article){
        String selection = ContractDB.ARTICLE_COLUMN_ID + "= ?";
        String[] selectionArgs = {String.valueOf(article.getId())};

        Cursor cursor = db.query(ContractDB.ARTICLE_TABLE_NAME, null, selection, selectionArgs, null, null, null);

        if(cursor.moveToFirst()) {
            Product product = new Product();
            product.setId(cursor.getInt(6));
            Customer customer = new Customer();
            customer.setId(cursor.getInt(5));
            article = new Article(cursor.getInt(0), product, customer, cursor.getInt(1),
                    Double.parseDouble(cursor.getString(2)), Double.parseDouble(cursor.getString(3)),
                    Double.parseDouble(cursor.getString(4)));
            return article;
        }
        return null;
    }

    public static long addArticle(SQLiteDatabase db, Article article){
        ContentValues values = new ContentValues();
        values.put(ContractDB.ARTICLE_COLUMN_CUSTOMER_ID, article.getCustomer().getId());
        values.put(ContractDB.ARTICLE_COLUMN_PRODUCT_ID, article.getProduct().getId());
        values.put(ContractDB.ARTICLE_COLUMN_DEBT, article.getDebt());
        values.put(ContractDB.ARTICLE_COLUMN_PAYMENT, article.getPayment());
        values.put(ContractDB.ARTICLE_COLUMN_DEBT, article.getDebt());
        values.put(ContractDB.ARTICLE_COLUMN_TOTAL, article.getTotal());
        return db.insert(ContractDB.ARTICLE_TABLE_NAME, null, values);
    }

    public static boolean deleteArticle(SQLiteDatabase db, Article article){
        String selection = ContractDB.ARTICLE_COLUMN_ID + "= ?";
        String[] selectionArgs = {String.valueOf(article.getId())};

        int deletedRows = db.delete(ContractDB.ARTICLE_TABLE_NAME, selection, selectionArgs);
        return deletedRows == 1;
    }

    public static boolean updateArticle(SQLiteDatabase db, Article article){
        // new values from article
        ContentValues values = new ContentValues();
        values.put(ContractDB.ARTICLE_COLUMN_CUSTOMER_ID, article.getCustomer().getId());
        //values.put(ContractDB.ARTICLE_COLUMN_PRODUCT_ID, article.getProduct().getId());
        values.put(ContractDB.ARTICLE_COLUMN_DEBT, article.getDebt());
        values.put(ContractDB.ARTICLE_COLUMN_PAYMENT, article.getPayment());
        values.put(ContractDB.ARTICLE_COLUMN_TOTAL, article.getTotal());
        String selection = ContractDB.ARTICLE_COLUMN_ID + "= ?";
        String[] selectionArgs = {String.valueOf(article.getId())};

        int count = db.update(ContractDB.ARTICLE_TABLE_NAME, values, selection, selectionArgs);
        return count == 1;
    }
}
