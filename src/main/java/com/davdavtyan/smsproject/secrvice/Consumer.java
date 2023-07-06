package com.davdavtyan.smsproject.secrvice;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenerMessage(String message) {
        System.out.println("message: " + message);
    }
}
