package com.simquity.backend;

import jakarta.persistence.*;

@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Region regionA;

    @ManyToOne(fetch = FetchType.LAZY)
    private Region regionB;

    private double distance;
    private double cost;
    private double travelTime;

    public Route() {}

    public Long getId() { return id; }
    public Region getRegionA() { return regionA; }
    public void setRegionA(Region regionA) { this.regionA = regionA; }
    public Region getRegionB() { return regionB; }
    public void setRegionB(Region regionB) { this.regionB = regionB; }
    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }
    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }
    public double getTravelTime() { return travelTime; }
    public void setTravelTime(double travelTime) { this.travelTime = travelTime; }
}
