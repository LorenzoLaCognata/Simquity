package com.github.lorenzolacognata.simquity.market;

import com.github.lorenzolacognata.simquity.inventory.AgentAsset;
import com.github.lorenzolacognata.simquity.inventory.AssetInventory;

public class SupplyAssetInventory {

    private final AgentAsset agentAsset;
    private final AssetInventory assetInventory;
    private final double quantity;
    private final double marginalCost;
    private double quantityLeft;

    public SupplyAssetInventory(AgentAsset agentAsset, AssetInventory assetInventory, double quantity, double marginalCost) {
        this.agentAsset = agentAsset;
        this.assetInventory = assetInventory;
        this.quantity = quantity;
        this.marginalCost = marginalCost;
        this.quantityLeft = quantity;
    }

    public AgentAsset getAgentAsset() {
        return agentAsset;
    }

    public AssetInventory getAssetInventory() {
        return assetInventory;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getMarginalCost() {
        return marginalCost;
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