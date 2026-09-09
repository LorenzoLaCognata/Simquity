package com.simquity.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SimulationClockEntity {

    @Id
    private Long id = 1L;

    private long day = 0;

    private boolean running = false;

    public SimulationClockEntity() {
    }

    public Long getId() {
        return id;
    }

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}
