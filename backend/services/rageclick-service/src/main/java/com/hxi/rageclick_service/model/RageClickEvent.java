package com.hxi.rageclick_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "rage_clicks")
public class RageClickEvent {

    @Id
    private String id;

    private String sessionId;
    private String userId;
    private String screen;
    private String elementId;

    private int clickCount;
    private long timeWindowMs;

    private String severity; // LOW, MEDIUM, HIGH
    private long detectedAt;
}
