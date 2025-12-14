package com.hxi.event_intake_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "events")
@Getter
@Setter
public class EventDocument {

    @Id
    private String id;

    private String sessionId;
    private String eventType;
    private String page;
    private Map<String, Object> metadata;
    private Instant timestamp;
}
