package com.simquity.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SimulationHistoryRepository extends JpaRepository<SimulationHistory, Long> {

    List<SimulationHistory> findAllByOrderByRecordedAtAsc();

}
