package com.simquity.backend;

import org.springframework.stereotype.Component;

// Computes the dashboard rollups (population by sector, trade volume,
// money moved) from the raw tables - the same role SimulationHistory
// plays for the day counter, generalized to the full data model once
// there's real data to aggregate.
@Component
public class WorldSnapshotAggregator {

    public void computeSnapshot() {
        // Intentionally empty for now - there's nothing to aggregate
        // yet. Fill this in once agents/activities/transactions exist.
    }

}
