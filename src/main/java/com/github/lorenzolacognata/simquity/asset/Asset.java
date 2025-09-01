package com.github.lorenzolacognata.simquity.asset;

import com.github.lorenzolacognata.simquity.production.AssetProduction;

import java.util.ArrayList;
import java.util.List;

public abstract class Asset {

    private final String name;
    private final double lifespan;
    private final List<AssetProduction> assetProductionList;

    public Asset(String name, double lifespan) {
        this.name = name;
        this.lifespan = lifespan;
        this.assetProductionList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public double getLifespan() {
        return lifespan;
    }

    public List<AssetProduction> getAssetProductionList() {
        return assetProductionList;
    }

    public void addAssetProductionList(AssetProduction assetProduction) {
        if (!assetProductionList.contains(assetProduction)) {
            assetProductionList.add(assetProduction);
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " - " + name;
    }
}
