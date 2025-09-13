package com.github.lorenzolacognata.simquity.asset;

public class Currency extends Asset {

    public Currency(String name) {
        super(name, Double.POSITIVE_INFINITY, Double.NaN);
    }

}
