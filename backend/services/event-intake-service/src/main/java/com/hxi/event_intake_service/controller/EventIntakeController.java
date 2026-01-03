package com.hxi.event_intake_service.controller;

import com.hxi.event_intake_service.dto.EventRequestDTO;
import com.hxi.event_intake_service.dto.EventResponseDTO;
import com.hxi.event_intake_service.service.EventIngestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventIntakeController {

    private final EventIngestionService ingestionService;

    public EventIntakeController(EventIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @PostMapping
    public ResponseEntity<EventResponseDTO> ingest(
            @RequestBody EventRequestDTO dto) {

        ingestionService.ingest(dto);

        return ResponseEntity.ok(
                new EventResponseDTO("SUCCESS", "Event accepted")
        );
    }
}
