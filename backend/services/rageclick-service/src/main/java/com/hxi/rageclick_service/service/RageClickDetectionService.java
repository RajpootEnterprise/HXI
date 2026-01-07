package com.hxi.rageclick_service.service;

import com.hxi.rageclick_service.model.RageClickEvent;
import com.hxi.rageclick_service.model.RawEvent;
import com.hxi.rageclick_service.repository.RageClickRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RageClickDetectionService {

    private static final int RAGE_THRESHOLD = 3;
    private static final long TIME_WINDOW_MS = 1000;

    private final RageClickRepository repository;

    // in-memory sliding window
    private final Map<String, List<RawEvent>> clickBuffer = new ConcurrentHashMap<>();

    public RageClickDetectionService(RageClickRepository repository) {
        this.repository = repository;
    }

    public void process(RawEvent event) {

        String key = event.getSessionId() + "|" +
                event.getScreen() + "|" +
                event.getMetadata().get("elementId");

        clickBuffer.putIfAbsent(key, new ArrayList<>());
        List<RawEvent> events = clickBuffer.get(key);

        events.add(event);

        long now = event.getTimestamp();

        // remove old clicks
        events.removeIf(e -> now - e.getTimestamp() > TIME_WINDOW_MS);

        if (events.size() >= RAGE_THRESHOLD) {
            emitRageClick(event, events.size());
            events.clear(); // avoid duplicate rage events
        }
    }

    private void emitRageClick(RawEvent event, int count) {

        RageClickEvent rage = new RageClickEvent();
        rage.setSessionId(event.getSessionId());
        rage.setUserId(event.getUserId());
        rage.setScreen(event.getScreen());
        rage.setElementId(
                String.valueOf(event.getMetadata().get("elementId"))
        );

        rage.setClickCount(count);
        rage.setTimeWindowMs(TIME_WINDOW_MS);
        rage.setSeverity(count >= 5 ? "HIGH" : "MEDIUM");
        rage.setDetectedAt(System.currentTimeMillis());

        repository.save(rage);
    }
}
