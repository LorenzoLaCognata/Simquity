package com.github.lorenzolacognata.simquity.production;

import com.github.lorenzolacognata.simquity.asset.Asset;
import com.github.lorenzolacognata.simquity.asset.Good;
import com.github.lorenzolacognata.simquity.inventory.AssetInventory;

public class ProductionInventory {

    private final AssetInventory assetInventory;
    private final double quantity;

    public ProductionInventory(AssetInventory assetInventory, double quantity) {
        this.assetInventory = assetInventory;
        this.quantity = quantity;
    }

    public AssetInventory getAssetInventory() {
        return assetInventory;
    }

    public double getQuantity() {
        return quantity;
    }

}