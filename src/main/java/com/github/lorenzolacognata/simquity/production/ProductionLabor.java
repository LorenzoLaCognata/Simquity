package com.github.lorenzolacognata.simquity.production;

import com.github.lorenzolacognata.simquity.inventory.AssetInventory;
import com.github.lorenzolacognata.simquity.labor.Employment;

public class ProductionLabor {

    private final Employment employment;
    private final double ftes;

    public ProductionLabor(Employment employment, double ftes) {
        this.employment = employment;
        this.ftes = ftes;
    }

    public Employment getEmployment() {
        return employment;
    }

    public double getFtes() {
        return ftes;
    }

}