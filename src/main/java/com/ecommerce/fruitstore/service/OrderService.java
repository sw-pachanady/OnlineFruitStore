package com.ecommerce.fruitstore.service;

import com.ecommerce.fruitstore.domain.OrderRequest;
import com.ecommerce.fruitstore.domain.OrderSummary;

public interface OrderService {

    OrderSummary createdOrder(OrderRequest orderRequest);
}
