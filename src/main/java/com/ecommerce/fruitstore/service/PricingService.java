package com.ecommerce.fruitstore.service;

import com.ecommerce.fruitstore.domain.FruitType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;

/**
 * Simple service to provide pricing information for fruits, in real projects this will be stored in Database and will have complex logic and will take care of several promotions and coupon codes
 */
@Component
public class PricingService {

    private static EnumMap<FruitType, Double> fruitPrices = new EnumMap<>(FruitType.class);

    public PricingService() {
        fruitPrices.put(FruitType.APPLE, 0.60);
        fruitPrices.put(FruitType.ORANGE, 0.25);
    }

    public double getPrice(FruitType fruitType) {
        return fruitPrices.get(fruitType);
    }
}
