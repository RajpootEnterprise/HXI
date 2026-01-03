package com.hxi.event_intake_service.util;

import com.hxi.event_intake_service.dto.EventRequestDTO;
import com.hxi.event_intake_service.exception.InvalidEventException;

public class EventValidator {

    public static void validate(EventRequestDTO dto) {

        if (dto.getSessionId() == null || dto.getSessionId().isBlank()) {
            throw new InvalidEventException("SessionId missing");
        }

        if (dto.getEventType() == null) {
            throw new InvalidEventException("EventType missing");
        }
    }
}
