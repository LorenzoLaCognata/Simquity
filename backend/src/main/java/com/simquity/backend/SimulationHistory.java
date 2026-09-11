package com.simquity.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Instant;

@Entity
public class SimulationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long day;

    private Instant recordedAt;

    public SimulationHistory() {
    }

    public SimulationHistory(long day, Instant recordedAt) {
        this.day = day;
        this.recordedAt = recordedAt;
    }

    public Long getId() {
        return id;
    }

    public long getDay() {
        return day;
    }

    public Instant getRecordedAt() {
        return recordedAt;
    }

}
