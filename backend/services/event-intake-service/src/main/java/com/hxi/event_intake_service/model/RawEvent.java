package com.hxi.event_intake_service.model;

import com.hxi.event_intake_service.enums.EventType;
import com.hxi.event_intake_service.enums.PlatformType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document(collection = "raw_events")
public class RawEvent {

    @Id
    private String id;

    private String sessionId;
    private String userId;
    private EventType eventType;
    private String screen;
    private PlatformType platform;

    private Map<String, Object> metadata;

    private long timestamp;
}

