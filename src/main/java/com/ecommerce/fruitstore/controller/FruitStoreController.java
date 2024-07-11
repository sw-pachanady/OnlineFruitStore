package com.ecommerce.fruitstore.controller;

import com.ecommerce.fruitstore.domain.CustomerOrder;
import com.ecommerce.fruitstore.domain.OrderRequest;
import com.ecommerce.fruitstore.domain.OrderSummary;
import com.ecommerce.fruitstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<CustomerOrder> getOrderById(@PathVariable Long id) {
        CustomerOrder order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<CustomerOrder>> getAllOrders() {
        List<CustomerOrder> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}
