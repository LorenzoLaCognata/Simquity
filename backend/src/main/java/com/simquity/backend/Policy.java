package com.simquity.backend;

import jakarta.persistence.*;

@Entity
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Government government;

    private double taxRate;
    private String tradeRestriction;

    public Policy() {}

    public Long getId() { return id; }
    public Government getGovernment() { return government; }
    public void setGovernment(Government government) { this.government = government; }
    public double getTaxRate() { return taxRate; }
    public void setTaxRate(double taxRate) { this.taxRate = taxRate; }
    public String getTradeRestriction() { return tradeRestriction; }
    public void setTradeRestriction(String tradeRestriction) { this.tradeRestriction = tradeRestriction; }
}