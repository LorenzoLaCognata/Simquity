package com.github.lorenzolacognata.simquity.agent;

import com.github.lorenzolacognata.simquity.labor.Employment;
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

    public List<Employment> getEmploymentList() {
        return employmentList;
    }

    @Override
    public String toString() {
        return "Organization{'" + name + "'}";
    }

    public void addEmployment(Employment employment) {
        boolean alreadyExists = employmentList.stream()
                .anyMatch(a -> a.getPerson().equals(employment.getPerson()));
        if (!alreadyExists) {
            employmentList.add(employment);
        }
    }

}