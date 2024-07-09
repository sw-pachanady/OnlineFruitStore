package com.ecommerce.fruitstore.service;

import com.ecommerce.fruitstore.buider.OrderBuilder;
import com.ecommerce.fruitstore.domain.CustomerOrder;
import com.ecommerce.fruitstore.domain.FruitType;
import com.ecommerce.fruitstore.domain.OrderRequest;
import com.ecommerce.fruitstore.domain.OrderSummary;
import com.ecommerce.fruitstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
/** A service class for doing CRUD operations for order maintainance*/
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository = null;

    @Autowired
    PricingService pricingService;

    @Override
    public OrderSummary createdOrder(OrderRequest orderRequest) {
        OrderBuilder builder = new OrderBuilder();
        if(orderRequest.getApples() > 0) {
            builder.addItem("APPLE", orderRequest.getApples(), pricingService.getPrice(FruitType.APPLE));
        }
        if(orderRequest.getOranges() > 0) {
            builder.addItem("ORANGE", orderRequest.getOranges(), pricingService.getPrice(FruitType.ORANGE));
        }

        CustomerOrder order = builder.build();
        orderRepository.saveOrder(builder.build());

        OrderSummary orderSummary = new OrderSummary(order.getId(), orderRequest.getApples(), orderRequest.getOranges(), orderRequest.getApples() * pricingService.getPrice(FruitType.APPLE) + orderRequest.getOranges() * pricingService.getPrice(FruitType.ORANGE),  LocalDateTime.now());
        return orderSummary;
    }
}
