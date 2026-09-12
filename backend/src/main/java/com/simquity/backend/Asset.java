package com.simquity.backend;

import jakarta.persistence.*;


@Entity
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private double quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Agent ownerAgent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organization ownerOrganization;

    public Asset() {}

    public Long getId() { return id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
    public Agent getOwnerAgent() { return ownerAgent; }
    public void setOwnerAgent(Agent ownerAgent) { this.ownerAgent = ownerAgent; }
    public Organization getOwnerOrganization() { return ownerOrganization; }
    public void setOwnerOrganization(Organization ownerOrganization) { this.ownerOrganization = ownerOrganization; }
}