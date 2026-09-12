package com.simquity.backend;

import jakarta.persistence.*;

@Entity
public class Factory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization;

    private double productionCapacity;

    public Factory() {}

    public Long getId() { return id; }
    public Organization getOrganization() { return organization; }
    public void setOrganization(Organization organization) { this.organization = organization; }
    public double getProductionCapacity() { return productionCapacity; }
    public void setProductionCapacity(double productionCapacity) { this.productionCapacity = productionCapacity; }
}