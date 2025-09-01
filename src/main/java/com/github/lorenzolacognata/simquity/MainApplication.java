package com.github.lorenzolacognata.simquity;

import com.github.lorenzolacognata.simquity.agent.Agent;
import com.github.lorenzolacognata.simquity.agent.Household;
import com.github.lorenzolacognata.simquity.agent.Organization;
import com.github.lorenzolacognata.simquity.agent.Person;
import com.github.lorenzolacognata.simquity.asset.*;
import com.github.lorenzolacognata.simquity.inventory.AgentAsset;
import com.github.lorenzolacognata.simquity.inventory.AssetInventory;
import com.github.lorenzolacognata.simquity.production.AssetProduction;
import com.github.lorenzolacognata.simquity.production.ProductionInventory;
import com.github.lorenzolacognata.simquity.production.ProductionLabor;
import com.github.lorenzolacognata.simquity.production.ProductionLine;
import com.github.lorenzolacognata.simquity.labor.Employment;
import com.github.lorenzolacognata.simquity.labor.Job;
import com.github.lorenzolacognata.simquity.labor.LaborRequirement;
import com.github.lorenzolacognata.simquity.market.DemandAgentAsset;
import com.github.lorenzolacognata.simquity.market.Market;
import com.github.lorenzolacognata.simquity.market.SupplyAssetInventory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

public class MainApplication extends Application {

    static final Pane root = new Pane();

    static final List<Job> jobs = new ArrayList<>();
    static final List<Asset> assets = new ArrayList<>();
    static final List<Agent> agents = new ArrayList<>();
    static final List<Market> markets = new ArrayList<>();

    public static void main(String[] args) {

        // JOBS


        Job farmer = new Job("Farmer");
        jobs.add(farmer);

        // ASSETS

        Asset wheatSeeds = new Good("Wheat Seeds", 104, UnitOfMeasure.KILOGRAM);
        assets.add(wheatSeeds);

        Asset farmingLand = new Good("Farming Land", 5200, UnitOfMeasure.HECTARE);
        assets.add(farmingLand);

        Asset farmingTools = new Good("Farming Tools", 260, UnitOfMeasure.UNIT);
        assets.add(farmingTools);

        Asset farmingMachinery = new Good("Farming Machinery", 520, UnitOfMeasure.UNIT);
        assets.add(farmingMachinery);

        Asset wheat = new Good("Wheat", 26, UnitOfMeasure.TONNE);
        assets.add(wheat);

        Asset dollar = new Currency("Dollar");
        assets.add(dollar);

        // MARKET

        Market wheatSeedsMarket = new Market(wheatSeeds);
        markets.add(wheatSeedsMarket);

        Market farmingLandMarket = new Market(farmingLand);
        markets.add(farmingLandMarket);

        Market farmingToolsMarket = new Market(farmingTools);
        markets.add(farmingToolsMarket);

        Market farmingMachineryMarket = new Market(farmingMachinery);
        markets.add(farmingMachineryMarket);

        // ASSET PRODUCTION

        AssetProduction wheatProduction = new AssetProduction(38, 43, 16, 5.0);
        wheat.addAssetProductionList(wheatProduction);

        AssetRequirement wheatWheatSeedsRequirement = new AssetRequirement(wheatSeeds, 170.0, 0.0, 0.0);
        wheatProduction.addConsumableAssetRequirement(wheatWheatSeedsRequirement);

        AssetRequirement wheatFarmingLandRequirement = new AssetRequirement(farmingLand, 1.0, 0.0, 0.0);
        wheatProduction.addDurableAssetRequirement(wheatFarmingLandRequirement);

        AssetRequirement wheatFarmingToolsRequirement = new AssetRequirement(farmingTools, 0.2, 0.0, 0.0);
        wheatProduction.addDurableAssetRequirement(wheatFarmingToolsRequirement);

        AssetRequirement wheatFarmingMachineryRequirement = new AssetRequirement(farmingMachinery, 0.2, 0.0, 0.0);
        wheatProduction.addDurableAssetRequirement(wheatFarmingMachineryRequirement);

        LaborRequirement wheatFarmerRequirement = new LaborRequirement(farmer, 0.05, 40.0);
        wheatProduction.addLaborRequirement(wheatFarmerRequirement);

        // AGENTS

        Person johnDoe = new Person("John", "Doe");
        Person janeDoe = new Person("Jane", "Doe");

        agents.add(
            new Household(List.of(johnDoe, janeDoe))
        );

        Organization wheatFarming = new Organization("Wheat Farming");
        agents.add(wheatFarming);

        Organization externalSupplier = new Organization("External Supplier");
        agents.add(externalSupplier);

        // EMPLOYMENT

        Employment johnDoeWheatFarming = new Employment(johnDoe, farmer, 12.0, 12.0);
        wheatFarming.addEmployment(johnDoeWheatFarming);

        // WHEAT FARMING ASSETS

        AgentAsset wheatFarmingWheatSeeds = new AgentAsset(wheatFarming, wheatSeeds);
        wheatFarming.addPurchasedAgentAsset(wheatFarmingWheatSeeds);

        AgentAsset wheatFarmingFarmingLand = new AgentAsset(wheatFarming, farmingLand);
        wheatFarming.addPurchasedAgentAsset(wheatFarmingFarmingLand);

        AgentAsset wheatFarmingFarmingTools = new AgentAsset(wheatFarming, farmingTools);
        wheatFarming.addPurchasedAgentAsset(wheatFarmingFarmingTools);

        AgentAsset wheatFarmingFarmingMachinery = new AgentAsset(wheatFarming, farmingMachinery);
        wheatFarming.addPurchasedAgentAsset(wheatFarmingFarmingMachinery);

        AgentAsset wheatFarmingDollar = new AgentAsset(wheatFarming, dollar);
        wheatFarming.addCurrencyAgentAsset(wheatFarmingDollar);
        wheatFarmingDollar.addAssetInventory(100000);

        AgentAsset wheatFarmingWheat = new AgentAsset(wheatFarming, wheat);
        wheatFarming.addProducedAgentAsset(wheatFarmingWheat);

        // EXTERNAL SUPPLIER ASSETS

        AgentAsset externalSupplierWheatSeeds = new AgentAsset(externalSupplier, wheatSeeds);
        externalSupplier.addPurchasedAgentAsset(externalSupplierWheatSeeds);
        externalSupplierWheatSeeds.addAssetInventory(1000000);

        AgentAsset externalSupplierFarmingLand = new AgentAsset(externalSupplier, farmingLand);
        externalSupplier.addPurchasedAgentAsset(externalSupplierFarmingLand);
        externalSupplierFarmingLand.addAssetInventory(1000000);

        AgentAsset externalSupplierFarmingTools = new AgentAsset(externalSupplier, farmingTools);
        externalSupplier.addPurchasedAgentAsset(externalSupplierFarmingTools);
        externalSupplierFarmingTools.addAssetInventory(1000000);

        AgentAsset externalSupplierFarmingMachinery = new AgentAsset(externalSupplier, farmingMachinery);
        externalSupplier.addPurchasedAgentAsset(externalSupplierFarmingMachinery);
        externalSupplierFarmingMachinery.addAssetInventory(1000000);

        AgentAsset externalSupplierDollar = new AgentAsset(externalSupplier, dollar);
        externalSupplier.addCurrencyAgentAsset(externalSupplierDollar);
        externalSupplierDollar.addAssetInventory(0);

        // EXTERNAL SUPPLIER SUPPLY

        SupplyAssetInventory externalSupplierWheatSeedsSupply = new SupplyAssetInventory(externalSupplierWheatSeeds, externalSupplierWheatSeeds.getAssetInventoryList().getFirst(), 1000000, 0.50);
        wheatSeedsMarket.addSupplyAssetInventory(externalSupplierWheatSeedsSupply);

        SupplyAssetInventory externalSupplierFarmingLandSupply = new SupplyAssetInventory(externalSupplierFarmingLand, externalSupplierFarmingLand.getAssetInventoryList().getFirst(), 1000000, 20000);
        farmingLandMarket.addSupplyAssetInventory(externalSupplierFarmingLandSupply);

        SupplyAssetInventory externalSupplierFarmingToolsSupply = new SupplyAssetInventory(externalSupplierFarmingTools, externalSupplierFarmingTools.getAssetInventoryList().getFirst(), 1000000, 800);
        farmingToolsMarket.addSupplyAssetInventory(externalSupplierFarmingToolsSupply);

        SupplyAssetInventory externalSupplierFarmingMachinerySupply = new SupplyAssetInventory(externalSupplierFarmingMachinery, externalSupplierFarmingMachinery.getAssetInventoryList().getFirst(), 1000000, 1000);
        farmingMachineryMarket.addSupplyAssetInventory(externalSupplierFarmingMachinerySupply);

        // WHEAT FARMING DEMAND

        DemandAgentAsset wheatFarmingWheatSeedsDemand = new DemandAgentAsset(wheatFarmingWheatSeeds, 1700, 0.50);
        wheatSeedsMarket.addDemandAgentAsset(wheatFarmingWheatSeedsDemand);

        DemandAgentAsset wheatFarmingFarmingLandDemand = new DemandAgentAsset(wheatFarmingFarmingLand, 1, 20000);
        farmingLandMarket.addDemandAgentAsset(wheatFarmingFarmingLandDemand);

        DemandAgentAsset wheatFarmingFarmingToolsDemand = new DemandAgentAsset(wheatFarmingFarmingTools, 1, 800);
        farmingToolsMarket.addDemandAgentAsset(wheatFarmingFarmingToolsDemand);

        DemandAgentAsset wheatFarmingFarmingMachineryDemand = new DemandAgentAsset(wheatFarmingFarmingMachinery, 1, 1000);
        farmingMachineryMarket.addDemandAgentAsset(wheatFarmingFarmingMachineryDemand);

        // MARKET CLEARING

        wheatSeedsMarket.clearMarketWithBackstop();

        // TEMPORARY - MANUAL PURCHASE OF EXTERNAL INPUTS

        wheatFarmingFarmingLand.addAssetInventory(0.0);
        wheatFarmingFarmingLand.getAssetInventoryList().getFirst().addQuantity(1.0);
        wheatFarming.getCurrencyAgentAssetList().getFirst().getAssetInventoryList().getFirst().addQuantity(-20000.0);

        wheatFarmingFarmingTools.addAssetInventory(0.0);
        wheatFarmingFarmingTools.getAssetInventoryList().getFirst().addQuantity(1.0);
        wheatFarming.getCurrencyAgentAssetList().getFirst().getAssetInventoryList().getFirst().addQuantity(-800.0);

        wheatFarmingFarmingMachinery.addAssetInventory(0.0);
        wheatFarmingFarmingMachinery.getAssetInventoryList().getFirst().addQuantity(1.0);
        wheatFarming.getCurrencyAgentAssetList().getFirst().getAssetInventoryList().getFirst().addQuantity(-1000.0);

        // PRODUCTION LINE

        ProductionLine wheatProductionLine = new ProductionLine(wheatFarmingWheat, wheatProduction, 40);
        wheatFarmingWheat.addProductionLine(wheatProductionLine);

        // PRODUCTION

        wheatFarmingWheat.produceAll();

        // TODO: 5) Simulate purchase through market
        // TODO: 6) Create supply of Wheat and create external demand for Wheat
        // TODO: 7) Simulate market iterations and equilibrium price setting

        final Label label = new Label();
        label.setText("Simquity");
        root.getChildren().add(label);

        logging();

        launch();
    }

    public static void logging() {

        // SUMMARY

        System.out.println("ASSETS");
        for (Asset asset : assets) {
            System.out.println("\t" + asset);
            if (!asset.getAssetProductionList().isEmpty()) {
                System.out.println("\t\tProduction:");
                for (AssetProduction assetProduction : asset.getAssetProductionList()) {
                    if (asset instanceof Good) {
                        System.out.println("\t\t\tOutput Qty: " + assetProduction.getOutputQuantity() + " " + ((Good) asset).getUnitOfMeasure());
                    }
                    else {
                        System.out.println("\t\t\tOutput Qty: " + assetProduction.getOutputQuantity());
                    }
                    if (!assetProduction.getConsumableAssetRequirementList().isEmpty()) {
                        System.out.println("\t\t\tConsumable Asset:");
                        for (AssetRequirement consumableAssetRequirement : assetProduction.getConsumableAssetRequirementList()) {
                            System.out.println("\t\t\t\t" + consumableAssetRequirement);
                        }
                    }
                    if (!assetProduction.getDurableAssetRequirementList().isEmpty()) {
                        System.out.println("\t\t\tDurable Asset:");
                        for (AssetRequirement durableAssetRequirement : assetProduction.getDurableAssetRequirementList()) {
                            System.out.println("\t\t\t\t" + durableAssetRequirement);
                        }
                    }
                    if (!assetProduction.getLaborRequirementList().isEmpty()) {
                        System.out.println("\t\t\tLabor:");
                        for (LaborRequirement laborRequirement : assetProduction.getLaborRequirementList()) {
                            System.out.println("\t\t\t\t" + laborRequirement);
                        }
                    }
                }
            }
        }

        System.out.println("\nAGENTS");
        for (Agent agent : agents) {
            System.out.println("\t" + agent);
            if (agent instanceof Organization) {
                List<Employment> employments = ((Organization) agent).getEmploymentList();
                if (!employments.isEmpty()) {
                    System.out.println("\t\tEmployments:");
                    for (Employment employment : employments) {
                        System.out.println("\t\t\t" + employment);
                    }
                }
                if (!agent.getProducedAgentAssetList().isEmpty()) {
                    System.out.println("\t\tProduced Assets:");
                    for (AgentAsset producedAgentAsset : agent.getProducedAgentAssetList()) {
                        System.out.println("\t\t\t" + producedAgentAsset);

                        List<AssetInventory> assetInventoryList = producedAgentAsset.getAssetInventoryList();
                        if (!assetInventoryList.isEmpty()) {
                            for (AssetInventory assetInventory : assetInventoryList) {
                                System.out.println("\t\t\t\tAvailable: " + assetInventory.getQuantity());
                            }
                        }

                        if (!producedAgentAsset.getProductionLineList().isEmpty()) {
                            System.out.println("\t\t\t\tProduction Line:");
                            for (ProductionLine assetProductionLine : producedAgentAsset.getProductionLineList()) {
                                System.out.println("\t\t\t\t\tStatus: " + assetProductionLine.getProductionStatus());
                                AssetProduction assetProduction = assetProductionLine.getAssetProduction();

                                if (!assetProduction.getConsumableAssetRequirementList().isEmpty()) {
                                    System.out.println("\t\t\t\t\tConsumable Asset:");
                                    for (AssetRequirement consumableAssetRequirement : assetProduction.getConsumableAssetRequirementList()) {
                                        Asset asset = consumableAssetRequirement.getAsset();
                                        System.out.println("\t\t\t\t\t\t" + asset);
                                        System.out.println("\t\t\t\t\t\t\tRequired: " + consumableAssetRequirement.getInitialQuantity());
                                        List<ProductionInventory> consumableProductionInventoryList = assetProductionLine.getConsumableProductionInventoryList(asset);
                                        if (!consumableProductionInventoryList.isEmpty()) {
                                            for (ProductionInventory consumableProductionInventory : consumableProductionInventoryList) {
                                                System.out.println("\t\t\t\t\t\t\tUsed: " + consumableProductionInventory.getQuantity());
                                            }
                                        }
                                    }
                                }

                                if (!assetProduction.getDurableAssetRequirementList().isEmpty()) {
                                    System.out.println("\t\t\t\t\tDurable Asset:");
                                    for (AssetRequirement durableAssetRequirement : assetProduction.getDurableAssetRequirementList()) {
                                        Asset asset = durableAssetRequirement.getAsset();
                                        System.out.println("\t\t\t\t\t\t" + asset);
                                        System.out.println("\t\t\t\t\t\t\tRequired: " + durableAssetRequirement.getInitialQuantity());
                                        List<ProductionInventory> durableProductionInventoryList = assetProductionLine.getDurableProductionInventoryList(asset);
                                        if (!durableProductionInventoryList.isEmpty()) {
                                            for (ProductionInventory durableProductionInventory : durableProductionInventoryList) {
                                                System.out.println("\t\t\t\t\t\t\tUsed: " + durableProductionInventory.getQuantity());
                                            }
                                        }
                                    }
                                }

                                if (!assetProduction.getLaborRequirementList().isEmpty()) {
                                    System.out.println("\t\t\t\t\tLabor:");
                                    for (LaborRequirement laborRequirement : assetProduction.getLaborRequirementList()) {
                                        Job job = laborRequirement.getJob();
                                        System.out.println("\t\t\t\t\t\t" + job);
                                        System.out.println("\t\t\t\t\t\t\tRequired: " + laborRequirement.getFtes());
                                        List<ProductionLabor> productionLaborList = assetProductionLine.getProductionLaborList(job);
                                        if (!productionLaborList.isEmpty()) {
                                            for (ProductionLabor productionLabor : productionLaborList) {
                                                System.out.println("\t\t\t\t\t\t\tUsed: " + productionLabor.getFtes());
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                }

                if (!agent.getPurchasedAgentAssetList().isEmpty()) {
                    System.out.println("\t\tPurchased Assets:");
                    for (AgentAsset purchasedAgentAsset : agent.getPurchasedAgentAssetList()) {
                        System.out.println("\t\t\t" + purchasedAgentAsset);

                        List<AssetInventory> assetInventoryList = purchasedAgentAsset.getAssetInventoryList();
                        if (!assetInventoryList.isEmpty()) {
                            for (AssetInventory assetInventory : assetInventoryList) {
                                System.out.println("\t\t\t\tAvailable: " + assetInventory.getQuantity());
                            }
                        }

                    }
                }

                if (!agent.getCurrencyAgentAssetList().isEmpty()) {
                    System.out.println("\t\tCurrency Assets:");
                    for (AgentAsset currencyAgentAsset : agent.getCurrencyAgentAssetList()) {
                        System.out.println("\t\t\t" + currencyAgentAsset);

                        List<AssetInventory> assetInventoryList = currencyAgentAsset.getAssetInventoryList();
                        if (!assetInventoryList.isEmpty()) {
                            for (AssetInventory assetInventory : assetInventoryList) {
                                System.out.println("\t\t\t\tAvailable: " + assetInventory.getQuantity());
                            }
                        }

                    }
                }

            }
        }

        System.out.println("\nMARKETS");
        for (Market market : markets) {
            System.out.println("\t" + market);
            if (!market.getSupplyAssetInventoryList().isEmpty()) {
                System.out.println("\t\tSupply:");
                for (SupplyAssetInventory supplyAssetInventory : market.getSupplyAssetInventoryList()) {
                    System.out.println("\t\t\t" + supplyAssetInventory + ": " + supplyAssetInventory.getQuantity() + " @" + supplyAssetInventory.getMarginalCost());
                }
            }
            if (!market.getDemandAgentAssetList().isEmpty()) {
                System.out.println("\t\tDemand:");
                for (DemandAgentAsset demandAgentAsset : market.getDemandAgentAssetList()) {
                    System.out.println("\t\t\t" + demandAgentAsset + ": " + demandAgentAsset.getQuantity() + " @" + demandAgentAsset.getMaximumPrice());
                }
            }
        }

    }

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Simquity");
        stage.setMaximized(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

    }

}