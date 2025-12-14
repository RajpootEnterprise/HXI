package com.hxi.event_intake_service.controller;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventIngestionService service;

    public EventController(EventIngestionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> ingest(@Valid @RequestBody EventRequest request) {
        service.ingest(request);
        return ResponseEntity.accepted().build();
    }
}

