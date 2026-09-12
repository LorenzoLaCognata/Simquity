package com.simquity.backend;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Agent agent;

    @ManyToOne(fetch = FetchType.LAZY)
    private ActivityType activityType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Profession profession;

    private LocalDate startDate;
    private String status;

    public Activity() {}

    public Long getId() { return id; }
    public Agent getAgent() { return agent; }
    public void setAgent(Agent agent) { this.agent = agent; }
    public ActivityType getActivityType() { return activityType; }
    public void setActivityType(ActivityType activityType) { this.activityType = activityType; }
    public Profession getProfession() { return profession; }
    public void setProfession(Profession profession) { this.profession = profession; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}