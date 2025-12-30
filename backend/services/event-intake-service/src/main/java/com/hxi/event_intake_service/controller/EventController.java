package com.hxi.event_intake_service.controller;

import com.hxi.event_intake_service.kafka.EventProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventProducer producer;

    public EventController(EventProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public String send(@RequestBody String event) {
        producer.publish("hxi-events", event);
        return "Event sent to Kafka";
    }
}
