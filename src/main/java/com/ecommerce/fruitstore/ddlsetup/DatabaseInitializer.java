package com.ecommerce.fruitstore.ddlsetup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * create tables in the in memory H2  database using InitializingBean
 */
@Component
public class DatabaseInitializer implements InitializingBean {
    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public DatabaseInitializer(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public void afterPropertiesSet() {
        transactionTemplate.execute(status -> {
            createCustomerOrderTable();
            createOrderItemTable();
            return null;
        });
    }

    private void createCustomerOrderTable() {
        String sql = "CREATE TABLE IF NOT EXISTS customer_order (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "order_date TIMESTAMP NOT NULL " +
                ")";
        jdbcTemplate.execute(sql);
    }

    private void createOrderItemTable() {
        String sql = "CREATE TABLE IF NOT EXISTS order_item (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "order_id BIGINT, " +
                "item_name VARCHAR(255), " +
                "quantity INTEGER NOT NULL, " +
                "unit_price DOUBLE NOT NULL, " +
                "total_price DOUBLE NOT NULL, " +
                "FOREIGN KEY (order_id) REFERENCES customer_order(id))";
        jdbcTemplate.execute(sql);
    }
}
