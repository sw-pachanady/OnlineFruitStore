package com.ecommerce.fruitstore.controller;

import com.ecommerce.fruitstore.domain.CustomerOrder;
import com.ecommerce.fruitstore.domain.OrderItem;
import com.ecommerce.fruitstore.domain.OrderRequest;
import com.ecommerce.fruitstore.domain.OrderSummary;
import com.ecommerce.fruitstore.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FruitStoreController.class)
@ExtendWith(MockitoExtension.class)

public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateOrder() throws Exception {
        OrderRequest orderRequest = new OrderRequest(3, 2, Collections.emptyList(), Collections.emptyList());

        OrderSummary orderSummary = new OrderSummary(1L, 3, 2, BigDecimal.valueOf(2.30), LocalDateTime.now());

        when(orderService.createOrder(any(OrderRequest.class))).thenReturn(orderSummary);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(orderSummary)));
    }

    @Test
    void testCreateOrderWithPromotions() throws Exception {
        OrderRequest orderRequest = new OrderRequest(3, 3, List.of(1L), List.of(2L));


        OrderSummary orderSummary = new OrderSummary(1L, 6, 4, BigDecimal.valueOf(2.8), LocalDateTime.now()); // Applying $3 discount

        when(orderService.createOrder(any(OrderRequest.class))).thenReturn(orderSummary);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(orderSummary)));
    }

    @Test
    void testInvalidEndpoint() throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/orders2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testInvalidMethod() throws Exception {
        ResultActions resultActions = mockMvc.perform(put("/orders"))
                .andExpect(status().isMethodNotAllowed());

    }

    @Test
    void testGetOrderById() throws Exception {

        CustomerOrder customerOrder = new CustomerOrder();
        List<OrderItem> orderItems = List.of(new OrderItem(customerOrder, "Apple", 3, 0.6, 1, BigDecimal.valueOf(1.8)),
                new OrderItem(customerOrder, "Orange", 2, 0.25, 2, BigDecimal.valueOf(0.5)));
        customerOrder.setOrderItems(orderItems);

        when(orderService.getOrderById(1L)).thenReturn(customerOrder);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(customerOrder)));
    }

    @Test
    void testGetAllOrders() throws Exception {
        CustomerOrder customerOrder1 = new CustomerOrder();
        List<OrderItem> orderItems1 = List.of(new OrderItem(customerOrder1, "Apple", 3, 0.6, 1, BigDecimal.valueOf(1.8)),
                new OrderItem(customerOrder1, "Orange", 2, 0.25, 2, BigDecimal.valueOf(0.5)));
        customerOrder1.setOrderItems(orderItems1);

        CustomerOrder customerOrder2 = new CustomerOrder();
        List<OrderItem> orderItems2 = List.of(new OrderItem(customerOrder2, "Apple", 3, 0.6, 1, BigDecimal.valueOf(1.8)),
                new OrderItem(customerOrder2, "Orange", 2, 0.25, 2, BigDecimal.valueOf(0.5)));
        customerOrder2.setOrderItems(orderItems2);

        when(orderService.getAllOrders()).thenReturn(List.of(customerOrder1, customerOrder2));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(customerOrder1, customerOrder2))));
    }
}
