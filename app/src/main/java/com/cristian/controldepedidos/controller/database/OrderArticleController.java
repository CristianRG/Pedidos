package com.cristian.controldepedidos.controller.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cristian.controldepedidos.model.Article;
import com.cristian.controldepedidos.model.ContractDB;
import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.model.Order;
import com.cristian.controldepedidos.model.OrderArticle;

import java.util.ArrayList;

public class OrderArticleController {

    public static ArrayList<OrderArticle> getOrderArticle(SQLiteDatabase db, Order order) {
        String selection = ContractDB.ORDER_ARTICLE_COLUMN_ORDER_ID + "= ?";
        String[] selectionArgs = {String.valueOf(order.getId())};
        Cursor cursor = db.query(ContractDB.ORDER_ARTICLE_TABLE_NAME, null, selection, selectionArgs, null, null, null);

        ArrayList<OrderArticle> orderArticles = new ArrayList<>();

        if(cursor.moveToFirst()){
            do {
                orderArticles.add(new OrderArticle(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2)));
            }while (cursor.moveToNext());
        }
        return orderArticles;
    }

    public static long addOrderArticle(SQLiteDatabase db, long idOrder, long idArticle){
        ContentValues values = new ContentValues();
        values.put(ContractDB.ORDER_ARTICLE_COLUMN_ORDER_ID, idOrder);
        values.put(ContractDB.ORDER_ARTICLE_COLUMN_ARTICLE_ID, idArticle);
        return db.insert(ContractDB.ORDER_ARTICLE_TABLE_NAME, null, values);
    }

    public static boolean deleteOrderArticle(SQLiteDatabase db, long idOrder, long idArticle){
        String selection = ContractDB.ORDER_ARTICLE_COLUMN_ORDER_ID + "= ? and " + ContractDB.ORDER_ARTICLE_COLUMN_ARTICLE_ID + "= ?";
        String[] selectionArgs = {String.valueOf(idOrder), String.valueOf(idArticle)};
        int count = db.delete(ContractDB.ORDER_ARTICLE_TABLE_NAME, selection, selectionArgs);
        Log.d("debug", "order: " + idOrder + "article: " + idArticle + count);
        return count > 0;
    }
}
