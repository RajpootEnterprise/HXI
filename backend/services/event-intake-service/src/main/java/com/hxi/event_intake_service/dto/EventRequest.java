package com.hxi.event_intake_service.dto;

public class EventRequest {

    @NotBlank
    private String sessionId;

    @NotBlank(message = "event type cannot be empty or just spaces")
    private String eventType;

    private String page;

    private Map<String, Object> metadata;
}
