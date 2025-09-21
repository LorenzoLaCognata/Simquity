package com.github.lorenzolacognata.simquity.asset;

public class Currency extends Asset {

    public Currency(AssetType assetType) {
        super(assetType, Double.POSITIVE_INFINITY, Double.NaN, Double.NaN);
    }

}
