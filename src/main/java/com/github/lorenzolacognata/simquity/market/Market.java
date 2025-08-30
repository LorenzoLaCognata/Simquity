package com.github.lorenzolacognata.simquity.market;

import com.github.lorenzolacognata.simquity.asset.Asset;

import java.util.ArrayList;
import java.util.List;

public class Market {

    private final Asset asset;
    private double equilibriumPrice;
    private final List<DemandAgentAsset> demandAgentAssetList;
    private final List<SupplyAssetInventory> supplyAssetInventoryList;

    public Market(Asset asset) {
        this.asset = asset;
        this.equilibriumPrice = Double.NaN;
        this.demandAgentAssetList = new ArrayList<>();
        this.supplyAssetInventoryList = new ArrayList<>();
    }

    public Asset getAsset() {
        return asset;
    }

    public List<DemandAgentAsset> getDemandAgentAssetList() {
        return demandAgentAssetList;
    }

    public List<SupplyAssetInventory> getSupplyAssetInventoryList() {
        return supplyAssetInventoryList;
    }

    public void addDemandAgentAsset(DemandAgentAsset demandAgentAsset) {
        demandAgentAssetList.add(demandAgentAsset);
    }

    public void addSupplyAssetInventory(SupplyAssetInventory supplyAssetInventory) {
        supplyAssetInventoryList.add(supplyAssetInventory);
    }

    @Override
    public String toString() {
        return asset.toString();
    }

}