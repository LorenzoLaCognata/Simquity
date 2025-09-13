package com.github.lorenzolacognata.simquity.asset;

public class Good extends Asset {

    private final UnitOfMeasure unitOfMeasure;

    public Good(String name, double lifespan, double targetGrossMargin, UnitOfMeasure unitOfMeasure) {
        super(name, lifespan, targetGrossMargin);
        this.unitOfMeasure = unitOfMeasure;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " - " + getName();
    }

}