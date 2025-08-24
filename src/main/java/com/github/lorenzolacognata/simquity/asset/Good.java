package com.github.lorenzolacognata.simquity.asset;

public class Good extends Asset {

    private final UnitOfMeasure unitOfMeasure;

    public Good(String name, double lifespan, UnitOfMeasure unitOfMeasure) {
        super(name, lifespan);
        this.unitOfMeasure = unitOfMeasure;
    }

}