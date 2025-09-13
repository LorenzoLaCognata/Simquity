package com.github.lorenzolacognata.simquity.labor;

import com.github.lorenzolacognata.simquity.agent.Person;

public class Employment {

    private final Person person;
    private final Job job;
    private double cost;
    private double salary;
    private double ftes;

    public Employment(Person person, Job job, double cost, double salary) {
        this.person = person;
        this.job = job;
        this.cost = cost;
        this.salary = salary;
        this.ftes = 1.0;
    }

    public Person getPerson() {
        return person;
    }

    public Job getJob() {
        return job;
    }

    public double getCost() {
        return cost;
    }

    public double getSalary() {
        return salary;
    }

    public double getFtes() {
        return ftes;
    }

    public void addFtes(double ftes) {
        this.ftes += ftes;
    }

    @Override
    public String toString() {
        return person + " - " + job;
    }
}