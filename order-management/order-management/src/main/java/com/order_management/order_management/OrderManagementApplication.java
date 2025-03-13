package com.order_management.order_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(
  basePackages = "com.order_management.order_management.repository"
)
@EntityScan(basePackages = "com.order_management.order_management.entity")
public class OrderManagementApplication {

  public static void main(String[] args) {
    SpringApplication.run(OrderManagementApplication.class, args);
  }
}
