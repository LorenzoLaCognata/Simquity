package com.github.lorenzolacognata.simquity.agent;

public class Organization extends Agent {

    private final String name;

    public Organization(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Organization{'" + name + "'}";
    }

}