# Order Service API

## Overview
An online Fruit Order Service implemented as a Spring Boot application that allows customers to place orders 
for apples and oranges. It also supports promotions specific to each fruit type.

## Features
- Create an order with apples and oranges.
- Apply promotions specific to apples and/or oranges.
- Retrieve a specific order by ID.
- Retrieve all orders.

###  Sample Requests

POST /orders
```json
{
  "apples": 2,
  "oranges": 4,
  "applePromotionCodes": [1],
  "orangePromotionCodes": [2]
}
```
GET /orders/1

GET /orders

We currently do not support multiple promotions for the same fruit type in a single order.

## Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven
- JUnit 5
- Mockito

## Setup and Installation

### Prerequisites
- Java 11 or later
- Maven 3.6.0 or later

### Clone the Repository
```sh
git clone https://github.com/sw-pachanady/OnlineFruitStore.git
cd OnlineFruitStore
```
### Build with maven and run the tests
```sh
mvn clean install
```
### Run the application
```sh
mvn spring-boot:run
```