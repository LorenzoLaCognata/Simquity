package com.github.lorenzolacognata.simquity.inventory;

import com.github.lorenzolacognata.simquity.asset.Asset;
import com.github.lorenzolacognata.simquity.asset.ProductionStatus;
import com.github.lorenzolacognata.simquity.labor.Employment;

import java.util.ArrayList;
import java.util.List;

public class AgentAsset {

    private final Asset asset;
    private double lastPrice;
    private final List<AssetInventory> assetInventoryList;
    private final List<ProductionLine> productionLineList;

    public AgentAsset(Asset asset) {
        this.asset = asset;
        this.lastPrice = Double.NaN;
        this.assetInventoryList = new ArrayList<>();
        assetInventoryList.add(new AssetInventory(asset));
        this.productionLineList = new ArrayList<>();
    }

    public Asset getAsset() {
        return asset;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public List<AssetInventory> getAssetInventoryList() {
        return assetInventoryList;
    }

    public List<ProductionLine> getProductionLineList() {
        return productionLineList;
    }

    public void addProductionLine(ProductionLine productionLine) {
        productionLineList.add(productionLine);
    }

    @Override
    public String toString() {
        return "AgentAsset{" + asset + ": " + assetInventoryList + "}";
    }

}