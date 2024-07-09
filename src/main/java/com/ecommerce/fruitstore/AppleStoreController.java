package com.ecommerce.fruitstore;

import com.ecommerce.fruitstore.domain.OrderRequest;
import com.ecommerce.fruitstore.domain.OrderSummary;
import com.ecommerce.fruitstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/orders")
public class AppleStoreController {

    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderSummary> createAppleOrder(@RequestBody OrderRequest appleOrderRequest) {
        OrderSummary orderSummary =  orderService.createdOrder(appleOrderRequest);
        return ResponseEntity.ok(orderSummary);
    }
}
