package com.ecommerce.fruitstore.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderSummary {

    private Long orderId;
    private int apples;

    private int oranges;

    private BigDecimal totalPrice;

    private LocalDateTime orderDateTime;

    public Long getOrderId() {
        return orderId;
    }

    public int getApples() {
        return apples;
    }

    public int getOranges() {
        return oranges;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public OrderSummary(Long orderId, int apples, int oranges, BigDecimal totalPrice, LocalDateTime orderDateTime) {
        this.orderId = orderId;
        this.apples = apples;
        this.oranges = oranges;
        this.totalPrice = totalPrice;
        this.orderDateTime = orderDateTime;
    }
}


