package com.ecommerce.fruitstore.domain;

import java.util.List;

public class OrderRequest {

    private int apples;
    private int oranges;

    private List<Long> applePromotionCodes;

    private List<Long> orangePromotionCodes;

    public OrderRequest() {

    }

    public OrderRequest(int apples, int oranges, List<Long> applePromotionCodes, List<Long> orangePromotionCodes) {
        this.apples = apples;
        this.oranges = oranges;
        this.applePromotionCodes = applePromotionCodes;
        this.orangePromotionCodes = orangePromotionCodes;
    }

    public int getApples() {
        return apples;
    }


    public int getOranges() {
        return oranges;
    }

    public List<Long> getApplePromotionCodes() {
        return applePromotionCodes;
    }

    public List<Long> getOrangePromotionCodes() {
        return orangePromotionCodes;
    }

}
