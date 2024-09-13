package com.cristian.controldepedidos.controller.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.cristian.controldepedidos.model.Article;
import com.cristian.controldepedidos.model.Customer;
import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.model.Product;

public class ArticleController {

    public static Article getArticle(SQLiteDatabase db, long idArticle){
        try {
            Cursor cursor = db.rawQuery(String.format("SELECT * FROM article WHERE id = %s", idArticle), null);

            if(cursor.moveToFirst()){
                Article article = new Article(cursor.getInt(0), null, null, cursor.getInt(1),
                        Double.parseDouble(cursor.getString(2)), Double.parseDouble(cursor.getString(3)),
                        Double.parseDouble(cursor.getString(4)));

                Product product = ProductController.getProduct(db, cursor.getInt(6));
                Customer customer = CustomerController.getCustomer(db, cursor.getInt(5));

                if(product==null && customer==null) return null;

                article.setProduct(product);
                article.setCustomer(customer);
                return article;
            }
            return null;
        }catch (SQLException e){
            return null;
        }
    }

    public static long addArticle(DatabaseHelper dbh, Article article){
        SQLiteDatabase db = dbh.getWritableDatabase();
        try {
            long idProduct = ProductController.addProduct(dbh, article.getProduct());
            if(idProduct==0) return 0;
            ContentValues values = new ContentValues();
            values.put("status", article.getStatus());
            values.put("payment", article.getPayment());
            values.put("deb", article.getDebt());
            values.put("total", article.getTotal());
            values.put("customer_id", article.getCustomer().getId());
            values.put("product_id", idProduct);
            long idArticle = db.insert("article", null, values);
            db.close();
            return idArticle;
        } catch (SQLException e){
            db.close();
            return 0;
        }
    }

    public static long addArticleWithTransaction(SQLiteDatabase db, Article article){
        try {
            long idProduct = ProductController.addProductWithTransaction(db, article.getProduct());
            if (idProduct == 0) {
                throw new SQLException("Error: No se pudo agregar el producto.");
            }

            ContentValues values = new ContentValues();
            values.put("status", article.getStatus());
            values.put("payment", article.getPayment());
            values.put("debt", article.getDebt());
            values.put("total", article.getTotal());
            values.put("customer_id", article.getCustomer().getId());
            values.put("product_id", idProduct);

            long result = db.insert("article", null, values);
            if (result == -1) {
                throw new SQLException("Error al insertar el artículo.");
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
