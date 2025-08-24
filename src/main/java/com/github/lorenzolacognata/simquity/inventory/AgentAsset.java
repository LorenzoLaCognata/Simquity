package com.github.lorenzolacognata.simquity.inventory;

import com.github.lorenzolacognata.simquity.asset.Asset;

import java.util.ArrayList;
import java.util.List;

public class AgentAsset {

    private final Asset asset;
    private final List<AssetInventory> assetInventoryList;
    private double lastPrice;

    public AgentAsset(Asset asset) {
        this.asset = asset;
        this.assetInventoryList = new ArrayList<>();
        assetInventoryList.add(new AssetInventory(asset));
        this.lastPrice = Double.NaN;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    @Override
    public String toString() {
        return "AgentAsset{" + asset + ": " + assetInventoryList + "}";
    }

}