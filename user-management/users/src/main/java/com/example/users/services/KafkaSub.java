package com.example.users.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaSub {
    @KafkaListener(topics = "${kafka.topic}")
    public void listenToGroup(String message){
        System.out.println("User updated "+message);
    }
}
