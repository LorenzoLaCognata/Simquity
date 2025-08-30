package com.github.lorenzolacognata.simquity.inventory;

import com.github.lorenzolacognata.simquity.asset.Asset;
import com.github.lorenzolacognata.simquity.asset.Good;

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

    public double getQuantityAvailable() {
        return quantityAvailable;
    }

    public void addQuantityAvailable(double quantity) {
        this.quantityAvailable += quantity;
    }

    public void removeQuantityAvailable(double quantity) {
        this.quantityAvailable -= quantity;
    }

    public void useQuantity(double quantity) {
        this.quantityInUse += quantity;
        this.quantityAvailable -= quantity;
    }

    @Override
    public String toString() {
        if (asset instanceof Good) {
            return asset + ": " + quantityInUse + " " + ((Good) asset).getUnitOfMeasure();
        }
        else {
            return asset + ": " + quantityInUse;
        }
    }

}