package com.ecommerce.fruitstore.promos;

public class PromotionApplied {

    private final int updatedQuantity;
    private final double unitPrice;

    private final int promoId;

    public PromotionApplied(double unitPrice, int updatedQuantity, int promoId) {
        this.unitPrice = unitPrice;
        this.updatedQuantity = updatedQuantity;
        this.promoId = promoId;
    }

    public int getUpdatedQuantity() {
        return updatedQuantity;
    }



    public double getUnitPrice() {
        return unitPrice;
    }



    public int getPromoId() {
        return promoId;
    }


}
