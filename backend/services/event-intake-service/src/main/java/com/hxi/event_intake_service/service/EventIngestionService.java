package com.hxi.event_intake_service.service;

import com.hxi.event_intake_service.dto.EventRequest;
import com.hxi.event_intake_service.kafka.EventProducer;
import com.hxi.event_intake_service.model.EventDocument;
import com.hxi.event_intake_service.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventIngestionService {

    private final EventRepository repository;
    private final EventProducer producer;

    public void ingest(EventRequest request) {

        if (repository.existsByEventId(request.getEventId())) {
            log.warn("Duplicate event ignored: {}", request.getEventId());
            return;
        }

        EventDocument doc = new EventDocument();
        doc.setEventId(request.getEventId());
        doc.setSessionId(request.getSessionId());
        doc.setUserId(request.getUserId());
        doc.setPage(request.getPage());
        doc.setEventType(request.getEventType());
        doc.setMetadata(request.getMetadata());
        doc.setTimestamp(Instant.now());

        repository.save(doc);
        producer.publishEvent(doc);

        log.info("Event stored & published: {}", request.getEventId());
    }
}
