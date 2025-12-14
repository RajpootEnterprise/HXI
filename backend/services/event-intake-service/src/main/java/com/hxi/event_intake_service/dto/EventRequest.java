package com.hxi.event_intake_service.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventRequest {

    @NotBlank
    private String sessionId;

    @NotBlank(message = "event type cannot be empty")
    private String eventType;

    private String page;

    private Map<String, Object> metadata;
}
