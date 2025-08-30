package com.github.lorenzolacognata.simquity.labor;

public class LaborRequirement {

    private final Job job;
    private final double ftes;
    private final double hours;

    public LaborRequirement(Job job, double ftes, double hours) {
        this.job = job;
        this.ftes = ftes;
        this.hours = hours;
    }

    public Job getJob() {
        return job;
    }

    public double getFtes() {
        return ftes;
    }

    @Override
    public String toString() {
        return job + " : " + hours + " h";
    }
}
