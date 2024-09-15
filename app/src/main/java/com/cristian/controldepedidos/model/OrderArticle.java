package com.cristian.controldepedidos.model;

public class OrderArticle {
    private int id;
    private int orderId;
    private int articleId;

    public OrderArticle(int id, int orderId, int articleId) {
        this.id = id;
        this.orderId = orderId;
        this.articleId = articleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
}
