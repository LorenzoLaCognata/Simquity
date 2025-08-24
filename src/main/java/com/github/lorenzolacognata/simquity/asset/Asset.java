package com.github.lorenzolacognata.simquity.asset;

public abstract class Asset {

    private final String name;
    private final double lifespan;

    public Asset(String name, double lifespan) {
        this.name = name;
        this.lifespan = lifespan;
    }

    public double getLifespan() {
        return lifespan;
    }

    @Override
    public String toString() {
        return "Asset{'" + name + "'}";
    }
}
