package com.fausto.users.users.services.Kafka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fausto.users.users.entities.UserData;


@Service
public class KafkaProducerService {
	@Value("${kafka.topic}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, UserData> kafkaTemplate;

    public void sendMessage(UserData message) {
        kafkaTemplate.send(topicName, message);
    }
}
