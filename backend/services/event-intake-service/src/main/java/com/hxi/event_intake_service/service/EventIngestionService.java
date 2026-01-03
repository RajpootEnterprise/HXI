package com.hxi.event_intake_service.service;

import com.hxi.event_intake_service.dto.EventRequestDTO;
import com.hxi.event_intake_service.model.RawEvent;
import com.hxi.event_intake_service.util.EventValidator;
import org.springframework.stereotype.Service;

@Service
public class EventIngestionService {

    private final KafkaEventProducer kafkaProducer;

    public EventIngestionService(KafkaEventProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public void ingest(EventRequestDTO dto) {

        EventValidator.validate(dto);

        RawEvent event = new RawEvent();
        event.setSessionId(dto.getSessionId());
        event.setUserId(dto.getUserId());
        event.setEventType(dto.getEventType());
        event.setScreen(dto.getScreen());
        event.setPlatform(dto.getPlatform());
        event.setMetadata(dto.getMetadata());
        event.setTimestamp(System.currentTimeMillis());

        kafkaProducer.publish(event);
    }
}
