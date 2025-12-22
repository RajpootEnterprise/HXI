package com.hxi.common.dto;

import com.hxi.common.enums.EventType;

import java.time.Instant;
import java.util.Map;

public class EventMessage {

    private String eventId;
    private String sessionId;
    private String userId;
    private String page;
    private EventType eventType;
    private Map<String, Object> metadata;
    private Instant timestamp;

    // getters & setters
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getPage() { return page; }
    public void setPage(String page) { this.page = page; }

    public EventType getEventType() { return eventType; }
    public void setEventType(EventType eventType) { this.eventType = eventType; }

    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
