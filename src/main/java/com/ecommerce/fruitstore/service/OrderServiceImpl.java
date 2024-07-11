package com.ecommerce.fruitstore.service;

import com.ecommerce.fruitstore.NumberFormatter;
import com.ecommerce.fruitstore.buider.OrderBuilder;
import com.ecommerce.fruitstore.domain.CustomerOrder;
import com.ecommerce.fruitstore.domain.FruitType;
import com.ecommerce.fruitstore.domain.OrderRequest;
import com.ecommerce.fruitstore.domain.OrderSummary;
import com.ecommerce.fruitstore.promos.PromotionApplied;
import com.ecommerce.fruitstore.promos.PromotionManager;
import com.ecommerce.fruitstore.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
/** A service class for doing CRUD operations for order maintainance*/
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    PromotionManager promotionManager;
    @Autowired
    OrderRepository orderRepository = null;

    @Autowired
    PricingService pricingService;


    public OrderServiceImpl(OrderRepository orderRepository, PricingService pricingService, PromotionManager promotionManager) {
        this.orderRepository = orderRepository;
        this.pricingService = pricingService;
        this.promotionManager = promotionManager;
    }

    @Override
    public OrderSummary createdOrder(OrderRequest orderRequest) {
        OrderBuilder builder = new OrderBuilder();
        int apples = orderRequest.getApples();
        if (orderRequest.getApples() > 0) {
            PromotionApplied promoAppled = promotionManager.applyPromotion(pricingService.getPrice(FruitType.APPLE), orderRequest.getApples(), orderRequest.getApplePromotionCodes());
            builder.addItem("APPLE", promoAppled.getUpdatedQuantity(), promoAppled.getUnitPrice());
            apples = promoAppled.getUpdatedQuantity();
        }
        int oranges = orderRequest.getOranges();
        if (orderRequest.getOranges() > 0) {
            PromotionApplied promoAppled = promotionManager.applyPromotion(pricingService.getPrice(FruitType.ORANGE), orderRequest.getOranges(), orderRequest.getOrangePromotionCodes());
            builder.addItem("ORANGE", promoAppled.getUpdatedQuantity(), pricingService.getPrice(FruitType.ORANGE));
            oranges = promoAppled.getUpdatedQuantity();
        }

        CustomerOrder order = orderRepository.saveOrder(builder.build());

        logger.info("Order ID: " + order.getId());
        double totalPrice = order.getOrderItems().stream().mapToDouble(item -> item.getTotalPrice().doubleValue()).sum();

        OrderSummary orderSummary = new OrderSummary(order.getId(), apples, oranges, NumberFormatter.formatDecimal(totalPrice), LocalDateTime.now());
        return orderSummary;
    }
}
