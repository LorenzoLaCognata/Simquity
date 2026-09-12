package com.simquity.backend;

import org.springframework.stereotype.Component;

// Routes an Event to whichever agents/organizations it affects, once
// there's real subscriber logic to route to. Currently just persists
// the event - the actual "who gets notified" behavior is future work,
// built alongside the first ActivityType that needs to raise events.
@Component
public class EventPublisher {

    public void publish(Event event) {
        // Intentionally empty for now - wiring this up is part of
        // building the first real ActivityType, not this skeleton pass.
    }

}
