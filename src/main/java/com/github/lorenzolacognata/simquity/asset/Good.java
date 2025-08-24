package com.github.lorenzolacognata.simquity.asset;

public class Good extends Asset {

    private final UnitOfMeasure unitOfMeasure;

    public Good(String name, UnitOfMeasure unitOfMeasure) {
        super(name);
        this.unitOfMeasure = unitOfMeasure;
    }

}