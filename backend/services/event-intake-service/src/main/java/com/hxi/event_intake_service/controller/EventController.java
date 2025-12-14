package com.hxi.event_intake_service.controller;

import com.hxi.event_intake_service.dto.EventRequest;
import com.hxi.event_intake_service.service.EventIngestionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventIngestionService ingestionService;

    public EventController(EventIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @PostMapping
    public ResponseEntity<String> ingestEvent(
            @Valid @RequestBody EventRequest request
    ) {
        ingestionService.ingest(request);
        return ResponseEntity.ok("Event ingested successfully");
    }
}
