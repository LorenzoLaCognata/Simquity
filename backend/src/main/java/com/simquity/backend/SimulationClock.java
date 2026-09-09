package com.simquity.backend;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class SimulationClock {

    private static final Long SINGLETON_ID = 1L;

    private final SimulationClockRepository repository;
    private final SimulationHistoryRepository historyRepository;

    public SimulationClock(SimulationClockRepository repository,
                            SimulationHistoryRepository historyRepository) {
        this.repository = repository;
        this.historyRepository = historyRepository;
        if (repository.findById(SINGLETON_ID).isEmpty()) {
            repository.save(new SimulationClockEntity());
        }
    }

    private SimulationClockEntity load() {
        return repository.findById(SINGLETON_ID)
                .orElseThrow(() -> new IllegalStateException("Simulation clock row is missing"));
    }

    public void play() {
        SimulationClockEntity entity = load();
        entity.setRunning(true);
        repository.save(entity);
    }

    public void stop() {
        SimulationClockEntity entity = load();
        entity.setRunning(false);
        repository.save(entity);
    }

    public boolean isRunning() {
        return load().isRunning();
    }

    public long tickIfRunning() {
        SimulationClockEntity entity = load();
        if (entity.isRunning()) {
            entity.setDay(entity.getDay() + 1);
            repository.save(entity);
            historyRepository.save(new SimulationHistory(entity.getDay(), Instant.now()));
        }
        return entity.getDay();
    }

    public long getCurrentDay() {
        return load().getDay();
    }

    public java.util.List<SimulationHistory> getHistory() {
        return historyRepository.findAllByOrderByRecordedAtAsc();
    }

}
