package com.ecommerce.fruitstore.service;

import com.ecommerce.fruitstore.domain.CustomerOrder;
import com.ecommerce.fruitstore.domain.OrderItem;
import com.ecommerce.fruitstore.repository.OrderJpaRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderRepositoryTest {

    @InjectMocks
    private OrderJpaRepositoryImpl orderRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<CustomerOrder> query;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllOrders() {
        CustomerOrder order1 = new CustomerOrder();
        order1.setId(1L);

        CustomerOrder order2 = new CustomerOrder();
        order2.setId(2L);

        List<CustomerOrder> orders = Arrays.asList(order1, order2);

        when(entityManager.createQuery(anyString(), eq(CustomerOrder.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(orders);

        List<CustomerOrder> result = orderRepository.getAllOrders();

        assertEquals(2, result.size());
        verify(entityManager, times(1)).createQuery(anyString(), eq(CustomerOrder.class));
        verify(query, times(1)).getResultList();
    }

    @Test
    public void testgetOrderById() {
        CustomerOrder order = new CustomerOrder();
        order.setId(1L);

        when(entityManager.find(CustomerOrder.class, 1L)).thenReturn(order);

        CustomerOrder result = orderRepository.getOrderById(1L);

        assertEquals(1L, result.getId());
        verify(entityManager, times(1)).find(CustomerOrder.class, 1L);
    }

    @Test
    public void testSaveOrder() {
        CustomerOrder order = new CustomerOrder();
        order.setId(1L);
        order.getOrderItems().add(new OrderItem(order, "Apple", 2, 0.6, 1, BigDecimal.valueOf(1.2)));
        order.getOrderItems().add(new OrderItem(order, "Orange", 2, 0.25, 2, BigDecimal.valueOf(0.5)));

        order.setId(1L);

        when(entityManager.merge(order)).thenReturn(order);

        CustomerOrder result = orderRepository.saveOrder(order);

        assertEquals(1L, result.getId());
        verify(entityManager, times(1)).merge(order);
    }

}
