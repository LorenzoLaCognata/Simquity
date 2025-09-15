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
        int SIMULATED_WHEAT_MILLING = 12;

        // JOBS

        Job farmer = new Job("Farmer");
        jobs.add(farmer);

        Job millWorker = new Job("Mill Worker");
        jobs.add(millWorker);

        // ASSETS

        Asset wheatSeeds = new Good("Wheat Seeds", 104, Double.NaN, UnitOfMeasure.KILOGRAM);
        assets.add(wheatSeeds);

        Asset farmingLand = new Good("Farming Land", 5200, Double.NaN, UnitOfMeasure.HECTARE);
        assets.add(farmingLand);

        Asset farmingTools = new Good("Farming Tools", 260, Double.NaN, UnitOfMeasure.UNIT);
        assets.add(farmingTools);

        Asset farmingMachinery = new Good("Farming Machinery", 520, Double.NaN, UnitOfMeasure.UNIT);
        assets.add(farmingMachinery);

        // TODO: review lifespan as with the original value (520) the marginal cost is always infinitesimal
        Asset industrialFlourMill = new Good("Industrial Flour Mill", 5, Double.NaN, UnitOfMeasure.UNIT);
        assets.add(industrialFlourMill);

        Asset flourBag = new Good("Flour Bag", 5200, Double.NaN, UnitOfMeasure.UNIT);
        assets.add(flourBag);

        Asset wheat = new Good("Wheat", 26, 0.25, UnitOfMeasure.TONNE);
        assets.add(wheat);

        Asset wheatFlour = new Good("Wheat Flour", 52, 0.40, UnitOfMeasure.KILOGRAM);
        assets.add(wheatFlour);

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

        Market industrialFlourMillMarket = new Market(industrialFlourMill);
        markets.add(industrialFlourMillMarket);

        Market flourBagMarket = new Market(flourBag);
        markets.add(flourBagMarket);

        Market wheatMarket = new Market(wheat);
        markets.add(wheatMarket);

        Market wheatFlourMarket = new Market(wheatFlour);
        markets.add(wheatFlourMarket);

        // ASSET PRODUCTION

            // WHEAT

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

            // WHEAT FLOUR

        AssetProduction wheatFlourProduction = new AssetProduction(Integer.MIN_VALUE, Integer.MAX_VALUE, 1, 720.0);
        wheatFlour.addAssetProductionList(wheatFlourProduction);

        AssetRequirement wheatFlourWheatRequirement = new AssetRequirement(wheat, 1.0, 0.0, 0.0);
        wheatFlourProduction.addConsumableAssetRequirement(wheatFlourWheatRequirement);

        AssetRequirement wheatFlourFlourBagRequirement = new AssetRequirement(flourBag, 29.0, 0.0, 0.0);
        wheatFlourProduction.addConsumableAssetRequirement(wheatFlourFlourBagRequirement);

        AssetRequirement wheatFlourIndustrialFlourMillRequirement = new AssetRequirement(industrialFlourMill, 0.05, 0.0, 0.0);
        wheatFlourProduction.addDurableAssetRequirement(wheatFlourIndustrialFlourMillRequirement);

        LaborRequirement wheatFlourMillWorkerRequirement = new LaborRequirement(millWorker, 0.05, 2.0);
        wheatFlourProduction.addLaborRequirement(wheatFlourMillWorkerRequirement);

        // AGENTS

            // WHEAT

        List<Person> wheatFarmingPeopleList = new ArrayList<>();

        for (int i=0; i<SIMULATED_WHEAT_FARMING; i++) {
            Person johnDoe = new Person("John", "Wheat Farmer #" + i);
            wheatFarmingPeopleList.add(johnDoe);
            agents.add(new Household(List.of(johnDoe)));
        }

        List<Organization> wheatFarmingList = new ArrayList<>();

        for (int i=0; i<SIMULATED_WHEAT_FARMING; i++) {
            wheatFarmingList.add(new Organization("Wheat Farming #" + i));
        }
        agents.addAll(wheatFarmingList);

            // WHEAT FLOUR

        List<Person> wheatMillingPeopleList = new ArrayList<>();

        for (int i=0; i<SIMULATED_WHEAT_MILLING; i++) {
            Person johnDoe = new Person("John", "Wheat Miller #" + i);
            wheatMillingPeopleList.add(johnDoe);
            agents.add(new Household(List.of(johnDoe)));
        }

        List<Organization> wheatMillingList = new ArrayList<>();

        for (int i=0; i<SIMULATED_WHEAT_MILLING; i++) {
            wheatMillingList.add(new Organization("Wheat Milling #" + i));
        }
        agents.addAll(wheatMillingList);

            // EXTERNAL AGENT

        Organization externalAgent = new Organization("External Agent");
        agents.add(externalAgent);

        // EMPLOYMENT

            // WHEAT

        for (int i=0; i<SIMULATED_WHEAT_FARMING; i++) {
            // TODO: review difference of cost / salary / hired worker
            wheatFarmingList.get(i).addEmployment(new Employment(wheatFarmingPeopleList.get(i), farmer, 12.0, 12.0));
        }

            // WHEAT FLOUR

        for (int i=0; i<SIMULATED_WHEAT_MILLING; i++) {
            // TODO: review difference of cost / salary / hired worker
            wheatMillingList.get(i).addEmployment(new Employment(wheatMillingPeopleList.get(i), millWorker, 14.0, 14.0));
        }

        // AGENT ASSETS

            // WHEAT

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

            // WHEAT FLOUR

        for (int i=0; i<SIMULATED_WHEAT_MILLING; i++) {
            wheatMillingList.get(i).addPurchasedAgentAsset(new AgentAsset(wheatMillingList.get(i), wheat));
            wheatMillingList.get(i).addPurchasedAgentAsset(new AgentAsset(wheatMillingList.get(i), industrialFlourMill));
            wheatMillingList.get(i).addPurchasedAgentAsset(new AgentAsset(wheatMillingList.get(i), flourBag));

            wheatMillingList.get(i).addProducedAgentAsset(new AgentAsset(wheatMillingList.get(i), wheatFlour));

            AgentAsset wheatMillingDollar = new AgentAsset(wheatMillingList.get(i), dollar);
            wheatMillingList.get(i).addCurrencyAgentAsset(wheatMillingDollar);
            wheatMillingDollar.addAssetInventory(100000);
        }

            // EXTERNAL AGENT

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

        AgentAsset externalAgentIndustrialFlourMill = new AgentAsset(externalAgent, industrialFlourMill);
        externalAgent.addPurchasedAgentAsset(externalAgentIndustrialFlourMill);
        externalAgentIndustrialFlourMill.addAssetInventory(1000000);

        AgentAsset externalAgentFlourBag = new AgentAsset(externalAgent, flourBag);
        externalAgent.addPurchasedAgentAsset(externalAgentFlourBag);
        externalAgentFlourBag.addAssetInventory(1000000);

        AgentAsset externalAgentWheat = new AgentAsset(externalAgent, wheat);
        externalAgent.addPurchasedAgentAsset(externalAgentWheat);
        // TODO: delete when confirmed it's not necessary
        //externalAgentWheat.addAssetInventory(0);

        AgentAsset externalAgentWheatFlour = new AgentAsset(externalAgent, wheatFlour);
        externalAgent.addPurchasedAgentAsset(externalAgentWheatFlour);
        // TODO: delete when confirmed it's not necessary
        //externalAgentWheatFlour.addAssetInventory(0);

        AgentAsset externalAgentDollar = new AgentAsset(externalAgent, dollar);
        externalAgent.addCurrencyAgentAsset(externalAgentDollar);
        externalAgentDollar.addAssetInventory(0);

        // EXTERNAL AGENT SUPPLY

        SupplyAssetInventory externalAgentFarmingLandSupply = new SupplyAssetInventory(externalAgentFarmingLand, externalAgentFarmingLand.getAssetInventoryList().getFirst(), 1000000, 20000);
        farmingLandMarket.addSupplyAssetInventory(externalAgentFarmingLandSupply);

        SupplyAssetInventory externalAgentFarmingToolsSupply = new SupplyAssetInventory(externalAgentFarmingTools, externalAgentFarmingTools.getAssetInventoryList().getFirst(), 1000000, 800);
        farmingToolsMarket.addSupplyAssetInventory(externalAgentFarmingToolsSupply);

        SupplyAssetInventory externalAgentFarmingMachinerySupply = new SupplyAssetInventory(externalAgentFarmingMachinery, externalAgentFarmingMachinery.getAssetInventoryList().getFirst(), 1000000, 1000);
        farmingMachineryMarket.addSupplyAssetInventory(externalAgentFarmingMachinerySupply);

        SupplyAssetInventory externalAgentIndustrialFlourMillSupply = new SupplyAssetInventory(externalAgentIndustrialFlourMill, externalAgentIndustrialFlourMill.getAssetInventoryList().getFirst(), 1000000, 2500);
        industrialFlourMillMarket.addSupplyAssetInventory(externalAgentIndustrialFlourMillSupply);

        // DEMAND

            // WHEAT

        for (int i=0; i<SIMULATED_WHEAT_FARMING; i++) {
            farmingLandMarket.addDemandAgentAsset(new DemandAgentAsset(wheatFarmingList.get(i).getPurchasedAgentAsset(farmingLand), 1, 20000));
            farmingToolsMarket.addDemandAgentAsset(new DemandAgentAsset(wheatFarmingList.get(i).getPurchasedAgentAsset(farmingTools), 1, 800));
            farmingMachineryMarket.addDemandAgentAsset(new DemandAgentAsset(wheatFarmingList.get(i).getPurchasedAgentAsset(farmingMachinery), 1, 1000));
        }

            // WHEAT FLOUR

        for (int i=0; i<SIMULATED_WHEAT_MILLING; i++) {
            industrialFlourMillMarket.addDemandAgentAsset(new DemandAgentAsset(wheatMillingList.get(i).getPurchasedAgentAsset(industrialFlourMill), 1, 2500));
        }

        // SIMULATING 15 WEEKS/CYCLES

        for (int week=0; week<16; week++) {

            System.out.println("\n[WEEK " + week + "]");

            // DEMAND

                // WHEAT

            for (int i=0; i<SIMULATED_WHEAT_FARMING; i++) {
                wheatSeedsMarket.addDemandAgentAsset(new DemandAgentAsset(wheatFarmingList.get(i).getPurchasedAgentAsset(wheatSeeds), 170, 0.50));
            }

                // WHEAT FLOUR

            for (int i=0; i<SIMULATED_WHEAT_MILLING; i++) {
                flourBagMarket.addDemandAgentAsset(new DemandAgentAsset(wheatMillingList.get(i).getPurchasedAgentAsset(flourBag), 29, 0.35));
                // TODO: review how to create Wheat demand based on Production needs for Wheat Milling
                wheatMarket.addDemandAgentAsset(new DemandAgentAsset(wheatMillingList.get(i).getPurchasedAgentAsset(wheat), 1, 180));
            }

            // SUPPLY

                // EXTERNAL AGENT

            SupplyAssetInventory externalAgentWheatSeedsSupply = new SupplyAssetInventory(externalAgentWheatSeeds, externalAgentWheatSeeds.getAssetInventoryList().getFirst(), 1000000, 0.50);
            wheatSeedsMarket.addSupplyAssetInventory(externalAgentWheatSeedsSupply);

            SupplyAssetInventory externalAgentFlourBagSupply = new SupplyAssetInventory(externalAgentFlourBag, externalAgentFlourBag.getAssetInventoryList().getFirst(), 1000000, 0.35);
            flourBagMarket.addSupplyAssetInventory(externalAgentFlourBagSupply);

            // PRODUCTION LINE

                // WHEAT

            for (int i=0; i<SIMULATED_WHEAT_FARMING; i++) {
                AgentAsset wheatFarmingWheat = wheatFarmingList.get(i).getProducedAgentAsset(wheat);
                wheatFarmingWheat.addProductionLine(new ProductionLine(wheatFarmingWheat, wheatProduction, 40));
            }

                // WHEAT FLOUR

            for (int i=0; i<SIMULATED_WHEAT_MILLING; i++) {
                AgentAsset wheatMillingWheatFlour = wheatMillingList.get(i).getProducedAgentAsset(wheatFlour);
                wheatMillingWheatFlour.addProductionLine(new ProductionLine(wheatMillingWheatFlour, wheatFlourProduction, 1));
            }

            // PRODUCTION

            System.out.println("\nPRODUCTION");

                // WHEAT

            for (int i=0; i<SIMULATED_WHEAT_FARMING; i++) {
                System.out.println("\t" + wheatFarmingList.get(i));
                wheatFarmingList.get(i).getProducedAgentAsset(wheat).produceAll();
            }

                // WHEAT FLOUR

            for (int i=0; i<SIMULATED_WHEAT_MILLING; i++) {
                System.out.println("\t" + wheatMillingList.get(i));
                wheatMillingList.get(i).getProducedAgentAsset(wheatFlour).produceAll();
            }

            // SUPPLY

                // WHEAT

            for (int i=0; i<SIMULATED_WHEAT_FARMING; i++) {

                AgentAsset wheatFarmingWheat = wheatFarmingList.get(i).getProducedAgentAsset(wheat);

                for (AssetInventory assetInventory : wheatFarmingWheat.getAssetInventoryList()) {
                    if (assetInventory.getQuantity() > 0) {
                        double quantity = assetInventory.getQuantity();
                        double price = assetInventory.getMarginalCost();
                        wheatMarket.addSupplyAssetInventory(new SupplyAssetInventory(wheatFarmingWheat, assetInventory, quantity, price));
                    }
                }
            }

                // WHEAT FLOUR

            // TODO: optimize and extract methods for these parts

            for (int i=0; i<SIMULATED_WHEAT_MILLING; i++) {

                AgentAsset wheatMillingWheatFlour = wheatMillingList.get(i).getProducedAgentAsset(wheatFlour);

                for (AssetInventory assetInventory : wheatMillingWheatFlour.getAssetInventoryList()) {
                    if (assetInventory.getQuantity() > 0) {
                        double quantity = assetInventory.getQuantity();
                        double price = assetInventory.getMarginalCost();
                        wheatFlourMarket.addSupplyAssetInventory(new SupplyAssetInventory(wheatMillingWheatFlour, assetInventory, quantity, price));
                    }
                }
            }

                // EXTERNAL AGENT

            if (wheatFlourMarket.getDemandAgentAssetList().isEmpty()) {
                // TODO: review as the original value (0.50) looks too low with the current marginal costs
                DemandAgentAsset externalAgentWheatFlourDemand = new DemandAgentAsset(externalAgentWheatFlour, 7500, 0.60);
                wheatFlourMarket.addDemandAgentAsset(externalAgentWheatFlourDemand);
            }

            // MARKET CLEARING

            System.out.println("\nMARKETS");

            for (Market market : markets) {

                System.out.println("\t" + market);

                if (!market.getSupplyAssetInventoryList().isEmpty()) {
                    System.out.println("\t\tInitial Supply:");
                    for (SupplyAssetInventory supplyAssetInventory : market.getSupplyAssetInventoryList()) {
                        System.out.println("\t\t\t" + supplyAssetInventory + ": " + supplyAssetInventory.getQuantity() + " @" + supplyAssetInventory.getMarginalCost());
                    }
                }
                if (!market.getDemandAgentAssetList().isEmpty()) {
                    System.out.println("\t\tInitial Demand:");
                    for (DemandAgentAsset demandAgentAsset : market.getDemandAgentAssetList()) {
                        System.out.println("\t\t\t" + demandAgentAsset + ": " + demandAgentAsset.getQuantity() + " @" + demandAgentAsset.getMaximumPrice());
                    }
                }

                market.clearMarketWithBackstop();

            }

        }

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