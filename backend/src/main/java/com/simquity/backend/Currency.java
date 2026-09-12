package com.simquity.backend;

import jakarta.persistence.*;

@Entity
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double exchangeRateToBase;

    @ManyToOne(fetch = FetchType.LAZY)
    private Region region;

    public Currency() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getExchangeRateToBase() { return exchangeRateToBase; }
    public void setExchangeRateToBase(double exchangeRateToBase) { this.exchangeRateToBase = exchangeRateToBase; }
    public Region getRegion() { return region; }
    public void setRegion(Region region) { this.region = region; }
}
