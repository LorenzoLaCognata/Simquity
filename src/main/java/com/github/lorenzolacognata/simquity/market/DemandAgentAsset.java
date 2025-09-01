package com.github.lorenzolacognata.simquity.market;

import com.github.lorenzolacognata.simquity.inventory.AgentAsset;

public class DemandAgentAsset {

    private final AgentAsset agentAsset;
    private final double quantity;
    private final double maximumPrice;
    private double quantityLeft;

    public DemandAgentAsset(AgentAsset agentAsset, double quantity, double maximumPrice) {
        this.agentAsset = agentAsset;
        this.quantity = quantity;
        this.maximumPrice = maximumPrice;
        this.quantityLeft = quantity;
    }

    public AgentAsset getAgentAsset() {
        return agentAsset;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getMaximumPrice() {
        return maximumPrice;
    }

    public double getQuantityLeft() {
        return quantityLeft;
    }

    public void removeQuantityLeft(double quantity) {
        this.quantityLeft -= quantity;
    }

    public double getQuantityTraded() {
        return quantity - quantityLeft;
    }

    @Override
    public String toString() {
        return agentAsset.getAgent().toString();
    }

}