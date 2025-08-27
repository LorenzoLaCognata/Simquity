package com.github.lorenzolacognata.simquity.labor;

public class Job {

    private final String name;

    public Job(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Job{'" + name + "'}";
    }
}
