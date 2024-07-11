package com.ecommerce.fruitstore.promos;

import com.ecommerce.fruitstore.NumberFormatter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class PromotionManagerImpl implements PromotionManager {

    /**
     * apply promotions in a simple manner, ideally in ral projects we should store all promotions in a database and have business logic rules that
     * can be dynamically applied, adn also have a way to apply multiple promotions on a single item
     */
    public PromotionApplied applyPromotion(double price, int quantity, List<Long> promotionCodes) {
        // considering only one promotion for any fruit for now, we can make this logic to apply multiple promotions later
        int promotionId = promotionCodes!=null && promotionCodes.size() > 0? promotionCodes.get(0).intValue(): 0;
        if (promotionId == 1) {
            return applyPromotion1(price, quantity, promotionId);
        } else if (promotionId == 2) {
            return applyPromotion2(price, quantity, promotionId);
        } else {
            return new PromotionApplied(price, quantity, 0);
        }
    }

    /**
     * Apply promotion one get one free on Apples, we keep it simple here and have UI send correct promotion id only if valid for fruit
     */
    private PromotionApplied applyPromotion1(double price, int quantity, int promotionId) {
        int updatedQty = quantity * 2;
        double updatedPrice = NumberFormatter.formatBigDecimal(BigDecimal.valueOf(price).multiply(BigDecimal.valueOf(0.5)).doubleValue()).doubleValue();
        return new PromotionApplied(updatedPrice, updatedQty, promotionId);
    }

    /** Apply promotion 3 for the price of 2 on Oranges */
    private PromotionApplied applyPromotion2(double price, int quantity, int promotionId) {

        int multpliesOfTwo = quantity / 2;
        int remaining = quantity % 2;

        BigDecimal totalAtPromoPrice =  BigDecimal.valueOf(price * 2 / 3).multiply(BigDecimal.valueOf(multpliesOfTwo * 3 ));
        BigDecimal totalAtRegularPrice = BigDecimal.valueOf(remaining * price);

        int updatedQuantity = multpliesOfTwo * 3 + remaining;
        double updatedUnitprice = totalAtPromoPrice.add(totalAtRegularPrice).divide(BigDecimal.valueOf(updatedQuantity)).doubleValue();

        return new PromotionApplied(updatedUnitprice, updatedQuantity, promotionId);
    }
}
