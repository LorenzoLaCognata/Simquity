package com.github.lorenzolacognata.simquity.agent;

public class Person extends Consumer {

    private final String firstName;
    private final String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person{'" + firstName + " " + lastName + "'}";
    }

}