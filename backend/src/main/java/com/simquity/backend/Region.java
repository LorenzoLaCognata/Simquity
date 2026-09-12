package com.simquity.backend;

import jakarta.persistence.*;

@Entity
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Region parentRegion;

    public Region() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Region getParentRegion() { return parentRegion; }
    public void setParentRegion(Region parentRegion) { this.parentRegion = parentRegion; }
}