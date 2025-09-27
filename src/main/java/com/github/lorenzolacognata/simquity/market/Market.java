package com.github.lorenzolacognata.simquity.market;

import com.github.lorenzolacognata.simquity.MainApplication;
import com.github.lorenzolacognata.simquity.inventory.AgentAsset;
import com.github.lorenzolacognata.simquity.inventory.AssetInventory;

import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Market {

    private double tradedQuantity;
    private final List<Double> clearingPriceList;
    private final List<DemandAgentAsset> demandAgentAssetList;
    private final List<SupplyAssetInventory> supplyAssetInventoryList;
    private final List<Transaction> transactionList;

    // TODO: implement backstop demand

    public Market() {
        this.tradedQuantity = 0;
        this.clearingPriceList = new ArrayList<>();
        this.demandAgentAssetList = new ArrayList<>();
        this.supplyAssetInventoryList = new ArrayList<>();
        this.transactionList = new ArrayList<>();
    }

    public double getTradedQuantity() {
        return tradedQuantity;
    }

    public List<Double> getClearingPriceList() {
        return clearingPriceList;
    }

    public List<DemandAgentAsset> getDemandAgentAssetList() {
        return demandAgentAssetList;
    }

    public List<SupplyAssetInventory> getSupplyAssetInventoryList() {
        return supplyAssetInventoryList;
    }

    public void addDemandAgentAsset(DemandAgentAsset demandAgentAsset) {
        demandAgentAssetList.add(demandAgentAsset);
    }

    public void addSupplyAssetInventory(SupplyAssetInventory supplyAssetInventory) {
        supplyAssetInventoryList.add(supplyAssetInventory);
    }

    public void addTransaction(Transaction transaction) {
        transactionList.add(transaction);
    }

    public void clearMarketWithBackstop() {

        if (!demandAgentAssetList.isEmpty()) {

            supplyAssetInventoryList.sort(Comparator.comparingDouble(a -> a.getMarginalCost()));
            demandAgentAssetList.sort((a, b) -> Double.compare(b.getMaximumPrice(), a.getMaximumPrice()));

            for (int di = 0; di < demandAgentAssetList.size(); di++) {
                DemandAgentAsset demandAgentAsset = demandAgentAssetList.get(di);
                if (MainApplication.LOG_MARKET_CLEARING & (demandAgentAsset.getAgentAsset().getAsset().getAssetType() == MainApplication.LOG_SELECTED_ASSET_TYPE || MainApplication.LOG_SELECTED_ASSET_TYPE == null)) {
                    System.out.println("\t\tDemand: " + demandAgentAsset + ": " + demandAgentAsset.getQuantity() + " @ " + demandAgentAsset.getMaximumPrice());
                }
                matchDemandWithSupply(demandAgentAsset, supplyAssetInventoryList);
            }

            /*
            System.out.println("Backstop Demand matching");

            for (int si = 0; si < supplyOffers.size(); si++) {
                SupplyOffer s = supplyOffers.get(si);
                if (s.quantity > 0 && backstopDemand.quantity > 0) {
                    double traded = Math.min(s.quantity, backstopDemand.quantity);
                    s.quantity -= traded;
                    backstopDemand.quantity -= traded;
                    System.out.println("Backstop Demand | Supply #" + s.id + " - Qty: " + traded + " - Price: " + backstopDemand.price);
                }
            }
            */

            if (tradedQuantity > 0) {
                if (MainApplication.LOG_MARKET_CLEARING & (demandAgentAssetList.getFirst().getAgentAsset().getAsset().getAssetType() == MainApplication.LOG_SELECTED_ASSET_TYPE || MainApplication.LOG_SELECTED_ASSET_TYPE == null)) {
                    System.out.println("\t\tMarket Clearing: " + tradedQuantity + " @" + clearingPriceList.getLast());
                }
            }
        }

        clearDemand(demandAgentAssetList);
        clearSupply(supplyAssetInventoryList);
        tradedQuantity = 0;

    }

    private void matchDemandWithSupply(DemandAgentAsset d, List<SupplyAssetInventory> supplyAssetInventoryList) {

        for (int si = 0; si < supplyAssetInventoryList.size(); si++) {
            if (d.getQuantityLeft() <= 0) {
                break;
            }

            SupplyAssetInventory supplyAssetInventory = supplyAssetInventoryList.get(si);
            if (supplyAssetInventory.getQuantityLeft() > 0) {
                if (MainApplication.LOG_MARKET_CLEARING & (supplyAssetInventory.getAgentAsset().getAsset().getAssetType() == MainApplication.LOG_SELECTED_ASSET_TYPE || MainApplication.LOG_SELECTED_ASSET_TYPE == null)) {
                    System.out.println("\t\t\tSupply: " + supplyAssetInventory + ": " + supplyAssetInventory.getQuantity() + " @ " + supplyAssetInventory.getMarginalCost());
                }
                if (d.getMaximumPrice() < supplyAssetInventory.getMarginalCost()) {
                    if (MainApplication.LOG_MARKET_CLEARING & (supplyAssetInventory.getAgentAsset().getAsset().getAssetType() == MainApplication.LOG_SELECTED_ASSET_TYPE || MainApplication.LOG_SELECTED_ASSET_TYPE == null)) {
                        System.out.println("\t\t\t\t[ALERT] Demand Maximum Buying Price lower than Supply Marginal Cost");
                    }
                    break;
                }

                double traded = Math.min(d.getQuantityLeft(), supplyAssetInventory.getQuantityLeft());
                d.removeQuantityLeft(traded);
                supplyAssetInventory.removeQuantityLeft(traded);
                tradedQuantity += traded;
                clearingPriceList.addLast(supplyAssetInventory.getMarginalCost());
                if (MainApplication.LOG_MARKET_CLEARING & (supplyAssetInventory.getAgentAsset().getAsset().getAssetType() == MainApplication.LOG_SELECTED_ASSET_TYPE || MainApplication.LOG_SELECTED_ASSET_TYPE == null)) {
                    System.out.println("\t\t\t\tTraded: " + traded + " - Current Price: " + supplyAssetInventory.getMarginalCost());
                }
            }
        }
    }

    private void clearSupply(List<SupplyAssetInventory> supplyAssetInventoryList) {

        Iterator<SupplyAssetInventory> supplyAssetInventoryIterator = supplyAssetInventoryList.iterator();

        while (supplyAssetInventoryIterator.hasNext()) {

            SupplyAssetInventory supplyAssetInventory = supplyAssetInventoryIterator.next();
            AgentAsset agentAsset = supplyAssetInventory.getAgentAsset();

            if (supplyAssetInventory.getQuantityTraded() > 0) {
                supplyAssetInventory.getAssetInventory().addQuantity(-supplyAssetInventory.getQuantityTraded());
                // TODO: manage multiple currencies with multiple inventories
                AssetInventory currencyAssetInventory = supplyAssetInventory.getAgentAsset().getAgent().getCurrencyAgentAssetList().getFirst().getAssetInventoryList().getFirst();
                currencyAssetInventory.addQuantity(clearingPriceList.getLast() * supplyAssetInventory.getQuantityTraded());
            }

            supplyAssetInventoryIterator.remove();
        }

    }

    private void clearDemand(List<DemandAgentAsset> demandAgentAssetList) {
        Iterator<DemandAgentAsset> demandAgentAssetIterator = demandAgentAssetList.iterator();
        while (demandAgentAssetIterator.hasNext()) {
            DemandAgentAsset demandAgentAsset = demandAgentAssetIterator.next();
            if (demandAgentAsset.getQuantityTraded() > 0) {
                demandAgentAsset.getAgentAsset().addAssetInventory(demandAgentAsset.getQuantityTraded(), clearingPriceList.getLast());
                // TODO: manage multiple currencies with multiple inventories
                AssetInventory currencyAssetInventory = demandAgentAsset.getAgentAsset().getAgent().getCurrencyAgentAssetList().getFirst().getAssetInventoryList().getFirst();
                currencyAssetInventory.addQuantity(-clearingPriceList.getLast() * demandAgentAsset.getQuantityTraded());
            }
            demandAgentAssetIterator.remove();
        }
    }

}