package com.simquity.backend;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/simulation")
public class SimulationController {

    private final SimulationClock clock;
    private final SimulationScheduler scheduler;

    public SimulationController(SimulationClock clock, SimulationScheduler scheduler) {
        this.clock = clock;
        this.scheduler = scheduler;
    }

    @GetMapping("/state")
    public SimulationState state() {
        return new SimulationState(clock.getCurrentDay(), clock.isRunning());
    }

    @GetMapping("/history")
    public java.util.List<SimulationHistory> history() {
        return clock.getHistory();
    }

    @PostMapping("/play")
    public SimulationState play() {
        clock.play();
        return state();
    }

    @PostMapping("/stop")
    public SimulationState stop() {
        clock.stop();
        return state();
    }

    @GetMapping("/stream")
    public SseEmitter stream() {
        return scheduler.subscribe();
    }

}
