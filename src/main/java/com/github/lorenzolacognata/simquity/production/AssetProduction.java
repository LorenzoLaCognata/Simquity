package com.github.lorenzolacognata.simquity.production;

import com.github.lorenzolacognata.simquity.asset.AssetRequirement;
import com.github.lorenzolacognata.simquity.labor.LaborRequirement;

import java.util.ArrayList;
import java.util.List;

public class AssetProduction {

    private final int minStartDate;
    private final int maxStartDate;
    private final int duration;
    private final double outputQuantity;
    private final List<AssetRequirement> consumableAssetRequirementList;
    private final List<AssetRequirement> durableAssetRequirementList;
    private final List<LaborRequirement> laborRequirementList;

    public AssetProduction(int minStartDate, int maxStartDate, int duration, double outputQuantity) {
        this.minStartDate = minStartDate;
        this.maxStartDate = maxStartDate;
        this.duration = duration;
        this.outputQuantity = outputQuantity;
        this.consumableAssetRequirementList = new ArrayList<>();
        this.durableAssetRequirementList = new ArrayList<>();
        this.laborRequirementList = new ArrayList<>();
    }

    public int getDuration() {
        return duration;
    }

    public double getOutputQuantity() {
        return outputQuantity;
    }

    public List<AssetRequirement> getConsumableAssetRequirementList() {
        return consumableAssetRequirementList;
    }

    public List<AssetRequirement> getDurableAssetRequirementList() {
        return durableAssetRequirementList;
    }

    public List<LaborRequirement> getLaborRequirementList() {
        return laborRequirementList;
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

    public void addLaborRequirement(LaborRequirement laborRequirement) {
        boolean alreadyExists = laborRequirementList.stream()
                .anyMatch(a -> a.getJob().equals(laborRequirement.getJob()));
        if (!alreadyExists) {
            laborRequirementList.add(laborRequirement);
        }
    }

}