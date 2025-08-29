package com.github.lorenzolacognata.simquity.agent;

import java.util.List;

public class Household extends Consumer {

    private final List<Person> personList;

    public Household(List<Person> personList) {
        this.personList = personList;
    }

    @Override
    public String toString() {
        if (personList.isEmpty()) {
            return this.getClass().getSimpleName() ;
        }
        else {
            return this.getClass().getSimpleName() + " - " + personList.getFirst();
        }
    }

}