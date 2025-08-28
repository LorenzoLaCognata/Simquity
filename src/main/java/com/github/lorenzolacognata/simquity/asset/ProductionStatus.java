package com.github.lorenzolacognata.simquity.asset;

public enum ProductionStatus {

    NOT_STARTED("Not Started"),
    IN_PROGRESS("In Progress"),
    COMPLETE("Complete");

    private final String name;

    ProductionStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}