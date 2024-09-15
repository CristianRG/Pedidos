package com.cristian.controldepedidos.controller.transactions;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;
import com.cristian.controldepedidos.controller.database.ArticleController;
import com.cristian.controldepedidos.controller.database.CustomerController;
import com.cristian.controldepedidos.controller.database.OrderArticleController;
import com.cristian.controldepedidos.controller.database.OrderController;
import com.cristian.controldepedidos.controller.database.ProductController;
import com.cristian.controldepedidos.model.Article;
import com.cristian.controldepedidos.model.Customer;
import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.model.Order;
import com.cristian.controldepedidos.model.OrderArticle;
import com.cristian.controldepedidos.model.Product;
import java.util.ArrayList;

public class OrderTransaction {
    @NonNull
    public static ArrayList<Order> getOrdersTransaction(@NonNull DatabaseHelper dbh){
        SQLiteDatabase db = dbh.getWritableDatabase();
        ArrayList<Order> orders = OrderController.getOrders(db);

        for (Order order: orders) {
            ArrayList<OrderArticle> orderArticles = OrderArticleController.getOrderArticle(db, order);

            for (OrderArticle orderArticle: orderArticles) {
                Article article = new Article();
                article.setId(orderArticle.getArticleId());
                article = ArticleController.getArticle(db, article);
                order.getArticles().add(article);
            }

            for (Article article: order.getArticles()) {
                Product product = article.getProduct();
                product = ProductController.getProduct(db, product);
                article.setProduct(product);

                Customer customer = article.getCustomer();
                customer = CustomerController.getCustomer(db, customer);
                article.setCustomer(customer);
            }
        }

        return orders;
    }
    public static boolean addOrderTransaction(@NonNull DatabaseHelper dbh, Order order){
        SQLiteDatabase db = dbh.getWritableDatabase();
        boolean response = false;

        try {
            db.beginTransaction();
            long orderId = OrderController.addOrder(db, order);
            if(orderId == -1)throw new SQLException("Not order inserted");

            for (Article article: order.getArticles()) {
                Customer customer = CustomerController.getCustomer(db, article.getCustomer());
                article.setCustomer(customer);

                long productId = ProductController.addProduct(db, article.getProduct());
                if(productId == -1)throw new SQLException("Not product inserted");
                article.getProduct().setId((int) productId);

                long articleId = ArticleController.addArticle(db, article);
                if(articleId == -1)throw new SQLException("Not article inserted");

                long orderArticleId = OrderArticleController.addOrderArticle(db, orderId, articleId);
                if(orderArticleId == -1)throw new SQLException("Not relationship established");
            }

            db.setTransactionSuccessful();
            response = true;
            }
        catch (SQLException ignored){}
        finally {
            db.endTransaction();
            db.close();
        }
        return response;
    }
}
