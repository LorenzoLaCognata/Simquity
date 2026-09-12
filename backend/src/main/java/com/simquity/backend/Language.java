package com.simquity.backend;

import jakarta.persistence.*;

@Entity
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Region primaryRegion;

    public Language() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Region getPrimaryRegion() { return primaryRegion; }
    public void setPrimaryRegion(Region primaryRegion) { this.primaryRegion = primaryRegion; }
}
