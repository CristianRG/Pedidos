package com.cristian.controldepedidos.controller.transactions;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cristian.controldepedidos.controller.database.ArticleController;
import com.cristian.controldepedidos.controller.database.CustomerController;
import com.cristian.controldepedidos.controller.database.HistoryController;
import com.cristian.controldepedidos.controller.database.OrderArticleController;
import com.cristian.controldepedidos.controller.database.ProductController;
import com.cristian.controldepedidos.model.Article;
import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.model.Order;
import com.cristian.controldepedidos.model.Transaction;
import com.cristian.controldepedidos.utils.UtilMethods;

import java.util.ArrayList;
import java.util.Objects;

public class ArticleTransaction {
    public static boolean addArticleTransaction(SQLiteDatabase db, Order order){
        boolean response = false;
        try {
            for (Article article: order.getArticles()) {
                long productId = ProductController.addProduct(db, article.getProduct());
                if(productId == -1)throw new SQLException("Not product inserted");
                article.getProduct().setId((int) productId);

                long articleId = ArticleController.addArticle(db, article);
                if(articleId == -1)throw new SQLException("Not article inserted");

                long orderArticleId = OrderArticleController.addOrderArticle(db, order.getId(), articleId);
                if(orderArticleId == -1)throw new SQLException("Not relationship established");
                String details = "Articulo agregado: " + article.getProduct().getName() + "\nCliente: " + article.getCustomer().getName();
                HistoryController.addHistory(db, new Transaction(0, Transaction.TYPE_ADDED, details, UtilMethods.getCurrentDate()));
            }
            response = true;
        }catch (SQLException e){
            Log.d("Error", Objects.requireNonNull(e.getMessage()));
        }
        return response;
    }
    public static boolean updateArticleTransaction(SQLiteDatabase db, Order order){
        boolean response = false;
        try {
            for (Article article: order.getArticles()) {
                boolean count = ArticleController.updateArticle(db, article);
                if (!count)throw new SQLException("Not updated article");
                boolean productCount = ProductController.updateProduct(db, article.getProduct());
                if(!productCount)throw new SQLException("Not updated product");
                String details = "Articulo editado: " + article.getProduct().getName() + "\nCliente: " + article.getCustomer().getName();
                HistoryController.addHistory(db, new Transaction(0, Transaction.TYPE_EDITED, details, UtilMethods.getCurrentDate()));
            }
            response = true;
        }
        catch (SQLException e){
            //throw new SQLException(e.getMessage());
        }
        return response;
    }
    public static boolean deleteArticleTransaction(SQLiteDatabase db, Order order){
        boolean response = false;
        try {
            for (Article article: order.getArticles()) {
                boolean count = ArticleController.deleteArticle(db, article);
                if (!count)throw new SQLException("Not deleted");
                boolean productCount = ProductController.deleteProduct(db, article.getProduct());
                if(!productCount)throw new SQLException("Not deleted");
                String details = "Articulo eliminado: " + article.getProduct().getName() + "\nCliente: " + article.getCustomer().getName();
                HistoryController.addHistory(db, new Transaction(0, Transaction.TYPE_REMOVED, details, UtilMethods.getCurrentDate()));
                //boolean orderArticleCount = OrderArticleController.deleteOrderArticle(db, order.getId(), article.getId());
                //if(!orderArticleCount)throw new SQLException("Not orderArticle deleted");
            }
            response = true;
        }
        catch (SQLException e){
            Log.d("debug", Objects.requireNonNull(e.getMessage()));
        }
        return response;
    }
}
