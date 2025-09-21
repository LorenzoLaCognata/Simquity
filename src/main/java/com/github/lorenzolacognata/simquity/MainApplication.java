package com.github.lorenzolacognata.simquity;

import com.github.lorenzolacognata.simquity.agent.Agent;
import com.github.lorenzolacognata.simquity.agent.Household;
import com.github.lorenzolacognata.simquity.agent.Organization;
import com.github.lorenzolacognata.simquity.agent.Person;
import com.github.lorenzolacognata.simquity.asset.*;
import com.github.lorenzolacognata.simquity.inventory.AgentAsset;
import com.github.lorenzolacognata.simquity.inventory.AssetInventory;
import com.github.lorenzolacognata.simquity.labor.JobType;
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
import java.util.Objects;

public class MainApplication extends Application {

    static final Pane root = new Pane();

    static final List<Job> jobs = new ArrayList<>();
    static final List<Asset> assets = new ArrayList<>();
    static final List<Agent> agents = new ArrayList<>();

    public static void main(String[] args) {

        int SIMULATED_WHEAT_FARMING = 3;
        int SIMULATED_WHEAT_MILLING = 12;

        // JOBS

        jobs.add(new Job(JobType.FARMER));
        jobs.add(new Job(JobType.MILL_WORKER));

        // ASSETS

        assets.add(new Good(AssetType.WHEAT_SEEDS, 104, 0.50, Double.NaN, UnitOfMeasure.KILOGRAM));
        assets.add(new Good(AssetType.FARMING_LAND, 5200, 20000.0, Double.NaN, UnitOfMeasure.HECTARE));
        assets.add(new Good(AssetType.FARMING_TOOLS, 260, 800.0, Double.NaN, UnitOfMeasure.UNIT));
        assets.add(new Good(AssetType.FARMING_MACHINERY, 520, 1000.0, Double.NaN, UnitOfMeasure.UNIT));
        assets.add(new Good(AssetType.INDUSTRIAL_FLOUR_MILL, 520, 15000.0, Double.NaN, UnitOfMeasure.UNIT));
        assets.add(new Good(AssetType.FLOUR_BAG, 5200, 0.35, Double.NaN, UnitOfMeasure.UNIT));
        assets.add(new Good(AssetType.WHEAT, 26, 180.0, 0.25, UnitOfMeasure.TONNE));
        assets.add(new Good(AssetType.WHEAT_FLOUR, 52, 0.50, 0.40, UnitOfMeasure.KILOGRAM));
        assets.add(new Currency(AssetType.DOLLAR));


        // ASSET PRODUCTION

        getAsset(AssetType.WHEAT).addAssetProductionList(new AssetProduction(38, 43, 16, 5.0));
        getAsset(AssetType.WHEAT_FLOUR).addAssetProductionList(new AssetProduction(Integer.MIN_VALUE, Integer.MAX_VALUE, 1, 720.0));

        // ASSET REQUIREMENT

            // WHEAT

        getAsset(AssetType.WHEAT).getAssetProductionList().getFirst().addConsumableAssetRequirement(new AssetRequirement(getAsset(AssetType.WHEAT_SEEDS), 170.0, 0.0, 0.0));
        getAsset(AssetType.WHEAT).getAssetProductionList().getFirst().addDurableAssetRequirement(new AssetRequirement(getAsset(AssetType.FARMING_LAND), 1.0, 0.0, 0.0));
        getAsset(AssetType.WHEAT).getAssetProductionList().getFirst().addDurableAssetRequirement(new AssetRequirement(getAsset(AssetType.FARMING_TOOLS), 0.2, 0.0, 0.0));
        getAsset(AssetType.WHEAT).getAssetProductionList().getFirst().addDurableAssetRequirement(new AssetRequirement(getAsset(AssetType.FARMING_MACHINERY), 0.2, 0.0, 0.0));
        getAsset(AssetType.WHEAT).getAssetProductionList().getFirst().addLaborRequirement(new LaborRequirement(getJob(JobType.FARMER), 0.05, 40.0));

            // WHEAT FLOUR

        getAsset(AssetType.WHEAT_FLOUR).getAssetProductionList().getFirst().addConsumableAssetRequirement(new AssetRequirement(getAsset(AssetType.WHEAT), 1.0, 0.0, 0.0));
        getAsset(AssetType.WHEAT_FLOUR).getAssetProductionList().getFirst().addConsumableAssetRequirement(new AssetRequirement(getAsset(AssetType.FLOUR_BAG), 29.0, 0.0, 0.0));
        getAsset(AssetType.WHEAT_FLOUR).getAssetProductionList().getFirst().addDurableAssetRequirement(new AssetRequirement(getAsset(AssetType.INDUSTRIAL_FLOUR_MILL), 0.10, 0.0, 0.0));
        getAsset(AssetType.WHEAT_FLOUR).getAssetProductionList().getFirst().addLaborRequirement(new LaborRequirement(getJob(JobType.MILL_WORKER), 0.05, 2.0));

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
            wheatFarmingList.get(i).addEmployment(new Employment(wheatFarmingPeopleList.get(i), getJob(JobType.FARMER), 12.0, 12.0));
        }

            // WHEAT FLOUR

        for (int i=0; i<SIMULATED_WHEAT_MILLING; i++) {
            // TODO: review difference of cost / salary / hired worker
            wheatMillingList.get(i).addEmployment(new Employment(wheatMillingPeopleList.get(i), getJob(JobType.MILL_WORKER), 14.0, 14.0));
        }

        // AGENT ASSETS

            // WHEAT

        for (int i=0; i<SIMULATED_WHEAT_FARMING; i++) {
            wheatFarmingList.get(i).addPurchasedAgentAsset(new AgentAsset(wheatFarmingList.get(i), getAsset(AssetType.WHEAT_SEEDS)));
            wheatFarmingList.get(i).addPurchasedAgentAsset(new AgentAsset(wheatFarmingList.get(i), getAsset(AssetType.FARMING_LAND)));
            wheatFarmingList.get(i).addPurchasedAgentAsset(new AgentAsset(wheatFarmingList.get(i), getAsset(AssetType.FARMING_TOOLS)));
            wheatFarmingList.get(i).addPurchasedAgentAsset(new AgentAsset(wheatFarmingList.get(i), getAsset(AssetType.FARMING_MACHINERY)));
            wheatFarmingList.get(i).addProducedAgentAsset(new AgentAsset(wheatFarmingList.get(i), getAsset(AssetType.WHEAT)));
            AgentAsset wheatFarmingDollar = new AgentAsset(wheatFarmingList.get(i), getAsset(AssetType.DOLLAR));
            wheatFarmingList.get(i).addCurrencyAgentAsset(wheatFarmingDollar);
            wheatFarmingDollar.addAssetInventory(100000);
        }

            // WHEAT FLOUR

        for (int i=0; i<SIMULATED_WHEAT_MILLING; i++) {
            wheatMillingList.get(i).addPurchasedAgentAsset(new AgentAsset(wheatMillingList.get(i), getAsset(AssetType.WHEAT)));
            wheatMillingList.get(i).addPurchasedAgentAsset(new AgentAsset(wheatMillingList.get(i), getAsset(AssetType.INDUSTRIAL_FLOUR_MILL)));
            wheatMillingList.get(i).addPurchasedAgentAsset(new AgentAsset(wheatMillingList.get(i), getAsset(AssetType.FLOUR_BAG)));
            wheatMillingList.get(i).addProducedAgentAsset(new AgentAsset(wheatMillingList.get(i), getAsset(AssetType.WHEAT_FLOUR)));
            AgentAsset wheatMillingDollar = new AgentAsset(wheatMillingList.get(i), getAsset(AssetType.DOLLAR));
            wheatMillingList.get(i).addCurrencyAgentAsset(wheatMillingDollar);
            wheatMillingDollar.addAssetInventory(100000);
        }

            // EXTERNAL AGENT

        AgentAsset externalAgentWheatSeeds = new AgentAsset(externalAgent, getAsset(AssetType.WHEAT_SEEDS));
        externalAgent.addPurchasedAgentAsset(externalAgentWheatSeeds);
        externalAgentWheatSeeds.addAssetInventory(1000000, 0.50);

        AgentAsset externalAgentFarmingLand = new AgentAsset(externalAgent, getAsset(AssetType.FARMING_LAND));
        externalAgent.addPurchasedAgentAsset(externalAgentFarmingLand);
        externalAgentFarmingLand.addAssetInventory(1000000, 20000);

        AgentAsset externalAgentFarmingTools = new AgentAsset(externalAgent, getAsset(AssetType.FARMING_TOOLS));
        externalAgent.addPurchasedAgentAsset(externalAgentFarmingTools);
        externalAgentFarmingTools.addAssetInventory(1000000, 800);

        AgentAsset externalAgentFarmingMachinery = new AgentAsset(externalAgent, getAsset(AssetType.FARMING_MACHINERY));
        externalAgent.addPurchasedAgentAsset(externalAgentFarmingMachinery);
        externalAgentFarmingMachinery.addAssetInventory(1000000, 1000);

        AgentAsset externalAgentIndustrialFlourMill = new AgentAsset(externalAgent, getAsset(AssetType.INDUSTRIAL_FLOUR_MILL));
        externalAgent.addPurchasedAgentAsset(externalAgentIndustrialFlourMill);
        externalAgentIndustrialFlourMill.addAssetInventory(1000000, 15000);

        AgentAsset externalAgentFlourBag = new AgentAsset(externalAgent, getAsset(AssetType.FLOUR_BAG));
        externalAgent.addPurchasedAgentAsset(externalAgentFlourBag);
        externalAgentFlourBag.addAssetInventory(1000000, 0.35);

        AgentAsset externalAgentWheat = new AgentAsset(externalAgent, getAsset(AssetType.WHEAT));
        externalAgent.addPurchasedAgentAsset(externalAgentWheat);

        AgentAsset externalAgentWheatFlour = new AgentAsset(externalAgent, getAsset(AssetType.WHEAT_FLOUR));
        externalAgent.addPurchasedAgentAsset(externalAgentWheatFlour);

        AgentAsset externalAgentDollar = new AgentAsset(externalAgent, getAsset(AssetType.DOLLAR));
        externalAgent.addCurrencyAgentAsset(externalAgentDollar);
        externalAgentDollar.addAssetInventory(0);

        // EXTERNAL AGENT SUPPLY

        generateSupply(externalAgentFarmingLand, AssetType.FARMING_LAND);
        generateSupply(externalAgentFarmingLand, AssetType.FARMING_TOOLS);
        generateSupply(externalAgentFarmingLand, AssetType.FARMING_MACHINERY);
        generateSupply(externalAgentFarmingLand, AssetType.INDUSTRIAL_FLOUR_MILL);

        // DEMAND

            // WHEAT

        for (int i=0; i<SIMULATED_WHEAT_FARMING; i++) {
            getAsset(AssetType.FARMING_LAND).getMarket().addDemandAgentAsset(
                new DemandAgentAsset(wheatFarmingList.get(i).getPurchasedAgentAsset(getAsset(AssetType.FARMING_LAND)), 1, getAsset(AssetType.FARMING_LAND).getReferencePrice()));
            getAsset(AssetType.FARMING_TOOLS).getMarket().addDemandAgentAsset(
                    new DemandAgentAsset(wheatFarmingList.get(i).getPurchasedAgentAsset(getAsset(AssetType.FARMING_TOOLS)), 1, getAsset(AssetType.FARMING_TOOLS).getReferencePrice()));
            getAsset(AssetType.FARMING_MACHINERY).getMarket().addDemandAgentAsset(
                    new DemandAgentAsset(wheatFarmingList.get(i).getPurchasedAgentAsset(getAsset(AssetType.FARMING_MACHINERY)), 1, getAsset(AssetType.FARMING_MACHINERY).getReferencePrice()));
        }

            // WHEAT FLOUR

        for (int i=0; i<SIMULATED_WHEAT_MILLING; i++) {
            getAsset(AssetType.INDUSTRIAL_FLOUR_MILL).getMarket().addDemandAgentAsset(
                    new DemandAgentAsset(wheatMillingList.get(i).getPurchasedAgentAsset(getAsset(AssetType.INDUSTRIAL_FLOUR_MILL)), 1, getAsset(AssetType.INDUSTRIAL_FLOUR_MILL).getReferencePrice()));
        }

        // SIMULATING 15 WEEKS/CYCLES

        for (int week=0; week<16; week++) {

            System.out.println("\n[WEEK " + week + "]");

            // DEMAND

            generateDemand(wheatFarmingList);
            generateDemand(wheatMillingList);

            // SUPPLY

                // EXTERNAL AGENT

            for (AssetInventory assetInventory : externalAgentWheatSeeds.getAssetInventoryList()) {
                getAsset(AssetType.WHEAT_SEEDS).getMarket().addSupplyAssetInventory(new SupplyAssetInventory(externalAgentWheatSeeds, assetInventory, assetInventory.getQuantity(), assetInventory.getMarginalCost()));
            }

            for (AssetInventory assetInventory : externalAgentFlourBag.getAssetInventoryList()) {
                getAsset(AssetType.FLOUR_BAG).getMarket().addSupplyAssetInventory(new SupplyAssetInventory(externalAgentFlourBag, assetInventory, assetInventory.getQuantity(), assetInventory.getMarginalCost()));
            }

            // PRODUCTION LINE

                // WHEAT

            for (int i=0; i<SIMULATED_WHEAT_FARMING; i++) {
                AgentAsset wheatFarmingWheat = wheatFarmingList.get(i).getProducedAgentAsset(getAsset(AssetType.WHEAT));
                wheatFarmingWheat.addProductionLine(new ProductionLine(wheatFarmingWheat, getAsset(AssetType.WHEAT).getAssetProductionList().getFirst(), 40));
            }

                // WHEAT FLOUR

            for (int i=0; i<SIMULATED_WHEAT_MILLING; i++) {
                AgentAsset wheatMillingWheatFlour = wheatMillingList.get(i).getProducedAgentAsset(getAsset(AssetType.WHEAT_FLOUR));
                wheatMillingWheatFlour.addProductionLine(new ProductionLine(wheatMillingWheatFlour, getAsset(AssetType.WHEAT_FLOUR).getAssetProductionList().getFirst(), 1));
            }

            // PRODUCTION

            System.out.println("\nPRODUCTION");

                // WHEAT

            for (int i=0; i<SIMULATED_WHEAT_FARMING; i++) {
                System.out.println("\t" + wheatFarmingList.get(i));
                wheatFarmingList.get(i).getProducedAgentAsset(getAsset(AssetType.WHEAT)).produceAll();
            }

                // WHEAT FLOUR

            for (int i=0; i<SIMULATED_WHEAT_MILLING; i++) {
                System.out.println("\t" + wheatMillingList.get(i));
                wheatMillingList.get(i).getProducedAgentAsset(getAsset(AssetType.WHEAT_FLOUR)).produceAll();
            }

            // SUPPLY

            createSupplyAssetInventory(wheatFarmingList, getAsset(AssetType.WHEAT));
            createSupplyAssetInventory(wheatMillingList, getAsset(AssetType.WHEAT_FLOUR));

            // EXTERNAL AGENT

            if (getAsset(AssetType.WHEAT_FLOUR).getMarket().getDemandAgentAssetList().isEmpty()) {
                DemandAgentAsset externalAgentWheatFlourDemand = new DemandAgentAsset(externalAgentWheatFlour, 7500, 0.60);
                getAsset(AssetType.WHEAT_FLOUR).getMarket().addDemandAgentAsset(externalAgentWheatFlourDemand);
            }

            // MARKET CLEARING

            System.out.println("\nMARKETS");

            for (Asset asset : assets) {

                Market market = asset.getMarket();
                System.out.println("\t" + asset);

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

                for (Agent agent : agents) {
                    agent.cleanPurchasedAssetInventoryList();
                    agent.cleanProducedAssetInventoryList();
                }

            }

        }

        final Label label = new Label();
        label.setText("Simquity");
        root.getChildren().add(label);

        logging();

        launch();
    }

    private static void generateSupply(AgentAsset agentAsset, AssetType assetType) {
        for (AssetInventory assetInventory : agentAsset.getAssetInventoryList()) {
            getAsset(assetType).getMarket().addSupplyAssetInventory(new SupplyAssetInventory(agentAsset, assetInventory, assetInventory.getQuantity(), assetInventory.getMarginalCost()));
        }
    }

    private static Asset getAsset(AssetType assetType) {
        return Objects.requireNonNull(assets.stream().filter(item -> item.getAssetType() == assetType).findFirst().orElse(null));
    }

    private static Job getJob(JobType jobType) {
        return Objects.requireNonNull(jobs.stream().filter(item -> item.getJobType() == jobType).findFirst().orElse(null));
    }

    private static void generateDemand(List<Organization> organizationList) {
        for (int i = 0; i< organizationList.size(); i++) {

            for (AgentAsset producedAgentAsset : organizationList.get(i).getProducedAgentAssetList()) {
                for (AssetProduction assetProduction : producedAgentAsset.getAsset().getAssetProductionList()) {
                    for (AssetRequirement consumableAssetRequirement : assetProduction.getConsumableAssetRequirementList()) {

                        Asset requiredAsset = consumableAssetRequirement.getAsset();
                        AgentAsset requiredAgentAsset = organizationList.get(i).getPurchasedAgentAsset(requiredAsset);
                        double inventoryQuantity = organizationList.get(i).getPurchasedAgentAsset(requiredAsset).getInventoryQuantity();
                        double missingQuantity = Math.max(consumableAssetRequirement.getInitialQuantity() - inventoryQuantity, 0.0);

                        if (missingQuantity > 0) {
                            double demandPrice = requiredAsset.getReferencePrice();
                            if (!requiredAsset.getMarket().getClearingPriceList().isEmpty()) {
                                demandPrice = requiredAsset.getMarket().getClearingPriceList().getLast();
                            }
                            requiredAsset.getMarket().addDemandAgentAsset(new DemandAgentAsset(requiredAgentAsset, missingQuantity, demandPrice));
                        }
                    }
                }
            }
        }
    }

    private static void createSupplyAssetInventory(List<Organization> organizationList, Asset asset) {

        for (Organization organization : organizationList) {
            AgentAsset agentAsset = organization.getProducedAgentAsset(asset);
            for (AssetInventory assetInventory : agentAsset.getAssetInventoryList()) {
                if (assetInventory.getQuantity() > 0) {
                    double quantity = assetInventory.getQuantity();
                    double price = assetInventory.getMarginalCost();
                    asset.getMarket().addSupplyAssetInventory(new SupplyAssetInventory(agentAsset, assetInventory, quantity, price));
                }
            }
        }

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
                        System.out.println("\t\t\t" + producedAgentAsset + ": " + producedAgentAsset.getInventoryQuantity());

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
                        System.out.println("\t\t\t" + purchasedAgentAsset + ": " + purchasedAgentAsset.getInventoryQuantity());
                    }
                }

                if (!agent.getCurrencyAgentAssetList().isEmpty()) {
                    System.out.println("\t\tCurrency Assets:");
                    for (AgentAsset currencyAgentAsset : agent.getCurrencyAgentAssetList()) {
                        System.out.println("\t\t\t" + currencyAgentAsset + ": " + currencyAgentAsset.getInventoryQuantity());
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