package com.hxi.event_intake_service.service;

import com.hxi.event_intake_service.dto.EventRequest;
import com.hxi.event_intake_service.kafka.EventProducer;
import com.hxi.event_intake_service.model.EventDocument;
import com.hxi.event_intake_service.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EventIngestionService {

    private final EventRepository repository;
    private final EventProducer producer;

    public EventIngestionService(EventRepository repository, EventProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    public void ingest(EventRequest request) {

        EventDocument doc = new EventDocument();
        doc.setSessionId(request.getSessionId());
        doc.setEventType(request.getEventType());
        doc.setPage(request.getPage());
        doc.setMetadata(request.getMetadata());
        doc.setTimestamp(Instant.now());

        repository.save(doc);
        producer.publish(doc.toString());
    }
}

