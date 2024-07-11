package com.ecommerce.fruitstore.controller;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        when(orderService.createdOrder(any(OrderRequest.class))).thenReturn(orderSummary);

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

        when(orderService.createdOrder(any(OrderRequest.class))).thenReturn(orderSummary);

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
        ResultActions resultActions = mockMvc.perform(get("/orders"))
                .andExpect(status().isMethodNotAllowed());

    }
}
