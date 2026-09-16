package com.simquity.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventController {

    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/api/events")
    public List<EventDto> listEvents() {
        return eventRepository.findTop200ByOrderByOccurredAtDesc()
                .stream()
                .map(EventDto::from)
                .toList();
    }

}
