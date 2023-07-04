package com.davdavtyan.smsproject;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmsProjectApplication {

    @Autowired
    private KafkaProperties kafkaProperties;

    public static void main(String[] args) {
        SpringApplication.run(SmsProjectApplication.class, args);
    }

    @PostConstruct
    public void runProducer() {
        // Конфигурация производителя Kafka
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaProperties.getBootstrapServers());
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());

        // Создание экземпляра производителя Kafka
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // Топик и сообщение для отправки
        String topic = "my-topic";
        String message = "Hello, Kafka!";

        // Создание записи для отправки
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);

        try {
            // Отправка сообщения
            producer.send(record);
            System.out.println("Сообщение отправлено на топик: " + topic);
        } finally {
            // Закрытие производителя Kafka
            producer.close();
        }
    }

    @PostConstruct
    public void runConsumer() {
        // Конфигурация потребителя Kafka
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-consumer-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Создание экземпляра потребителя Kafka
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // Подписка на топик
        String topic = "my-topic";
        consumer.subscribe(Collections.singleton(topic));

        // Цикл чтения сообщений
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Получено сообщение:");
                System.out.println("Топик: " + record.topic());
                System.out.println("Ключ: " + record.key());
                System.out.println("Значение: " + record.value());
                System.out.println("Смещение: " + record.offset());
                System.out.println("Партиция: " + record.partition());
                System.out.println("--------------------");
            }
        }
    }
}
