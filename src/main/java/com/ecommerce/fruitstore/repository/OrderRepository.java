package com.ecommerce.fruitstore.repository;

import com.ecommerce.fruitstore.domain.CustomerOrder;

import java.util.List;

public interface OrderRepository {
      CustomerOrder saveOrder(CustomerOrder customerOrder);

      /** we will use this method to get the order by id , will return null if not found, for better clarity
       * we could make this return Optional<CustomerOrder> */
      CustomerOrder getOrderById(Long orderId);

      List<CustomerOrder>  getAllOrders();
}
