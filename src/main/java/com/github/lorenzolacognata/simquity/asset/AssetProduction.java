package com.github.lorenzolacognata.simquity.asset;

import com.github.lorenzolacognata.simquity.inventory.AgentAsset;

import java.util.ArrayList;
import java.util.List;

public class AssetProduction {

    private final int minStartDate;
    private final int maxStartDate;
    private final int duration;
    private final double outputQuantity;
    private final List<AssetRequirement> consumableAssetRequirementList;
    private final List<AssetRequirement> durableAssetRequirementList;
    // laborQuantityList

    public AssetProduction(int minStartDate, int maxStartDate, int duration, double outputQuantity) {
        this.minStartDate = minStartDate;
        this.maxStartDate = maxStartDate;
        this.duration = duration;
        this.outputQuantity = outputQuantity;
        this.consumableAssetRequirementList = new ArrayList<>();
        this.durableAssetRequirementList = new ArrayList<>();
    }

    public List<AssetRequirement> getConsumableAssetRequirementList() {
        return consumableAssetRequirementList;
    }

    public List<AssetRequirement> getDurableAssetRequirementList() {
        return durableAssetRequirementList;
    }

    @Override
    public String toString() {
        return "AssetProduction{'" + duration + " weeks'}";
    }

    public void addAssetRequirement(AssetRequirement assetRequirement, List<AssetRequirement> assetRequirementList) {
        boolean alreadyExists = assetRequirementList.stream()
                .anyMatch(a -> a.getAsset().equals(assetRequirement.getAsset()));
        if (!alreadyExists) {
            assetRequirementList.add(assetRequirement);
        }
    }

    public void addConsumableAssetRequirement(AssetRequirement assetRequirement) {
        addAssetRequirement(assetRequirement, consumableAssetRequirementList);
    }


    public void addDurableAssetRequirement(AssetRequirement assetRequirement) {
        addAssetRequirement(assetRequirement, durableAssetRequirementList);
    }

}