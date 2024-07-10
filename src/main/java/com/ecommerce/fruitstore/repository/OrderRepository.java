package com.ecommerce.fruitstore.repository;

import com.ecommerce.fruitstore.domain.CustomerOrder;

public interface OrderRepository {

      CustomerOrder saveOrder(CustomerOrder customerOrder);
}
