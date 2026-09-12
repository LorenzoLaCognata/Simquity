package com.simquity.backend;

import jakarta.persistence.*;

@Entity
public class Relationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Agent agentA;

    @ManyToOne(fetch = FetchType.LAZY)
    private Agent agentB;

    private String type;

    public Relationship() {}

    public Long getId() { return id; }
    public Agent getAgentA() { return agentA; }
    public void setAgentA(Agent agentA) { this.agentA = agentA; }
    public Agent getAgentB() { return agentB; }
    public void setAgentB(Agent agentB) { this.agentB = agentB; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
