package com.ecommerce.fruitstore.controller;

import com.ecommerce.fruitstore.domain.OrderRequest;
import com.ecommerce.fruitstore.domain.OrderSummary;
import com.ecommerce.fruitstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main controller for eCommerce fruit store, uses Spring
 */
@RestController
@RequestMapping("/orders")
public class FruitStoreController {

    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderSummary> createAppleOrder(@RequestBody OrderRequest appleOrderRequest) {
        OrderSummary orderSummary = orderService.createdOrder(appleOrderRequest);
        return ResponseEntity.ok(orderSummary);
    }
}
