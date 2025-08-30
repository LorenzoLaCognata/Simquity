package com.github.lorenzolacognata.simquity.asset;

public class AssetRequirement {

    private final Asset asset;
    private final double initialQuantity;
    private final double recurringQuantity;
    private final double finalQuantity;

    public AssetRequirement(Asset asset, double initialQuantity, double recurringQuantity, double finalQuantity) {
        this.asset = asset;
        this.initialQuantity = initialQuantity;
        this.recurringQuantity = recurringQuantity;
        this.finalQuantity = finalQuantity;
    }

    public Asset getAsset() {
        return asset;
    }

    public double getInitialQuantity() {
        return initialQuantity;
    }

    @Override
    public String toString() {
        if (asset instanceof Good) {
            return asset + ": " + initialQuantity + " " + ((Good) asset).getUnitOfMeasure();
        }
        else {
            return asset + ": " + initialQuantity;
        }
    }

}