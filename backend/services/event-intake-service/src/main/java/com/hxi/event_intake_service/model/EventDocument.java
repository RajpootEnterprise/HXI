package com.hxi.event_intake_service.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@Document(collection = "events")
public class EventDocument {

    @Id
    private String id;

    @Indexed(unique = true)
    private String eventId;

    private String sessionId;
    private String userId;

    // 🔥 MUST BE ENUM — NOT STRING
    private EventType eventType;

    private String page;
    private Map<String, Object> metadata;
    private Instant timestamp;
}
