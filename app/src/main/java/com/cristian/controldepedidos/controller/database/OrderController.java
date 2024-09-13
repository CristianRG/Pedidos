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
import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.model.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class OrderController {

    public static ArrayList<Order> getOrders(@NonNull DatabaseHelper dbh){
        ArrayList<Order> orders = new ArrayList<>();
        SQLiteDatabase db = dbh.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM orders", null);


            if(cursor.moveToFirst()){
                do {
                    Order order = new Order(cursor.getInt(0), cursor.getString(1), new ArrayList<Article>(), cursor.getString(2),
                            Double.parseDouble(cursor.getString(3)), cursor.getString(4));

                    OrderArticleController.getOrderArticle(db, order);

                    orders.add(order);


                }while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
            return orders;
        }catch (Exception e){
            return orders;
        }
    }

    public static boolean addOrder(@NonNull DatabaseHelper dbh, @NonNull Order order, Context context){
        SQLiteDatabase db = dbh.getWritableDatabase();
        try{
            db.beginTransaction();

            ContentValues values = new ContentValues();
            values.put("type", order.getType());
            values.put("status", order.getStatus());
            values.put("total", order.getTotal());
            values.put("date", order.getDate());
            long idOrder = db.insert("orders", null, values);

            for (Article article:
                 order.getArticles()) {
                long articleId = ArticleController.addArticleWithTransaction(db, article);
                long orderArticleId = OrderArticleController.addOrderArticle(db, idOrder, articleId);
                if(orderArticleId==0) {
                    db.endTransaction();
                    db.close();
                    return false;
                };
            }

            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return true;
        }catch (SQLException e){
            Log.d("Error", e.getMessage());
            db.close();
            Toast.makeText(context, "Error al agregar", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
