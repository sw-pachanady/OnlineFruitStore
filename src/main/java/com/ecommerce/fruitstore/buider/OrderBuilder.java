package com.ecommerce.fruitstore.buider;

import com.ecommerce.fruitstore.NumberFormatter;
import com.ecommerce.fruitstore.domain.CustomerOrder;
import com.ecommerce.fruitstore.domain.OrderItem;

import java.util.ArrayList;
import java.util.List;

/** Simple builder to convert OrderRequest from web to Order Domain object */
public class OrderBuilder {

    List<OrderItem> orderItemList = new ArrayList<>();

    public OrderBuilder addItem(String itemName, int quantity, double unitPrice) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItemName(itemName);
        orderItem.setQuantity(quantity);
        orderItem.setUnitPrice(unitPrice);
        orderItem.setTotalPrice(NumberFormatter.formatDecimal(unitPrice * quantity));
        orderItemList.add(orderItem);
        return this;
    }

    public CustomerOrder build() {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setOrderItems(orderItemList);
        customerOrder.setOrderDate(java.time.LocalDateTime.now());

        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrder(customerOrder);
        }

        return customerOrder;
    }
}
