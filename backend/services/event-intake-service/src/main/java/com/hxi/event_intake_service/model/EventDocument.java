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
    private String id; // Mongo _id

    @Indexed(unique = true)
    private String eventId;   // <-- THIS FIELD MUST EXIST

    private String sessionId;
    private String userId;
    private String page;
    private String eventType;
    private Map<String, Object> metadata;
    private Instant timestamp;
}
