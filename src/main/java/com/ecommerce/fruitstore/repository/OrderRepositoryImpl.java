package com.ecommerce.fruitstore.repository;

import com.ecommerce.fruitstore.domain.CustomerOrder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

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

    @Override
    @Transactional
    public CustomerOrder getOrderById(Long orderId) {
        return entityManager.find(CustomerOrder.class, orderId);
    }

    @Override
    @Transactional
    public List<CustomerOrder> getAllOrders() {
        TypedQuery<CustomerOrder> query = entityManager.createQuery("SELECT o FROM CustomerOrder o", CustomerOrder.class);
        return query.getResultList();
    }

}
