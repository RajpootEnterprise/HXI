package com.hxi.event_intake_service.controller;

import com.hxi.event_intake_service.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<String> publishEvent(@RequestBody Map<String, Object> payload) {
        eventService.processEvent(payload);
        return ResponseEntity.ok("Event received and processed");
    }
}
