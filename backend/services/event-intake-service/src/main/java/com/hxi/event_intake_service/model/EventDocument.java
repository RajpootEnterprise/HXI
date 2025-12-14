package com.hxi.event_intake_service.model;

@Document(collection = "events")
public class EventDocument {

    @Id
    private String id;

    private String sessionId;
    private String userId;
    private String eventType;
    private String page;
    private Map<String, Object> metadata;
    private Instant timestamp;
}
