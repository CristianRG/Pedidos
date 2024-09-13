package com.cristian.controldepedidos.controller.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.cristian.controldepedidos.model.Article;
import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.model.Order;

public class OrderArticleController {

    public static void getOrderArticle(SQLiteDatabase db, Order order) throws Exception {
        try {
            String query = "SELECT * FROM order_article WHERE order_id = " + String.valueOf(order.getId());
            //String query = "SELECT * FROM order_article";
            Cursor cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst()){
                do {
                    Article article = ArticleController.getArticle(db, cursor.getInt(2));
                    order.getArticles().add(article);
                }while (cursor.moveToNext());
            }
        }catch (SQLException e){
            throw new Exception("Error to get orders");
        }
    }

    public static long addOrderArticle(SQLiteDatabase db, long idOrder, long idArticle){
        try {
            ContentValues values = new ContentValues();
            values.put("order_id", idOrder);
            values.put("article_id", idArticle);
            return db.insert("order_article", null, values);
        }catch (SQLException e){
            return 0;
        }
    }
}
