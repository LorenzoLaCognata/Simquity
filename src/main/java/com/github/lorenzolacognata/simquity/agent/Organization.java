package com.github.lorenzolacognata.simquity.agent;

import com.github.lorenzolacognata.simquity.labor.Employment;
import com.github.lorenzolacognata.simquity.labor.Job;
import com.github.lorenzolacognata.simquity.labor.LaborRequirement;

import java.util.ArrayList;
import java.util.List;

public class Organization extends Agent {

    private final String name;
    private final List<Employment> employmentList;

    public Organization(String name) {
        this.name = name;
        this.employmentList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Employment> getEmploymentList() {
        return employmentList;
    }

    public List<Employment> getEmploymentList(Job job) {
        return employmentList.stream()
                .filter(a -> a.getJob().equals(job))
                .toList();
    }

    @Override
    public String toString() {
        return name;
    }

    public void addEmployment(Employment employment) {
        boolean alreadyExists = employmentList.stream()
                .anyMatch(a -> a.getPerson().equals(employment.getPerson()));
        if (!alreadyExists) {
            employmentList.add(employment);
        }
    }

}