package com.simquity.backend;

import java.time.Instant;

public record EventDto(Long id, String eventType, String description, Instant occurredAt) {

    public static EventDto from(Event event) {
        return new EventDto(
                event.getId(),
                event.getEventType() != null ? event.getEventType().getName() : null,
                event.getDescription(),
                event.getOccurredAt()
        );
    }

}
