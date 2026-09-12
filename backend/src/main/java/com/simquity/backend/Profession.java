package com.simquity.backend;

import jakarta.persistence.*;

@Entity
public class Profession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sector sector;

    public Profession() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Sector getSector() { return sector; }
    public void setSector(Sector sector) { this.sector = sector; }
}