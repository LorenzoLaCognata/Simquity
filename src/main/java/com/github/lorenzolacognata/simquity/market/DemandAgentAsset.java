package com.github.lorenzolacognata.simquity.market;

import com.github.lorenzolacognata.simquity.inventory.AgentAsset;

public class DemandAgentAsset {

    private final AgentAsset agentAsset;
    private double quantity;
    private double maximumPrice;

    public DemandAgentAsset(AgentAsset agentAsset, double quantity, double maximumPrice) {
        this.agentAsset = agentAsset;
        this.quantity = quantity;
        this.maximumPrice = maximumPrice;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getMaximumPrice() {
        return maximumPrice;
    }

    @Override
    public String toString() {
        return agentAsset.getAgent().toString();
    }

}