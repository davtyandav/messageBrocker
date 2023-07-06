package com.davdavtyan.smsproject.controller;

import com.davdavtyan.smsproject.model.Message;
import com.davdavtyan.smsproject.secrvice.Producer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class MessageController {

    private final Producer producer;

    public MessageController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping("/add")
    public void addMessagesInKafka(@RequestBody Message message) {
        producer.addMessageToKafka(message);
    }
}
