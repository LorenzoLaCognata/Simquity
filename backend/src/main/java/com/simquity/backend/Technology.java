package com.simquity.backend;

import jakarta.persistence.*;

@Entity
public class Technology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Technology prerequisiteTechnology;

    public Technology() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Technology getPrerequisiteTechnology() { return prerequisiteTechnology; }
    public void setPrerequisiteTechnology(Technology prerequisiteTechnology) { this.prerequisiteTechnology = prerequisiteTechnology; }
}