package com.ecommerce.fruitstore;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberFormatter {
    public static BigDecimal formatDecimal(double price) {
        BigDecimal roundedTotalPrice = BigDecimal.valueOf(price);
        return roundedTotalPrice.setScale(2, RoundingMode.HALF_UP);
    }
}
