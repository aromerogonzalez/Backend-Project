package com.order_management.order_management.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order_management.order_management.entity.Order;
import com.order_management.order_management.exception.OrderException;
import com.order_management.order_management.kafka.KafkaProducer;
import com.order_management.order_management.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

  private final OrderRepository orderRepository;
  private final KafkaProducer kafkaProducer;
  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;

  @Autowired
  public OrderService(
    OrderRepository orderRepository,
    KafkaProducer kafkaProducer,
    RestTemplate restTemplate,
    ObjectMapper objectMapper
  ) {
    this.orderRepository = orderRepository;
    this.kafkaProducer = kafkaProducer;
    this.restTemplate = restTemplate;
    this.objectMapper = objectMapper;
  }

  public Order getOrderById(Long id) {
    return orderRepository
      .findById(id)
      .orElseThrow(() -> new OrderException("Order not found with id: " + id));
  }

  public Order createOrder(Order order) {
    JsonNode userDetails = getUserDetails(order.getCustomerName());
    JsonNode bookDetails = getBookDetails(order.getProduct());

    // Process the order

    Order createdOrder = orderRepository.save(order);
    kafkaProducer.sendMessage("Order created: " + createdOrder.toString());
    return createdOrder;
  }

  public Order updateOrder(Long id, Order updatedOrder) {
    Order existingOrder = orderRepository
      .findById(id)
      .orElseThrow(() -> new OrderException("Order not found with id: " + id));

    existingOrder.setCustomerName(updatedOrder.getCustomerName());
    existingOrder.setProduct(updatedOrder.getProduct());
    existingOrder.setQuantity(updatedOrder.getQuantity());
    existingOrder.setStatus(updatedOrder.getStatus());

    Order updatedOrderEntity = orderRepository.save(existingOrder);
    kafkaProducer.sendMessage(
      "Order updated: " + updatedOrderEntity.toString()
    );
    return updatedOrderEntity;
  }

  private JsonNode getUserDetails(String userId) {
    String endPoint = "http://localhost:8080/users/" + userId;
    HttpHeaders headers = new HttpHeaders();
    headers.set(
      "Authorization",
      "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0LXVzZXIifQ.isF2Ew9THqtcCDdalOeOLIpftDtDOfFU8SAm-Qnh9XM"
    );
    HttpEntity<Void> entity = new HttpEntity<>(headers);
    ResponseEntity<String> response = restTemplate.exchange(
      endPoint,
      HttpMethod.GET,
      entity,
      String.class
    );
    try {
      return objectMapper.readTree(response.getBody());
    } catch (Exception e) {
      throw new OrderException(
        "Failed to parse JSON for user details: " + e.getMessage()
      );
    }
  }

  private JsonNode getBookDetails(String bookId) {
    String endPoint = "http://localhost:8080/books/" + bookId;
    HttpHeaders headers = new HttpHeaders();
    headers.set(
      "Authorization",
      "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0LXVzZXIifQ.isF2Ew9THqtcCDdalOeOLIpftDtDOfFU8SAm-Qnh9XM"
    );
    HttpEntity<Void> entity = new HttpEntity<>(headers);
    ResponseEntity<String> response = restTemplate.exchange(
      endPoint,
      HttpMethod.GET,
      entity,
      String.class
    );
    try {
      return objectMapper.readTree(response.getBody());
    } catch (Exception e) {
      throw new OrderException(
        "Failed to parse JSON for book details: " + e.getMessage()
      );
    }
  }
}
