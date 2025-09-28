package com.github.lorenzolacognata.simquity.market;

import com.github.lorenzolacognata.simquity.agent.Agent;
import com.github.lorenzolacognata.simquity.asset.Asset;

public class Transaction {

    private final Agent inputAgent;
    private final double inputQuantity;
    private final Agent outputAgent;
    private final Asset outputAsset;
    private double outputQuantity;

    public Transaction(Agent inputAgent, double inputQuantity, Agent outputAgent, Asset outputAsset, double outputQuantity) {
        this.inputAgent = inputAgent;
        this.inputQuantity = inputQuantity;
        this.outputAgent = outputAgent;
        this.outputAsset = outputAsset;
        this.outputQuantity = outputQuantity;
    }

    public Transaction(Agent inputAgent, double inputQuantity, Agent outputAgent, Asset outputAsset) {
        this.inputAgent = inputAgent;
        this.inputQuantity = inputQuantity;
        this.outputAgent = outputAgent;
        this.outputAsset = outputAsset;
    }

    public Agent getInputAgent() {
        return inputAgent;
    }

    public double getInputQuantity() {
        return inputQuantity;
    }

    public Agent getOutputAgent() {
        return outputAgent;
    }

    public double getOutputQuantity() {
        return outputQuantity;
    }

    public Asset getOutputAsset() {
        return outputAsset;
    }

    public void setOutputQuantity(double outputQuantity) {
        this.outputQuantity = outputQuantity;
    }

}