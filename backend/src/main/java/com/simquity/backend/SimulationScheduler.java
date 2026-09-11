package com.simquity.backend;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SimulationScheduler {

    private final SimulationClock clock;

    private final List<SseEmitter> subscribers = new CopyOnWriteArrayList<>();

    public SimulationScheduler(SimulationClock clock) {
        this.clock = clock;
    }

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        subscribers.add(emitter);
        emitter.onCompletion(() -> subscribers.remove(emitter));
        emitter.onTimeout(() -> subscribers.remove(emitter));
        return emitter;
    }

    @Scheduled(fixedRate = 1000)
    public void tick() {
        long day = clock.tickIfRunning();
        for (SseEmitter emitter : subscribers) {
            try {
                emitter.send(SseEmitter.event().data(new SimulationState(day, clock.isRunning())));
            } catch (IOException e) {
                subscribers.remove(emitter);
            }
        }
    }

}
