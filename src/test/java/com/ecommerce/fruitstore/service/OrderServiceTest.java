package com.ecommerce.fruitstore.service;

import com.ecommerce.fruitstore.NumberFormatter;
import com.ecommerce.fruitstore.domain.*;
import com.ecommerce.fruitstore.promos.PromotionApplied;
import com.ecommerce.fruitstore.promos.PromotionManager;
import com.ecommerce.fruitstore.promos.PromotionManagerImpl;
import com.ecommerce.fruitstore.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    PricingService pricingService;

    @Mock
    PromotionManager promotionManager;

    @BeforeEach
    void setUp() {
        // No setup required for this example
    }

    @Test
    public void testCreateOrderWithApplesAndOranges() {

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.getOrderItems().add(createOrderItem("Apple", 3, 0.6));
        customerOrder.getOrderItems().add(createOrderItem("Orange", 2, 0.25));
        customerOrder.setId(1L); // In real use autigenerated by JPA

        when(orderRepository.saveOrder(any(CustomerOrder.class))).thenReturn(customerOrder);
        when(promotionManager.applyPromotion(0.6, 3, Collections.emptyList())).thenReturn(new PromotionApplied(0.6, 3, 0));
        when(promotionManager.applyPromotion(0.25, 2, Collections.emptyList())).thenReturn(new PromotionApplied( 0.25, 2, 0));
        when(pricingService.getPrice(FruitType.APPLE)).thenReturn(  0.6);
        when(pricingService.getPrice(FruitType.ORANGE)).thenReturn( 0.25 );

        OrderService orderService = new OrderServiceImpl(orderRepository, pricingService, promotionManager);
        OrderSummary summary = orderService.createdOrder(new OrderRequest(3, 2, Collections.emptyList(), Collections.emptyList()));
        assertNotNull(summary);
        assertNotNull(summary.getOrderId());
        assertEquals(1L, summary.getOrderId());
        assertTrue(summary.getTotalPrice().equals(NumberFormatter.formatBigDecimal(2.3)));

        verify(orderRepository, times(1)).saveOrder(any(CustomerOrder.class));
        assertEquals(3, summary.getApples());
        assertEquals(2, summary.getOranges());
    }

    @Test
    public void testCreateOrderWithApplesOnly() {

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.getOrderItems().add(createOrderItem("Apple", 3, 0.6));


        when(promotionManager.applyPromotion(0.6, 3, Collections.emptyList())).thenReturn(new PromotionApplied(0.6, 3, 0));
        when(pricingService.getPrice(FruitType.APPLE)).thenReturn(  0.6);
        when(orderRepository.saveOrder(any(CustomerOrder.class))).thenReturn(customerOrder);

        OrderService orderService = new OrderServiceImpl(orderRepository, pricingService, promotionManager);
        OrderSummary summary = orderService.createdOrder(new OrderRequest(3, 0, Collections.emptyList(), Collections.emptyList()));
        assertNotNull(summary);
        assertTrue(summary.getTotalPrice().equals(NumberFormatter.formatBigDecimal(1.8)));

        verify(orderRepository, times(1)).saveOrder(any(CustomerOrder.class));
        assertEquals(3, summary.getApples());
        assertEquals(0, summary.getOranges());
    }


    @Test
    public void testCreateOrderWithOrangesOnly() {
        

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.getOrderItems().add(createOrderItem("Orange", 2, 0.25));


        when(promotionManager.applyPromotion(0.25, 2, Collections.emptyList())).thenReturn(new PromotionApplied( 0.25, 2, 0));
        when(pricingService.getPrice(FruitType.ORANGE)).thenReturn( 0.25 );
        when(orderRepository.saveOrder(any(CustomerOrder.class))).thenReturn(customerOrder);

        OrderService orderService = new OrderServiceImpl(orderRepository, pricingService, promotionManager);

        OrderSummary summary = orderService.createdOrder(new OrderRequest(0, 2, Collections.emptyList(), Collections.emptyList()));
        assertNotNull(summary);
        //assertEquals(3L, summary.getOrderId());
        assertTrue(summary.getTotalPrice().equals(NumberFormatter.formatBigDecimal(0.5)));

        verify(orderRepository, times(1)).saveOrder(any(CustomerOrder.class));
        assertEquals(0, summary.getApples());
        assertEquals(2, summary.getOranges());
    }

    @Test
    public void testCreateWithNoItems() {
        

        CustomerOrder customerOrder = new CustomerOrder();
        //customerOrder.setId(4L); // Will be set by JPA in real use

        OrderService orderService = new OrderServiceImpl(orderRepository, pricingService, promotionManager);

        when(orderRepository.saveOrder(any(CustomerOrder.class))).thenReturn(customerOrder);

        OrderSummary summary = orderService.createdOrder(new OrderRequest(0, 0, Collections.emptyList(), Collections.emptyList()));
        assertNotNull(summary);

        verify(orderRepository, times(1)).saveOrder(any(CustomerOrder.class));
        assertEquals(0, summary.getApples());
        assertEquals(0, summary.getOranges());
    }


    @Test
    public void testCreateOrderWithApplesAndOrangePromotions() {
 
        OrderService orderService = new OrderServiceImpl(orderRepository, pricingService, new PromotionManagerImpl());

        when(orderRepository.saveOrder(any(CustomerOrder.class))).thenAnswer(new Answer<CustomerOrder>() {
            @Override
            public CustomerOrder answer(InvocationOnMock invocation) throws Throwable {
                CustomerOrder argument = invocation.getArgument(0);
                argument.setId(1L);
                return argument;
            }
        });

        when(pricingService.getPrice(FruitType.APPLE)).thenReturn(  0.6);
        when(pricingService.getPrice(FruitType.ORANGE)).thenReturn( 0.25 );

        OrderSummary summary = orderService.createdOrder(new OrderRequest(3, 3, List.of(1L), List.of(2L)));
        assertNotNull(summary);
        assertNotNull(summary.getOrderId());
        assertEquals(1L, summary.getOrderId());
        assertTrue(summary.getTotalPrice().equals(NumberFormatter.formatBigDecimal(2.8)));

        verify(orderRepository, times(1)).saveOrder(any(CustomerOrder.class));
        assertEquals(6, summary.getApples());
        assertEquals(4, summary.getOranges());
    }

    @Test
    public void testCreateOrderWithApplesAndOrangesWithOnlyApplePromotion() {

        OrderService orderService = new OrderServiceImpl(orderRepository, pricingService, new PromotionManagerImpl());

        when(orderRepository.saveOrder(any(CustomerOrder.class))).thenAnswer(new Answer<CustomerOrder>() {
            @Override
            public CustomerOrder answer(InvocationOnMock invocation) throws Throwable {
                CustomerOrder argument = invocation.getArgument(0);
                argument.setId(1L);
                return argument;
            }
        });

        when(pricingService.getPrice(FruitType.APPLE)).thenReturn(  0.6);
        when(pricingService.getPrice(FruitType.ORANGE)).thenReturn( 0.25 );

        OrderSummary summary = orderService.createdOrder(new OrderRequest(6, 2, List.of(1L), Collections.emptyList()));
        assertNotNull(summary);
        assertNotNull(summary.getOrderId());
        assertEquals(1L, summary.getOrderId());
        assertTrue(summary.getTotalPrice().equals(NumberFormatter.formatBigDecimal(4.1)));

        verify(orderRepository, times(1)).saveOrder(any(CustomerOrder.class));
        assertEquals(12, summary.getApples());
        assertEquals(2, summary.getOranges());
    }

    private OrderItem createOrderItem(String itemName, int quantity, double unitPrice) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItemName(itemName);
        orderItem.setQuantity(quantity);
        orderItem.setUnitPrice(unitPrice);
        orderItem.setTotalPrice(NumberFormatter.formatBigDecimal(quantity * unitPrice));
        return orderItem;
    }
}
