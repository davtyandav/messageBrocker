package com.davdavtyan.smsproject.secrvice;

import java.time.Duration;

import com.davdavtyan.smsproject.model.Message;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenerMessage(@Payload Message message, Acknowledgment ack) {


        if(message.getType().equals("")){
            ack.nack(Duration.ZERO);

        }else {
            System.out.println("Received message: " + message);
            ack.acknowledge();
        }

    }
}
