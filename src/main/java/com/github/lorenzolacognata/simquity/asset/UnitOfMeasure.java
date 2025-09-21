package com.github.lorenzolacognata.simquity.asset;

public enum UnitOfMeasure {

    // TODO: make sure assets and inventories counted in units never have decimals and are always integers
    UNIT(""),
    KILOGRAM("kg"),
    TONNE("t"),
    HECTARE("ha");

    private final String name;

    UnitOfMeasure(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}