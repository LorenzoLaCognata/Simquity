package com.github.lorenzolacognata.simquity.asset;

public enum AssetType {

    WHEAT_SEEDS("Wheat Seeds"),
    FARMING_LAND("Farming Land"),
    FARMING_TOOLS("Farming Tools"),
    FARMING_MACHINERY("Farming Machinery"),
    INDUSTRIAL_FLOUR_MILL("Industrial Flour Mill"),
    FLOUR_BAG("Flour Bag"),
    WHEAT("Wheat"),
    WHEAT_FLOUR("Wheat Flour"),
    DOLLAR("Dollar");

    private final String name;

    AssetType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}