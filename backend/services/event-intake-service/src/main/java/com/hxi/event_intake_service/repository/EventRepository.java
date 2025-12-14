package com.hxi.event_intake_service.repository;

import com.hxi.event_intake_service.model.EventDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository
        extends MongoRepository<EventDocument, String> {
}

