package com.simquity.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class ActivityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String behaviorClass;

    public ActivityType() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBehaviorClass() { return behaviorClass; }
    public void setBehaviorClass(String behaviorClass) { this.behaviorClass = behaviorClass; }
}