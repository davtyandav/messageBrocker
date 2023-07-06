package com.davdavtyan.smsproject.controller;

import com.davdavtyan.smsproject.secrvice.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class MessageController {

    @Autowired
    Producer producer;

    @GetMapping("/add")
    public void addMessagesInKafka(@RequestParam String message) {
        producer.addMessageToKafka(message);
    }
}
