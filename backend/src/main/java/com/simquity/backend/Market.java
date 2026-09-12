package com.simquity.backend;

import jakarta.persistence.*;

@Entity
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sector sector;

    public Market() {}

    public Long getId() { return id; }
    public Region getRegion() { return region; }
    public void setRegion(Region region) { this.region = region; }
    public Sector getSector() { return sector; }
    public void setSector(Sector sector) { this.sector = sector; }
}