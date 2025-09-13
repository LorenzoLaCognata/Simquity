package com.github.lorenzolacognata.simquity.inventory;

import com.github.lorenzolacognata.simquity.agent.Agent;
import com.github.lorenzolacognata.simquity.asset.Asset;
import com.github.lorenzolacognata.simquity.asset.ProductionStatus;
import com.github.lorenzolacognata.simquity.production.ProductionLine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AgentAsset {

    private final Agent agent;
    private final Asset asset;
    private double lastPrice;
    private final List<AssetInventory> assetInventoryList;
    private final List<ProductionLine> productionLineList;

    public AgentAsset(Agent agent, Asset asset) {
        this.agent = agent;
        this.asset = asset;
        this.lastPrice = Double.NaN;
        this.assetInventoryList = new ArrayList<>();
        this.productionLineList = new ArrayList<>();
    }

    public Agent getAgent() {
        return agent;
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

    public List<AssetInventory> getAssetInventoryList(Asset asset) {
        return assetInventoryList.stream()
                .filter(a -> a.getAsset().equals(asset))
                .toList();
    }

    public void addAssetInventory(double quantity) {
        assetInventoryList.add(new AssetInventory(asset, quantity, Double.NaN));
    }

    public void addAssetInventory(double quantity, double marginalCost) {
        assetInventoryList.add(new AssetInventory(asset, quantity, marginalCost));
    }

    public List<ProductionLine> getProductionLineList() {
        return productionLineList;
    }

    public void addProductionLine(ProductionLine productionLine) {
        productionLineList.add(productionLine);
    }

    public void produceAll() {
        Iterator<ProductionLine> productionLineIterator = productionLineList.iterator();

        while (productionLineIterator.hasNext()) {
            ProductionLine productionLine = productionLineIterator.next();
            productionLine.produce();
            if (productionLine.getProductionStatus() == ProductionStatus.COMPLETE || productionLine.getProductionStatus() == ProductionStatus.ABORTED) {
                productionLineIterator.remove();
            }
        }
    }

    @Override
    public String toString() {
        return asset.toString();
    }

}