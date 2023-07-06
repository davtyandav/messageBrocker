package com.davdavtyan.smsproject.secrvice;

import com.davdavtyan.smsproject.model.Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    public Producer(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void addMessageToKafka(Message message) {
        kafkaTemplate.send("my-topic4", message);
    }
}
