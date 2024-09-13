package com.cristian.controldepedidos.controller.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cristian.controldepedidos.model.Article;
import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.model.Order;

import java.util.ArrayList;

public class ArticleOrder {
    public static ArrayList<Article> getArticles(DatabaseHelper dbh){
        ArrayList<Article> articles = new ArrayList<>();
        SQLiteDatabase db = dbh.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM article", null);

        if(cursor.moveToFirst()){
            do {

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return articles;
    }
}
