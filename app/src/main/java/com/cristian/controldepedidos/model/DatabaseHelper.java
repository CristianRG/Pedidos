package com.cristian.controldepedidos.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "orders.db";
    private static final int DATABASE_VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tablas aquí
        String CUSTOMER_TABLE = "CREATE TABLE customer (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(100) NOT NULL, email VARCHAR(200), phone VARCHAR(15), status INTEGER)";

        String PRODUCT_TABLE = "CREATE TABLE product (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(100) NOT NULL, link TEXT, price TEXT)";

        String ARTICLE_TABLE = "CREATE TABLE article (id INTEGER PRIMARY KEY AUTOINCREMENT, status INTEGER,payment TEXT, debt TEXT, total TEXT, " +
                "customer_id INTEGER, product_id INTEGER, FOREIGN KEY(customer_id) REFERENCES customer(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                " FOREIGN KEY(product_id) REFERENCES product(id) ON DELETE CASCADE ON UPDATE CASCADE)";

        String ORDER_TABLE = "CREATE TABLE orders (id INTEGER PRIMARY KEY AUTOINCREMENT, type VARCHAR(50), status VARCHAR(50), total TEXT, date TEXT NOT NULL)";

        String ORDER_ARTICLE = "CREATE TABLE order_article (id INTEGER PRIMARY KEY AUTOINCREMENT, order_id INTEGER, article_id INTEGER, " +
                "FOREIGN KEY(order_id) REFERENCES orders(id)  ON DELETE CASCADE ON UPDATE CASCADE, " +
                "FOREIGN KEY(article_id) REFERENCES article(id)  ON DELETE CASCADE ON UPDATE CASCADE)";
        String TRANSACTION_TABLE = "CREATE TABLE history (id INTEGER PRIMARY KEY AUTOINCREMENT, type INTEGER NOT NULL, details TEXT, date TEXT NOT NULL)";
        db.execSQL(CUSTOMER_TABLE);
        db.execSQL(PRODUCT_TABLE);
        db.execSQL(ARTICLE_TABLE);
        db.execSQL(ORDER_TABLE);
        db.execSQL(ORDER_ARTICLE);
        db.execSQL(TRANSACTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Actualizar la base de datos si es necesario
        clearTestData(db);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    public void clearTestData(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS customer");
        db.execSQL("DROP TABLE IF EXISTS product");
        db.execSQL("DROP TABLE IF EXISTS article");
        db.execSQL("DROP TABLE IF EXISTS orders");
        db.execSQL("DROP TABLE IF EXISTS order_article");
        db.execSQL("DROP TABLE IF EXISTS history");

        // Opcional: Restablece el contador de AUTOINCREMENT
    }

}

