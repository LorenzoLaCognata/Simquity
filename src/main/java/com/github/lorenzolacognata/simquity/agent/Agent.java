package com.github.lorenzolacognata.simquity.agent;

import com.github.lorenzolacognata.simquity.inventory.AgentAsset;

import java.util.ArrayList;
import java.util.List;

public abstract class Agent {

    private final List<AgentAsset> purchasedAgentAssetList;
    private final List<AgentAsset> producedAgentAssetList;
    private final List<AgentAsset> currencyAgentAssetList;

    public Agent() {
        this.purchasedAgentAssetList = new ArrayList<>();
        this.producedAgentAssetList = new ArrayList<>();
        this.currencyAgentAssetList = new ArrayList<>();
    }

    public List<AgentAsset> getPurchasedAgentAssetList() {
        return purchasedAgentAssetList;
    }

    public List<AgentAsset> getProducedAgentAssetList() {
        return producedAgentAssetList;
    }

    public List<AgentAsset> getCurrencyAgentAssetList() {
        return currencyAgentAssetList;
    }

    public void addPurchasedAgentAsset(AgentAsset agentAsset) {
        boolean alreadyExists = purchasedAgentAssetList.stream()
                .anyMatch(a -> a.getAsset().equals(agentAsset.getAsset()));
        if (!alreadyExists) {
            purchasedAgentAssetList.add(agentAsset);
        }
    }

    public void addProducedAgentAsset(AgentAsset agentAsset) {
        boolean alreadyExists = producedAgentAssetList.stream()
                .anyMatch(a -> a.getAsset().equals(agentAsset.getAsset()));
        if (!alreadyExists) {
            producedAgentAssetList.add(agentAsset);
        }
    }

    public void addCurrencyAgentAsset(AgentAsset agentAsset) {
        boolean alreadyExists = currencyAgentAssetList.stream()
                .anyMatch(a -> a.getAsset().equals(agentAsset.getAsset()));
        if (!alreadyExists) {
            currencyAgentAssetList.add(agentAsset);
        }
    }

}