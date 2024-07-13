package com.ecommerce.fruitstore.promos;

public class PromotionDetails {

    private final int updatedQuantity;
    private final double uppdatedUnitPrice;

    private final int promoId;

    public PromotionDetails(double uppdatedUnitPrice, int updatedQuantity, int promoId) {
        this.uppdatedUnitPrice = uppdatedUnitPrice;
        this.updatedQuantity = updatedQuantity;
        this.promoId = promoId;
    }

    public int getUpdatedQuantity() {
        return updatedQuantity;
    }


    public double getUppdatedUnitPrice() {
        return uppdatedUnitPrice;
    }

    public int getPromoId() {
        return promoId;
    }
}
