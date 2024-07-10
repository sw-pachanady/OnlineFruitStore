package com.ecommerce.fruitstore.repository;

import com.ecommerce.fruitstore.domain.CustomerOrder;
import org.springframework.stereotype.Repository;


public interface OrderRepository {

      CustomerOrder saveOrder(CustomerOrder customerOrder);
}
