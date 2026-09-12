package com.simquity.backend;

import jakarta.persistence.*;
import java.time.LocalDate;

// A standing wage relationship between an agent and an organization -
// distinct from a one-off Transaction, since this is ongoing.
@Entity
public class Employment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Agent agent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization;

    private double wage;

    @ManyToOne(fetch = FetchType.LAZY)
    private Currency currency;

    private LocalDate startDate;

    public Employment() {}

    public Long getId() { return id; }
    public Agent getAgent() { return agent; }
    public void setAgent(Agent agent) { this.agent = agent; }
    public Organization getOrganization() { return organization; }
    public void setOrganization(Organization organization) { this.organization = organization; }
    public double getWage() { return wage; }
    public void setWage(double wage) { this.wage = wage; }
    public Currency getCurrency() { return currency; }
    public void setCurrency(Currency currency) { this.currency = currency; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
}
