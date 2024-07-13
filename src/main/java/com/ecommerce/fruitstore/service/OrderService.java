package com.ecommerce.fruitstore.service;

import com.ecommerce.fruitstore.domain.CustomerOrder;
import com.ecommerce.fruitstore.domain.OrderRequest;
import com.ecommerce.fruitstore.domain.OrderSummary;

import java.util.List;

/** a service to manage orders to help build REST end points */
public interface OrderService {

    OrderSummary createOrder(OrderRequest orderRequest);

    CustomerOrder getOrderById(Long id);

    List<CustomerOrder> getAllOrders();
}
