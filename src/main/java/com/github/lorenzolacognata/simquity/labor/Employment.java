package com.github.lorenzolacognata.simquity.labor;

import com.github.lorenzolacognata.simquity.agent.Person;

public class Employment {

    private final Person person;
    private final Job job;
    private double cost;
    private double salary;
    private double ftesInUse;

    public Employment(Person person, Job job, double cost, double salary) {
        this.person = person;
        this.job = job;
        this.cost = cost;
        this.salary = salary;
        this.ftesInUse = 0.0;
    }

    public Person getPerson() {
        return person;
    }

    public Job getJob() {
        return job;
    }

    @Override
    public String toString() {
        return "Employment{" + person + " - " + job + "}";
    }
}
