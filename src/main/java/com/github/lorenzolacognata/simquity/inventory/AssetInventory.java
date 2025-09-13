package com.github.lorenzolacognata.simquity.inventory;

import com.github.lorenzolacognata.simquity.asset.Asset;
import com.github.lorenzolacognata.simquity.asset.Good;

public class AssetInventory {

    private final Asset asset;
    private double quantity;
    private double marginalCost;
    private double sunkCost;
    private double lifeRemaining;

    public AssetInventory(Asset asset, double quantity, double marginalCost) {
        this.asset = asset;
        this.quantity = quantity;
        this.marginalCost = marginalCost;
        this.sunkCost = 0.0;
        this.lifeRemaining = asset.getLifespan();
    }

    public Asset getAsset() {
        return asset;
    }

    public double getQuantity() {
        return quantity;
    }

    public void addQuantity(double quantity) {
        this.quantity += quantity;
    }

    public double getMarginalCost() {
        return marginalCost;
    }

    public void setMarginalCost(double marginalCost) {
        this.marginalCost = marginalCost;
    }

    public double getLifeRemaining() {
        return lifeRemaining;
    }

    @Override
    public String toString() {
        if (asset instanceof Good) {
            return asset + ": " + quantity + " " + ((Good) asset).getUnitOfMeasure();
        }
        else {
            return asset + ": " + quantity;
        }
    }

}