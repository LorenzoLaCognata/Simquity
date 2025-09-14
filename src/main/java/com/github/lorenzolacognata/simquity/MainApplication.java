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

        int SIMULATED_WHEAT_FARMING = 3;

        // JOBS

        Job farmer = new Job("Farmer");
        jobs.add(farmer);

        // ASSETS

        Asset wheatSeeds = new Good("Wheat Seeds", 104, Double.NaN, UnitOfMeasure.KILOGRAM);
        assets.add(wheatSeeds);

        Asset farmingLand = new Good("Farming Land", 5200, Double.NaN, UnitOfMeasure.HECTARE);
        assets.add(farmingLand);

        Asset farmingTools = new Good("Farming Tools", 260, Double.NaN, UnitOfMeasure.UNIT);
        assets.add(farmingTools);

        Asset farmingMachinery = new Good("Farming Machinery", 520, Double.NaN, UnitOfMeasure.UNIT);
        assets.add(farmingMachinery);

        Asset wheat = new Good("Wheat", 26, 0.25, UnitOfMeasure.TONNE);
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

        Market wheatMarket = new Market(wheat);
        markets.add(wheatMarket);

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

        List<Person> personList = new ArrayList<>();

        for (int i=0; i<SIMULATED_WHEAT_FARMING; i++) {
            Person johnDoe = new Person("John", "Doe #" + i);
            personList.add(johnDoe);
            agents.add(new Household(List.of(johnDoe)));
        }

        List<Organization> wheatFarmingList = new ArrayList<>();

        for (int i=0; i<SIMULATED_WHEAT_FARMING; i++) {
            wheatFarmingList.add(new Organization("Wheat Farming #" + i));
        }
        agents.addAll(wheatFarmingList);

        Organization externalAgent = new Organization("External Agent");
        agents.add(externalAgent);

        // EMPLOYMENT

        for (int i=0; i<SIMULATED_WHEAT_FARMING; i++) {
            wheatFarmingList.get(i).addEmployment(new Employment(personList.get(i), farmer, 12.0, 12.0));
        }

        // WHEAT FARMING ASSETS

        for (int i=0; i<SIMULATED_WHEAT_FARMING; i++) {
            wheatFarmingList.get(i).addPurchasedAgentAsset(new AgentAsset(wheatFarmingList.get(i), wheatSeeds));
            wheatFarmingList.get(i).addPurchasedAgentAsset(new AgentAsset(wheatFarmingList.get(i), farmingLand));
            wheatFarmingList.get(i).addPurchasedAgentAsset(new AgentAsset(wheatFarmingList.get(i), farmingTools));
            wheatFarmingList.get(i).addPurchasedAgentAsset(new AgentAsset(wheatFarmingList.get(i), farmingMachinery));

            wheatFarmingList.get(i).addProducedAgentAsset(new AgentAsset(wheatFarmingList.get(i), wheat));

            AgentAsset wheatFarmingDollar = new AgentAsset(wheatFarmingList.get(i), dollar);
            wheatFarmingList.get(i).addCurrencyAgentAsset(wheatFarmingDollar);
            wheatFarmingDollar.addAssetInventory(100000);
        }

        // EXTERNAL AGENT ASSETS

        AgentAsset externalAgentWheatSeeds = new AgentAsset(externalAgent, wheatSeeds);
        externalAgent.addPurchasedAgentAsset(externalAgentWheatSeeds);
        externalAgentWheatSeeds.addAssetInventory(1000000);

        AgentAsset externalAgentFarmingLand = new AgentAsset(externalAgent, farmingLand);
        externalAgent.addPurchasedAgentAsset(externalAgentFarmingLand);
        externalAgentFarmingLand.addAssetInventory(1000000);

        AgentAsset externalAgentFarmingTools = new AgentAsset(externalAgent, farmingTools);
        externalAgent.addPurchasedAgentAsset(externalAgentFarmingTools);
        externalAgentFarmingTools.addAssetInventory(1000000);

        AgentAsset externalAgentFarmingMachinery = new AgentAsset(externalAgent, farmingMachinery);
        externalAgent.addPurchasedAgentAsset(externalAgentFarmingMachinery);
        externalAgentFarmingMachinery.addAssetInventory(1000000);

        AgentAsset externalAgentWheat = new AgentAsset(externalAgent, wheat);
        externalAgent.addPurchasedAgentAsset(externalAgentWheat);
        externalAgentWheatSeeds.addAssetInventory(0);

        AgentAsset externalAgentDollar = new AgentAsset(externalAgent, dollar);
        externalAgent.addCurrencyAgentAsset(externalAgentDollar);
        externalAgentDollar.addAssetInventory(0);

        // EXTERNAL AGENT SUPPLY

        SupplyAssetInventory externalAgentWheatSeedsSupply = new SupplyAssetInventory(externalAgentWheatSeeds, externalAgentWheatSeeds.getAssetInventoryList().getFirst(), 1000000, 0.50);
        wheatSeedsMarket.addSupplyAssetInventory(externalAgentWheatSeedsSupply);

        SupplyAssetInventory externalAgentFarmingLandSupply = new SupplyAssetInventory(externalAgentFarmingLand, externalAgentFarmingLand.getAssetInventoryList().getFirst(), 1000000, 20000);
        farmingLandMarket.addSupplyAssetInventory(externalAgentFarmingLandSupply);

        SupplyAssetInventory externalAgentFarmingToolsSupply = new SupplyAssetInventory(externalAgentFarmingTools, externalAgentFarmingTools.getAssetInventoryList().getFirst(), 1000000, 800);
        farmingToolsMarket.addSupplyAssetInventory(externalAgentFarmingToolsSupply);

        SupplyAssetInventory externalAgentFarmingMachinerySupply = new SupplyAssetInventory(externalAgentFarmingMachinery, externalAgentFarmingMachinery.getAssetInventoryList().getFirst(), 1000000, 1000);
        farmingMachineryMarket.addSupplyAssetInventory(externalAgentFarmingMachinerySupply);

        // WHEAT FARMING DEMAND

        for (int i=0; i<SIMULATED_WHEAT_FARMING; i++) {
            wheatSeedsMarket.addDemandAgentAsset(new DemandAgentAsset(wheatFarmingList.get(i).getPurchasedAgentAsset(wheatSeeds), 1700, 0.50));
            farmingLandMarket.addDemandAgentAsset(new DemandAgentAsset(wheatFarmingList.get(i).getPurchasedAgentAsset(farmingLand), 1, 20000));
            farmingToolsMarket.addDemandAgentAsset(new DemandAgentAsset(wheatFarmingList.get(i).getPurchasedAgentAsset(farmingTools), 1, 800));
            farmingMachineryMarket.addDemandAgentAsset(new DemandAgentAsset(wheatFarmingList.get(i).getPurchasedAgentAsset(farmingMachinery), 1, 1000));
        }

        // SIMULATING 15 WEEKS/CYCLES

        for (int week=0; week<15; week++) {

            System.out.println("\n[WEEK " + week + "]");

            // PRODUCTION LINE

            for (int i=0; i<SIMULATED_WHEAT_FARMING; i++) {
                AgentAsset wheatFarmingWheat = wheatFarmingList.get(i).getProducedAgentAsset(wheat);
                wheatFarmingWheat.addProductionLine(new ProductionLine(wheatFarmingWheat, wheatProduction, 40));
            }

            // PRODUCTION

            System.out.println("\nPRODUCTION");

            for (int i=0; i<SIMULATED_WHEAT_FARMING; i++) {

                System.out.println("\t" + wheatFarmingList.get(i));
                wheatFarmingList.get(i).getProducedAgentAsset(wheat).produceAll();
            }

            // WHEAT FARMING SUPPLY

            for (int i=0; i<SIMULATED_WHEAT_FARMING; i++) {

                AgentAsset wheatFarmingWheat = wheatFarmingList.get(i).getProducedAgentAsset(wheat);

                for (AssetInventory wheatFarmingWheatInventory : wheatFarmingWheat.getAssetInventoryList()) {
                    if (wheatFarmingWheatInventory.getQuantity() > 0) {
                        double quantity = wheatFarmingWheatInventory.getQuantity();
                        double price = wheatFarmingWheatInventory.getMarginalCost();
                        wheatMarket.addSupplyAssetInventory(new SupplyAssetInventory(wheatFarmingWheat, wheatFarmingWheatInventory, quantity, price));
                    }
                }
            }

            // EXTERNAL AGENT DEMAND

            if (wheatMarket.getDemandAgentAssetList().isEmpty()) {
                DemandAgentAsset externalAgentWheatDemand = new DemandAgentAsset(externalAgentWheat, 12, 180.0);
                wheatMarket.addDemandAgentAsset(externalAgentWheatDemand);
            }

            // MARKET CLEARING

            System.out.println("\nMARKETS");
            for (Market market : markets) {
                market.clearMarketWithBackstop();
            }

        }

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