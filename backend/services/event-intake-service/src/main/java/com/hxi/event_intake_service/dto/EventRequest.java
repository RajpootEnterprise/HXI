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

    @NotBlank
    private String eventId;

    @NotBlank
    private String sessionId;

    @NotBlank
    private String userId;

    @NotBlank
    private String page;

    @NotNull
    private EventType eventType;

    private Map<String, Object> metadata;
}
