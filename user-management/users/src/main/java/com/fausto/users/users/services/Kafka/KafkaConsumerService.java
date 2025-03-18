package com.fausto.users.users.services.Kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
	@KafkaListener(topics = "${kafka.topic}")
    public void listenGroup(String message) {
        System.out.println("users updated: " + message);
    }
}
