package com.hxi.event_intake_service.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EventService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public EventService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void processEvent(Map<String, Object> event) {
        // Send to Kafka
        kafkaTemplate.send("raw-events-topic", event);

        // Later: save to MongoDB (optional now)
        System.out.println("Event published to Kafka: " + event);
    }
}
