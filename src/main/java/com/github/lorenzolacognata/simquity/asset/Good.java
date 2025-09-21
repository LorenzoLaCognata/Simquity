package com.github.lorenzolacognata.simquity.asset;

public class Good extends Asset {

    private final UnitOfMeasure unitOfMeasure;

    public Good(AssetType assetType, double lifespan, double referencePrice, double targetGrossMargin, UnitOfMeasure unitOfMeasure) {
        super(assetType, lifespan, referencePrice, targetGrossMargin);
        this.unitOfMeasure = unitOfMeasure;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

}