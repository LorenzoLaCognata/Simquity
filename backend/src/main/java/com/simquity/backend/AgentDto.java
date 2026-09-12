package com.simquity.backend;

import java.time.LocalDate;

public record AgentDto(Long id, String name, LocalDate birthDate, RegionSummaryDto region) {

    public static AgentDto from(Agent agent) {
        return new AgentDto(
                agent.getId(),
                agent.getName(),
                agent.getBirthDate(),
                RegionSummaryDto.from(agent.getRegion())
        );
    }

}
