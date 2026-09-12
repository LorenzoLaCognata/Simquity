package com.simquity.backend;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Asset asset;

    @ManyToOne(fetch = FetchType.LAZY)
    private Agent fromAgent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organization fromOrganization;

    @ManyToOne(fetch = FetchType.LAZY)
    private Agent toAgent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organization toOrganization;

    private double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Currency currency;

    private Instant occurredAt;

    public Transaction() {}

    public Long getId() { return id; }
    public Asset getAsset() { return asset; }
    public void setAsset(Asset asset) { this.asset = asset; }
    public Agent getFromAgent() { return fromAgent; }
    public void setFromAgent(Agent fromAgent) { this.fromAgent = fromAgent; }
    public Organization getFromOrganization() { return fromOrganization; }
    public void setFromOrganization(Organization fromOrganization) { this.fromOrganization = fromOrganization; }
    public Agent getToAgent() { return toAgent; }
    public void setToAgent(Agent toAgent) { this.toAgent = toAgent; }
    public Organization getToOrganization() { return toOrganization; }
    public void setToOrganization(Organization toOrganization) { this.toOrganization = toOrganization; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public Currency getCurrency() { return currency; }
    public void setCurrency(Currency currency) { this.currency = currency; }
    public Instant getOccurredAt() { return occurredAt; }
    public void setOccurredAt(Instant occurredAt) { this.occurredAt = occurredAt; }
}