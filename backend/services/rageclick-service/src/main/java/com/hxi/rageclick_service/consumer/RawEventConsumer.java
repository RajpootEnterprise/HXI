package com.hxi.rageclick_service.consumer;

import com.hxi.rageclick_service.model.RawEvent;
import com.hxi.rageclick_service.service.RageClickDetectionService;
import org.springframework.stereotype.Component;

@Component
public class RawEventConsumer {

    private final RageClickDetectionService detectionService;

    public RawEventConsumer(RageClickDetectionService detectionService) {
        this.detectionService = detectionService;
    }

    @KafkaListener(
            topics = "ux.events.raw",
            groupId = "rage-click-service"
    )
    public void consume(RawEvent event) {

        if (!"CLICK".equals(event.getEventType())) {
            return; // ignore non-clicks
        }

        detectionService.process(event);
    }
}
