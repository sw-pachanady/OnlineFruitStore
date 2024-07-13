package com.ecommerce.fruitstore.service;

import com.ecommerce.fruitstore.util.NumberFormatter;
import com.ecommerce.fruitstore.buider.OrderBuilder;
import com.ecommerce.fruitstore.domain.CustomerOrder;
import com.ecommerce.fruitstore.domain.FruitType;
import com.ecommerce.fruitstore.domain.OrderRequest;
import com.ecommerce.fruitstore.domain.OrderSummary;
import com.ecommerce.fruitstore.promos.PromotionDetails;
import com.ecommerce.fruitstore.promos.PromotionManager;
import com.ecommerce.fruitstore.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
/** A service class for doing CRUD operations for order maintainance*/
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    PromotionManager promotionManager;
    @Autowired
    @Qualifier("orderJpaRepositoryImpl")
    OrderRepository orderRepository;

    @Autowired
    PricingService pricingService;


    public OrderServiceImpl(@Qualifier("orderJpaRepositoryImpl") OrderRepository orderRepository, PricingService pricingService, PromotionManager promotionManager) {
        this.orderRepository = orderRepository;
        this.pricingService = pricingService;
        this.promotionManager = promotionManager;
    }

    /**
     * Save order that consists of fruits(apples / oranges) and their quantities and promo codes
     */
    @Override
    public OrderSummary createOrder(OrderRequest orderRequest) {
        OrderBuilder builder = new OrderBuilder();
        int apples = orderRequest.getApples();
        if (orderRequest.getApples() > 0) {
            PromotionDetails promoAppled = promotionManager.applyPromotion(pricingService.getPrice(FruitType.APPLE), orderRequest.getApples(), orderRequest.getApplePromotionCodes());
            builder.addItem("APPLE", promoAppled.getUpdatedQuantity(), NumberFormatter.formatDecimal(promoAppled.getUppdatedUnitPrice()));
            apples = promoAppled.getUpdatedQuantity();
        }
        int oranges = orderRequest.getOranges();
        if (orderRequest.getOranges() > 0) {
            PromotionDetails promoAppled = promotionManager.applyPromotion(pricingService.getPrice(FruitType.ORANGE), orderRequest.getOranges(), orderRequest.getOrangePromotionCodes());
            builder.addItem("ORANGE", promoAppled.getUpdatedQuantity(), NumberFormatter.formatDecimal(promoAppled.getUppdatedUnitPrice()));
            oranges = promoAppled.getUpdatedQuantity();
        }

        CustomerOrder order = orderRepository.saveOrder(builder.build());

        logger.debug("Order ID: " + order.getId());
        double totalPrice = order.getOrderItems().stream().mapToDouble(item -> item.getTotalPrice().doubleValue()).sum();

        OrderSummary orderSummary = new OrderSummary(order.getId(), apples, oranges, NumberFormatter.formatBigDecimal(totalPrice), LocalDateTime.now());
        return orderSummary;
    }

    /**
     * Retrive order from db using given id
     */
    public CustomerOrder getOrderById(Long id) {
        return orderRepository.getOrderById(id);
    }

    /**
     * Retrive all the order , in real projects it never a good idea to return all rows , we should use filters and pagination
     */
    public List<CustomerOrder> getAllOrders() {
        return orderRepository.getAllOrders();
    }
}
