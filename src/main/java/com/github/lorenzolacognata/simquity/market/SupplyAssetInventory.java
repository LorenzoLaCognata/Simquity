package com.github.lorenzolacognata.simquity.market;

import com.github.lorenzolacognata.simquity.inventory.AgentAsset;
import com.github.lorenzolacognata.simquity.inventory.AssetInventory;

public class SupplyAssetInventory {

    private final AgentAsset agentAsset;
    private final AssetInventory assetInventory;
    private double quantity;
    private double marginalCost;

    public SupplyAssetInventory(AgentAsset agentAsset, AssetInventory assetInventory, double quantity, double marginalCost) {
        this.agentAsset = agentAsset;
        this.assetInventory = assetInventory;
        this.quantity = quantity;
        this.marginalCost = marginalCost;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getMarginalCost() {
        return marginalCost;
    }

    @Override
    public String toString() {
        return agentAsset.getAgent().toString();
    }

}