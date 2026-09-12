package com.simquity.backend;

import jakarta.persistence.*;

@Entity
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization;

    private String curriculum;
    private double teachingEffectiveness;

    public School() {}

    public Long getId() { return id; }
    public Organization getOrganization() { return organization; }
    public void setOrganization(Organization organization) { this.organization = organization; }
    public String getCurriculum() { return curriculum; }
    public void setCurriculum(String curriculum) { this.curriculum = curriculum; }
    public double getTeachingEffectiveness() { return teachingEffectiveness; }
    public void setTeachingEffectiveness(double teachingEffectiveness) { this.teachingEffectiveness = teachingEffectiveness; }
}