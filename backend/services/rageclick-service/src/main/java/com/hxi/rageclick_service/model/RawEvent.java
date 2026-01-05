package com.hxi.rageclick_service.model;

import java.util.Map;

public class RawEvent {

    private String sessionId;
    private String userId;
    private String eventType;
    private String screen;
    private String platform;
    private Map<String, Object> metadata;
    private long timestamp;
}
