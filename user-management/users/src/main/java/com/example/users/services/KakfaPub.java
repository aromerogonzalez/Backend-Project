package com.example.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.users.entities.User;

@Service
public class KakfaPub {
    private final String topic = "users_topic";

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    public void publish(User user){
        kafkaTemplate.send(topic,user);
    }
}
