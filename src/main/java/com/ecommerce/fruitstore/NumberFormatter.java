package com.ecommerce.fruitstore;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberFormatter {

    private static final DecimalFormat decimalFormatter = new DecimalFormat("#.00");

    public static BigDecimal formatBigDecimal(double price) {
        BigDecimal roundedTotalPrice = BigDecimal.valueOf(price);
        return roundedTotalPrice.setScale(2, RoundingMode.HALF_UP);
    }

    public static double formatDecimal(double price) {
        return formatBigDecimal(price).doubleValue();
    }

}
