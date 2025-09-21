package com.github.lorenzolacognata.simquity.labor;

public enum JobType {

    FARMER("Farmer"),
    MILL_WORKER("Mill Worker");

    private final String name;

    JobType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}