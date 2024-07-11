package com.ecommerce.fruitstore.service;

import com.ecommerce.fruitstore.domain.CustomerOrder;
import com.ecommerce.fruitstore.domain.OrderRequest;
import com.ecommerce.fruitstore.domain.OrderSummary;

import java.util.List;

public interface OrderService {

    OrderSummary createdOrder(OrderRequest orderRequest);

    CustomerOrder getOrderById(Long id);

    List<CustomerOrder> getAllOrders();
}
