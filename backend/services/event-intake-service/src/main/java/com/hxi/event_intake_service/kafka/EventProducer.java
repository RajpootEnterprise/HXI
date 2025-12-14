package com.hxi.event_intake_service.kafka;

import com.hxi.event_intake_service.model.EventDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "user-events";

    public void publishEvent(EventDocument event) {

        Map<String, Object> payload = new HashMap<>();
        payload.put("eventId", event.getEventId());
        payload.put("sessionId", event.getSessionId());
        payload.put("userId", event.getUserId());
        payload.put("page", event.getPage());

        // ✅ FIX: convert enum → string
        payload.put("eventType", event.getEventType().name());

        payload.put("metadata", event.getMetadata());
        payload.put("timestamp", event.getTimestamp().toString());

        kafkaTemplate.send(TOPIC, event.getEventId(), payload);

        log.info("Kafka event published: {}", event.getEventId());
    }
}
