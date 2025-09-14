package com.github.lorenzolacognata.simquity.market;

import com.github.lorenzolacognata.simquity.asset.Asset;
import com.github.lorenzolacognata.simquity.inventory.AssetInventory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Market {

    private final Asset asset;
    private double tradedQuantity;
    private double clearingPrice;
    private final List<DemandAgentAsset> demandAgentAssetList;
    private final List<SupplyAssetInventory> supplyAssetInventoryList;
    // TODO: implement backstop demand

    public Market(Asset asset) {
        this.asset = asset;
        this.tradedQuantity = 0;
        this.clearingPrice = Double.NaN;
        this.demandAgentAssetList = new ArrayList<>();
        this.supplyAssetInventoryList = new ArrayList<>();
    }

    public Asset getAsset() {
        return asset;
    }

    public double getTradedQuantity() {
        return tradedQuantity;
    }

    public double getClearingPrice() {
        return clearingPrice;
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

    @Override
    public String toString() {
        return asset.toString();
    }

    public void clearMarketWithBackstop() {

        if (!demandAgentAssetList.isEmpty()) {

            System.out.println("\t" + this);

            supplyAssetInventoryList.sort(Comparator.comparingDouble(a -> a.getMarginalCost()));
            demandAgentAssetList.sort((a, b) -> Double.compare(b.getMaximumPrice(), a.getMaximumPrice()));

            for (int di = 0; di < demandAgentAssetList.size(); di++) {
                DemandAgentAsset demandAgentAsset = demandAgentAssetList.get(di);
                System.out.println("\t\tDemand: " + demandAgentAsset + ": " + demandAgentAsset.getQuantity());
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

            System.out.println("\t\tMarket Clearing: " + tradedQuantity + " @" + clearingPrice);
        }

        clearDemand(demandAgentAssetList);
        clearSupply(supplyAssetInventoryList);
        tradedQuantity = 0;
        clearingPrice = Double.NaN;

    }

    private void matchDemandWithSupply(DemandAgentAsset d, List<SupplyAssetInventory> supplyAssetInventoryList) {

        for (int si = 0; si < supplyAssetInventoryList.size(); si++) {
            if (d.getQuantityLeft() <= 0) {
                break;
            }

            SupplyAssetInventory supplyAssetInventory = supplyAssetInventoryList.get(si);
            if (supplyAssetInventory.getQuantityLeft() > 0) {
                System.out.println("\t\t\tSupply: " + supplyAssetInventory);
                if (d.getMaximumPrice() < supplyAssetInventory.getMarginalCost()) {
                    break;
                }

                double traded = Math.min(d.getQuantityLeft(), supplyAssetInventory.getQuantityLeft());
                d.removeQuantityLeft(traded);
                supplyAssetInventory.removeQuantityLeft(traded);
                tradedQuantity += traded;
                clearingPrice = supplyAssetInventory.getMarginalCost();
                System.out.println("\t\t\t\tTraded: " + traded + " - Current Price: " + supplyAssetInventory.getMarginalCost());
            }
        }
    }

    private void clearSupply(List<SupplyAssetInventory> supplyAssetInventoryList) {
        Iterator<SupplyAssetInventory> supplyAssetInventoryIterator = supplyAssetInventoryList.iterator();
        while (supplyAssetInventoryIterator.hasNext()) {
            SupplyAssetInventory supplyAssetInventory = supplyAssetInventoryIterator.next();
            if (supplyAssetInventory.getQuantityTraded() > 0) {
                supplyAssetInventory.getAssetInventory().addQuantity(-supplyAssetInventory.getQuantityTraded());
                // TODO: manage multiple currencies with multiple inventories
                AssetInventory currencyAssetInventory = supplyAssetInventory.getAgentAsset().getAgent().getCurrencyAgentAssetList().getFirst().getAssetInventoryList().getFirst();
                currencyAssetInventory.addQuantity(clearingPrice * supplyAssetInventory.getQuantityTraded());
                supplyAssetInventoryIterator.remove();
            }
        }
    }

    private void clearDemand(List<DemandAgentAsset> demandAgentAssetList) {
        Iterator<DemandAgentAsset> demandAgentAssetIterator = demandAgentAssetList.iterator();
        while (demandAgentAssetIterator.hasNext()) {
            DemandAgentAsset demandAgentAsset = demandAgentAssetIterator.next();
            if (demandAgentAsset.getQuantityTraded() > 0) {
                demandAgentAsset.getAgentAsset().addAssetInventory(demandAgentAsset.getQuantityTraded(), clearingPrice);
                // TODO: manage multiple currencies with multiple inventories
                AssetInventory currencyAssetInventory = demandAgentAsset.getAgentAsset().getAgent().getCurrencyAgentAssetList().getFirst().getAssetInventoryList().getFirst();
                currencyAssetInventory.addQuantity(-clearingPrice * demandAgentAsset.getQuantityTraded());
                demandAgentAssetIterator.remove();
            }
        }
    }

}