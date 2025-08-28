package com.github.lorenzolacognata.simquity.inventory;

import com.github.lorenzolacognata.simquity.asset.Asset;

public class AssetInventory {

    private final Asset asset;
    private double quantityAvailable;
    private double quantityInUse;
    private double sunkCost;
    private double lifeRemaining;

    public AssetInventory(Asset asset) {
        this.asset = asset;
        this.quantityAvailable = 0.0;
        this.quantityInUse = 0.0;
        this.sunkCost = 0.0;
        this.lifeRemaining = asset.getLifespan();
    }

    public Asset getAsset() {
        return asset;
    }

    @Override
    public String toString() {
        return "AssetInventory{" + quantityAvailable + "}";
    }

}