package com.simquity.backend;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class EventPublisher {

    private final EventRepository eventRepository;

    public EventPublisher(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void publish(EventType eventType, String description) {
        Event event = new Event();
        event.setEventType(eventType);
        event.setDescription(description);
        event.setOccurredAt(Instant.now());
        eventRepository.save(event);
    }

}
