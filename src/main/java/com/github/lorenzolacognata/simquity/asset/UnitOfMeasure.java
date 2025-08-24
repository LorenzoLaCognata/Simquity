package com.github.lorenzolacognata.simquity.asset;

public enum UnitOfMeasure {

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
        return this.name;
    }

}