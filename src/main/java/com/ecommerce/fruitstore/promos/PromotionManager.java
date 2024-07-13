package com.ecommerce.fruitstore.promos;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public  interface PromotionManager {
    PromotionDetails applyPromotion(double price, int quantity, List<Long> poromotionCodes);
}
