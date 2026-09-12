package com.simquity.backend;

import jakarta.persistence.*;

@Entity
public class Government {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    private Region region;

    public Government() {}

    public Long getId() { return id; }
    public Organization getOrganization() { return organization; }
    public void setOrganization(Organization organization) { this.organization = organization; }
    public Region getRegion() { return region; }
    public void setRegion(Region region) { this.region = region; }
}