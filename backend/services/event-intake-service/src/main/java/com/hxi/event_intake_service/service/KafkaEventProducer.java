package com.hxi.event_intake_service.service;

import com.hxi.event_intake_service.model.RawEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventProducer {

    private final KafkaTemplate<String, RawEvent> kafkaTemplate;

    public KafkaEventProducer(KafkaTemplate<String, RawEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(RawEvent event) {
        kafkaTemplate.send("ux.events.raw", event.getSessionId(), event);
    }
}

