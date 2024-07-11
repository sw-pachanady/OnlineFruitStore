package com.ecommerce.fruitstore.repository;

import com.ecommerce.fruitstore.domain.CustomerOrder;

import java.util.List;

public interface OrderRepository {
      CustomerOrder saveOrder(CustomerOrder customerOrder);

      CustomerOrder getOrderById(Long orderId);

      List<CustomerOrder>  getAllOrders();
}
