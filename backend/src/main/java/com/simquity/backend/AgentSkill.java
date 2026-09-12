package com.simquity.backend;

import jakarta.persistence.*;

@Entity
public class AgentSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Agent agent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Skill skill;

    private double level;

    public AgentSkill() {}

    public Long getId() { return id; }
    public Agent getAgent() { return agent; }
    public void setAgent(Agent agent) { this.agent = agent; }
    public Skill getSkill() { return skill; }
    public void setSkill(Skill skill) { this.skill = skill; }
    public double getLevel() { return level; }
    public void setLevel(double level) { this.level = level; }
}