package com.github.lorenzolacognata.simquity.agent;

import com.github.lorenzolacognata.simquity.asset.Asset;
import com.github.lorenzolacognata.simquity.inventory.AgentAsset;
import com.github.lorenzolacognata.simquity.inventory.AssetInventory;
import com.github.lorenzolacognata.simquity.market.DemandAgentAsset;

import java.util.ArrayList;
import java.util.Iterator;
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

    public AgentAsset getPurchasedAgentAsset(Asset asset) {
        return purchasedAgentAssetList.stream()
            .filter(a -> a.getAsset().equals(asset))
            .findFirst()
            .orElse(null);
    }

    public AgentAsset getProducedAgentAsset(Asset asset) {
        return producedAgentAssetList.stream()
                .filter(a -> a.getAsset().equals(asset))
                .findFirst()
                .orElse(null);
    }

    public AgentAsset getCurrencyAgentAsset(Asset asset) {
        return currencyAgentAssetList.stream()
                .filter(a -> a.getAsset().equals(asset))
                .findFirst()
                .orElse(null);
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

    private void cleanAssetInventoryList(List<AgentAsset> agentAssetList) {
        for (AgentAsset agentAsset : agentAssetList) {
            agentAsset.cleanAssetInventoryList();
        }
    }

    public void cleanPurchasedAssetInventoryList() {
        cleanAssetInventoryList(purchasedAgentAssetList);
    }

    public void cleanProducedAssetInventoryList() {
        cleanAssetInventoryList(producedAgentAssetList);
    }


}