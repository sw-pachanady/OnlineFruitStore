package com.ecommerce.fruitstore.repository;

import com.ecommerce.fruitstore.domain.CustomerOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository {

    void saveOrder(CustomerOrder customerOrder);
}
