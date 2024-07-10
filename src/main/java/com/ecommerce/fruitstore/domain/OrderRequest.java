package com.ecommerce.fruitstore.domain;

public class OrderRequest {

    int apples;
    int oranges;

    public OrderRequest(int apples, int oranges) {
        this.apples = apples;
        this.oranges = oranges;
    }

    public int getApples() {
        return apples;
    }

    public void setApples(int apples) {
        this.apples = apples;
    }

    public int getOranges() {
        return oranges;
    }

    public void setOranges(int oranges) {
        this.oranges = oranges;
    }
}
