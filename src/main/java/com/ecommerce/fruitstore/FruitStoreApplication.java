package com.ecommerce.fruitstore;

import com.ecommerce.fruitstore.ddlsetup.DatabaseInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FruitStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(FruitStoreApplication.class, args);
        /*DatabaseInitializer databaseInitializer = new DatabaseInitializer();
        databaseInitializer.initialize();*/
    }
}
