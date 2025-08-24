package com.github.lorenzolacognata.simquity.inventory;

import com.github.lorenzolacognata.simquity.asset.Asset;

public class AssetInventory {

    private double quantityAvailable;
    private double quantityInUse;
    private double sunkCost;
    private double lifeRemaining;

    public AssetInventory(Asset asset) {
        this.quantityAvailable = 0.0;
        this.quantityInUse = 0.0;
        this.sunkCost = 0.0;
        this.lifeRemaining = asset.getLifespan();
    }

    @Override
    public String toString() {
        return "AssetInventory{" + quantityAvailable + "}";
    }

}