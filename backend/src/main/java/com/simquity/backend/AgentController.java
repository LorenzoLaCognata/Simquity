package com.simquity.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AgentController {

    private final AgentRepository agentRepository;

    public AgentController(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @GetMapping("/api/agents")
    public List<AgentDto> listAgents() {
        return agentRepository.findAll()
                .stream()
                .map(AgentDto::from)
                .toList();
    }

}