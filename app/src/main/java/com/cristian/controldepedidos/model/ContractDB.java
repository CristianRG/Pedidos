package com.cristian.controldepedidos.model;

public class ContractDB {

    // Table Names
    public static final String CUSTOMER_TABLE_NAME = "customer";
    public static final String PRODUCT_TABLE_NAME = "product";
    public static final String ARTICLE_TABLE_NAME = "article";
    public static final String ORDER_TABLE_NAME = "orders";
    public static final String ORDER_ARTICLE_TABLE_NAME = "order_article";

    // Column Names for Customer Table
    public static final String CUSTOMER_COLUMN_ID = "id";
    public static final String CUSTOMER_COLUMN_NAME = "name";
    public static final String CUSTOMER_COLUMN_EMAIL = "email";
    public static final String CUSTOMER_COLUMN_PHONE = "phone";
    public static final String CUSTOMER_COLUMN_STATUS = "status";

    // Column Names for Product Table
    public static final String PRODUCT_COLUMN_ID = "id";
    public static final String PRODUCT_COLUMN_NAME = "name";
    public static final String PRODUCT_COLUMN_LINK = "link";
    public static final String PRODUCT_COLUMN_PRICE = "price";

    // Column Names for Article Table
    public static final String ARTICLE_COLUMN_ID = "id";
    public static final String ARTICLE_COLUMN_STATUS = "status";
    public static final String ARTICLE_COLUMN_PAYMENT = "payment";
    public static final String ARTICLE_COLUMN_DEBT = "debt";
    public static final String ARTICLE_COLUMN_TOTAL = "total";
    public static final String ARTICLE_COLUMN_CUSTOMER_ID = "customer_id";
    public static final String ARTICLE_COLUMN_PRODUCT_ID = "product_id";

    // Column Names for Orders Table
    public static final String ORDER_COLUMN_ID = "id";
    public static final String ORDER_COLUMN_TYPE = "type";
    public static final String ORDER_COLUMN_STATUS = "status";
    public static final String ORDER_COLUMN_TOTAL = "total";
    public static final String ORDER_COLUMN_DATE = "date";

    // Column Names for Order_Article Table
    public static final String ORDER_ARTICLE_COLUMN_ID = "id";
    public static final String ORDER_ARTICLE_COLUMN_ORDER_ID = "order_id";
    public static final String ORDER_ARTICLE_COLUMN_ARTICLE_ID = "article_id";
}
