package com.hxi.event_intake_service.dto;

import com.hxi.event_intake_service.model.EventType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class EventRequest {

    private String eventId;

    @NotBlank
    private String sessionId;

    @NotBlank
    private String userId;

    @NotNull
    private EventType eventType;

    @NotBlank
    private String page;

    private Map<String, Object> metadata;
}
