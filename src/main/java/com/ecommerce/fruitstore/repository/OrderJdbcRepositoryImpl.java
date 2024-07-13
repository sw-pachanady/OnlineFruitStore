package com.ecommerce.fruitstore.repository;


import com.ecommerce.fruitstore.domain.CustomerOrder;
import com.ecommerce.fruitstore.domain.OrderItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * This class is responsible for saving and retrieving orders from the database using SQL queries
 */
@Repository
public class OrderJdbcRepositoryImpl implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderJdbcRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public CustomerOrder saveOrder(CustomerOrder order) {
        if (order.getId() == null) {
            return insertOrder(order);
        } else {
            return updateOrder(order);
        }
    }

    @Override
    public List<CustomerOrder> getAllOrders() {
        String sql = "SELECT * FROM customer_order";
        List<CustomerOrder> orders = jdbcTemplate.query(sql, new OrderRowMapper());

        for (CustomerOrder order : orders) {
            order.setOrderItems(findOrderItemsByOrderId(order.getId()));
        }

        return orders;
    }

    private CustomerOrder insertOrder(CustomerOrder order) {
        String sql = "INSERT INTO customer_order (order_date) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(order.getOrderDate()));
            return ps;
        }, keyHolder);

        Long orderId = keyHolder.getKey().longValue();
        order.setId(orderId);

        for (OrderItem item : order.getOrderItems()) {
            insertOrderItem(orderId, item);
        }

        return order;
    }

    private CustomerOrder updateOrder(CustomerOrder order) {
        String sql = "UPDATE customer_order SET order_date = ? WHERE id = ?";
        jdbcTemplate.update(sql, order.getOrderDate(), order.getId());

        // Delete existing order items and insert new ones
        jdbcTemplate.update("DELETE FROM order_item WHERE order_id = ?", order.getId());
        for (OrderItem item : order.getOrderItems()) {
            insertOrderItem(order.getId(), item);
        }

        return order;
    }


    @Override
    public CustomerOrder getOrderById(Long id) {
        String sql = "SELECT * FROM customer_order WHERE id = ?";
        List<CustomerOrder> orders = jdbcTemplate.query(sql, new Object[]{id}, new OrderRowMapper());

        CustomerOrder order = orders.get(0);
        order.setOrderItems(findOrderItemsByOrderId(id));
        return order;
    }

    private List<OrderItem> findOrderItemsByOrderId(Long orderId) {
        String sql = "SELECT * FROM order_item WHERE order_id = ?";
        return jdbcTemplate.query(sql, new Object[]{orderId}, new OrderItemRowMapper());
    }

    private void insertOrderItem(Long orderId, OrderItem item) {
        String sql = "INSERT INTO order_item (order_id, item_name, quantity, unit_price, total_price) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, orderId, item.getItemName(), item.getQuantity(), item.getUnitPrice(), item.getTotalPrice());
    }

    private class OrderItemRowMapper implements RowMapper<OrderItem> {
        @Override
        public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderItem item = new OrderItem();
            item.setId(rs.getLong("id"));
            item.setItemName(rs.getString("item_name"));
            item.setQuantity(rs.getInt("quantity"));
            item.setUnitPrice(rs.getDouble("unit_price"));
            item.setTotalPrice(rs.getBigDecimal("total_price"));
            return item;
        }
    }

    private class OrderRowMapper implements RowMapper<CustomerOrder> {
        @Override
        public CustomerOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
            CustomerOrder order = new CustomerOrder();
            order.setId(rs.getLong("id"));
            order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
            return order;
        }
    }
}
