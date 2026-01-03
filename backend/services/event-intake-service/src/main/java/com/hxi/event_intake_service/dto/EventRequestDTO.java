package com.hxi.event_intake_service.dto;

import com.hxi.event_intake_service.enums.EventType;
import com.hxi.event_intake_service.enums.PlatformType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class EventRequestDTO {

    private String sessionId;
    private String userId;
    private EventType eventType;
    private String screen;
    private PlatformType platform;

    private Map<String, Object> metadata;
}

