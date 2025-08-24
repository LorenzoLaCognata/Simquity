package com.github.lorenzolacognata.simquity.asset;

public abstract class Asset {

    private final String name;

    public Asset(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Asset{'" + name + "'}";
    }
}
