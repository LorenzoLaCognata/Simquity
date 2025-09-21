package com.github.lorenzolacognata.simquity.asset;

import com.github.lorenzolacognata.simquity.market.Market;
import com.github.lorenzolacognata.simquity.production.AssetProduction;

import java.util.ArrayList;
import java.util.List;

public abstract class Asset {

    private final AssetType assetType;
    private final Market market;
    private final double lifespan;
    private final double referencePrice;
    private final double targetGrossMargin;
    private final List<AssetProduction> assetProductionList;

    public Asset(AssetType assetType, double lifespan, double referencePrice, double targetGrossMargin) {
        this.assetType = assetType;
        this.market = new Market();
        this.lifespan = lifespan;
        this.referencePrice = referencePrice;
        this.targetGrossMargin = targetGrossMargin;
        this.assetProductionList = new ArrayList<>();
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public Market getMarket() {
        return market;
    }

    public double getLifespan() {
        return lifespan;
    }

    public double getReferencePrice() {
        return referencePrice;
    }

    public double getTargetGrossMargin() {
        return targetGrossMargin;
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
        return assetType.toString();
    }
}
