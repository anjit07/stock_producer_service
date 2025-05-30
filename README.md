## Stock Producer Service

This is the Kafka producer service responsible for publishing messages to the Kafka cluster.

## Features
- REST API to buy stocks,which publishes messages to Kafka.
- The scheduler sends stock prices to the Kafka topic `stock-prices` every 5 milliseconds.

## Before starting 
 - Ensure that you have a running Kafka cluster and the `stock-prices` topic is created.

## Refer this for Kafka setup:
- - [Kafka Setup Guide](setup_kafka.md)

## How to Run
1. Build the project:
   ```sh
   ./mvnw clean install
   ```
2. Run the application:
   ```sh
   ./mvnw spring-boot:run
   ```
   
## Requirements
- Java 17 or later
- Maven


