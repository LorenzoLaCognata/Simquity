package com.simquity.backend;

import jakarta.persistence.*;

@Entity
public class Guild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    private Profession profession;

    public Guild() {}

    public Long getId() { return id; }
    public Organization getOrganization() { return organization; }
    public void setOrganization(Organization organization) { this.organization = organization; }
    public Profession getProfession() { return profession; }
    public void setProfession(Profession profession) { this.profession = profession; }
}
