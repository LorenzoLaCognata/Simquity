package com.github.lorenzolacognata.simquity.inventory;

import com.github.lorenzolacognata.simquity.asset.Asset;
import com.github.lorenzolacognata.simquity.asset.AssetProduction;
import com.github.lorenzolacognata.simquity.asset.AssetRequirement;
import com.github.lorenzolacognata.simquity.asset.ProductionStatus;
import com.github.lorenzolacognata.simquity.labor.Employment;
import com.github.lorenzolacognata.simquity.labor.LaborRequirement;

import java.util.ArrayList;
import java.util.List;

public class ProductionLine {

    private final AssetProduction assetProduction;
    private final int startDate;
    private ProductionStatus productionStatus;
    private int currentDuration;
    private double outputQuantity;
    private final List<AssetInventory> consumableAssetInventoryList;
    private final List<AssetInventory> durableAssetInventoryList;
    private final List<Employment> employmentList;

    public ProductionLine(AssetProduction assetProduction, int startDate) {
        this.assetProduction = assetProduction;
        this.startDate = startDate;
        this.productionStatus = ProductionStatus.NOT_STARTED;
        this.currentDuration = 0;
        this.outputQuantity = 0.0;
        this.consumableAssetInventoryList = new ArrayList<>();
        this.durableAssetInventoryList = new ArrayList<>();
        this.employmentList = new ArrayList<>();
    }

    public List<AssetInventory> getConsumableAssetInventoryList() {
        return consumableAssetInventoryList;
    }

    public List<AssetInventory> getDurableAssetInventoryList() {
        return durableAssetInventoryList;
    }

    public List<Employment> getEmploymentList() {
        return employmentList;
    }

    public void addAssetInventory(AssetInventory assetInventory, List<AssetInventory> assetInventoryList) {
        boolean alreadyExists = assetInventoryList.stream()
                .anyMatch(a -> a.getAsset().equals(assetInventory.getAsset()));
        if (!alreadyExists) {
            assetInventoryList.add(assetInventory);
        }
    }

    public void addConsumableAssetInventory(AssetInventory assetInventory) {
        addAssetInventory(assetInventory, consumableAssetInventoryList);
    }


    public void addDurableAssetInventory(AssetInventory assetInventory) {
        addAssetInventory(assetInventory, durableAssetInventoryList);
    }

    public void addEmployment(Employment employment) {
        boolean alreadyExists = employmentList.stream()
                .anyMatch(a -> a.getJob().equals(employment.getJob()));
        if (!alreadyExists) {
            employmentList.add(employment);
        }
    }

    @Override
    public String toString() {
        return "ProductionLine{" + productionStatus + "}";
    }

}