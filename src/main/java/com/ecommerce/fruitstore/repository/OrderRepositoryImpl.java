package com.ecommerce.fruitstore.repository;

import com.ecommerce.fruitstore.domain.CustomerOrder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public CustomerOrder saveOrder(CustomerOrder customerOrder) {

        if (customerOrder.getId() == null) {
            entityManager.persist(customerOrder);
        } else {
            customerOrder = entityManager.merge(customerOrder);
        }
        return customerOrder;
    }
}
