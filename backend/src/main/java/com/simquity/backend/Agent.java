package com.simquity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate birthDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Region region;

    public Agent() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public Region getRegion() { return region; }
    public void setRegion(Region region) { this.region = region; }
}