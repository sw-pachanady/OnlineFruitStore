package com.ecommerce.fruitstore.controller;

import com.ecommerce.fruitstore.domain.CustomerOrder;
import com.ecommerce.fruitstore.domain.Errors;
import com.ecommerce.fruitstore.domain.OrderRequest;
import com.ecommerce.fruitstore.domain.OrderSummary;
import com.ecommerce.fruitstore.service.OrderService;
import com.ecommerce.fruitstore.validators.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Main controller for online fruit store, uses Spring
 */
@RestController
@RequestMapping("/orders")
public class FruitStoreController {
    private final RequestValidator requestValidator;
    @Autowired
    OrderService orderService;

    public FruitStoreController() {
        this.requestValidator = new RequestValidator();
    }

    @PostMapping
    public ResponseEntity<OrderSummary> createFruitOrder(@RequestBody OrderRequest fruitOrderRequest) {

        Errors validationErrors = requestValidator.validateOrderRequest(fruitOrderRequest);
        if (!validationErrors.getErrorList().isEmpty()) {
            return ResponseEntity.badRequest().body(new OrderSummary(validationErrors));
        }
        OrderSummary orderSummary = orderService.createOrder(fruitOrderRequest);
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
