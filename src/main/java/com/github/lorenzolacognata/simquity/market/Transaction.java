package com.github.lorenzolacognata.simquity.market;

import com.github.lorenzolacognata.simquity.agent.Agent;
import com.github.lorenzolacognata.simquity.asset.Asset;

public class Transaction {

    private final Agent inputAgent;
    private final double inputQuantity;
    private final Asset outputAsset;
    private final Agent outputAgent;
    private final double outputQuantity;

    public Transaction(Agent inputAgent, double inputQuantity, Asset outputAsset, Agent outputAgent, double outputQuantity) {
        this.inputAgent = inputAgent;
        this.inputQuantity = inputQuantity;
        this.outputAsset = outputAsset;
        this.outputAgent = outputAgent;
        this.outputQuantity = outputQuantity;
    }

    public Agent getInputAgent() {
        return inputAgent;
    }

    public double getInputQuantity() {
        return inputQuantity;
    }

    public Asset getOutputAsset() {
        return outputAsset;
    }

    public Agent getOutputAgent() {
        return outputAgent;
    }

    public double getOutputQuantity() {
        return outputQuantity;
    }
}