package com.github.lorenzolacognata.simquity;

import com.github.lorenzolacognata.simquity.agent.Agent;
import com.github.lorenzolacognata.simquity.agent.Household;
import com.github.lorenzolacognata.simquity.agent.Organization;
import com.github.lorenzolacognata.simquity.agent.Person;
import com.github.lorenzolacognata.simquity.asset.*;
import com.github.lorenzolacognata.simquity.inventory.AgentAsset;
import com.github.lorenzolacognata.simquity.inventory.AssetInventory;
import com.github.lorenzolacognata.simquity.labor.JobType;
import com.github.lorenzolacognata.simquity.market.Transaction;
import com.github.lorenzolacognata.simquity.production.AssetProduction;
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

    static List<Organization> wheatFarmingList;
    static List<Organization> wheatMillingList;
    static Organization externalAgent;

    static public boolean LOG_PRODUCTION = false;
    static public boolean LOG_MARKET_CLEARING = false;
    static public boolean LOG_ASSETS = false;
    static public boolean LOG_TRANSACTIONS = false;
    static public AssetType LOG_SELECTED_ASSET_TYPE = null;
    static public int WEEKS = 3;
    private static Label label;

    public static void main(String[] args) {

        initData();

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
        generateSupply(externalAgentFarmingTools, AssetType.FARMING_TOOLS);
        generateSupply(externalAgentFarmingMachinery, AssetType.FARMING_MACHINERY);
        generateSupply(externalAgentIndustrialFlourMill, AssetType.INDUSTRIAL_FLOUR_MILL);

        // DEMAND

            // WHEAT

        for (Organization organization : wheatFarmingList) {
            getAsset(AssetType.FARMING_LAND).getMarket().addDemandAgentAsset(
                new DemandAgentAsset(organization.getPurchasedAgentAsset(getAsset(AssetType.FARMING_LAND)), 1, getAsset(AssetType.FARMING_LAND).getReferencePrice()));
            getAsset(AssetType.FARMING_TOOLS).getMarket().addDemandAgentAsset(
                    new DemandAgentAsset(organization.getPurchasedAgentAsset(getAsset(AssetType.FARMING_TOOLS)), 1, getAsset(AssetType.FARMING_TOOLS).getReferencePrice()));
            getAsset(AssetType.FARMING_MACHINERY).getMarket().addDemandAgentAsset(
                    new DemandAgentAsset(organization.getPurchasedAgentAsset(getAsset(AssetType.FARMING_MACHINERY)), 1, getAsset(AssetType.FARMING_MACHINERY).getReferencePrice()));
        }

            // WHEAT FLOUR

        for (Organization organization : wheatMillingList) {
            getAsset(AssetType.INDUSTRIAL_FLOUR_MILL).getMarket().addDemandAgentAsset(
                    new DemandAgentAsset(organization.getPurchasedAgentAsset(getAsset(AssetType.INDUSTRIAL_FLOUR_MILL)), 1, getAsset(AssetType.INDUSTRIAL_FLOUR_MILL).getReferencePrice()));
        }


        // SIMULATION


        for (int week=1; week<=WEEKS; week++) {
            System.out.println("\n[WEEK " + week + "]");
            simulateDemand();
            simulateExternalSupply();
            simulateExternalDemand();
            simulateMarketClearing();
            simulateProduction();
            logging();
        }

        final Label label = new Label();
        label.setText("Simquity");
        root.getChildren().add(label);

        launch();
    }

    private static void simulateExternalDemand() {
        AgentAsset externalAgentWheatFlour = externalAgent.getPurchasedAgentAsset(getAsset(AssetType.WHEAT_FLOUR));
        if (getAsset(AssetType.WHEAT_FLOUR).getMarket().getDemandAgentAssetList().isEmpty()) {
            DemandAgentAsset externalAgentWheatFlourDemand = new DemandAgentAsset(externalAgentWheatFlour, 10800, 0.60);
            getAsset(AssetType.WHEAT_FLOUR).getMarket().addDemandAgentAsset(externalAgentWheatFlourDemand);
        }
    }

    private static void simulateExternalSupply() {

        AgentAsset externalAgentWheatSeeds = externalAgent.getPurchasedAgentAsset(getAsset(AssetType.WHEAT_SEEDS));
        for (AssetInventory assetInventory : externalAgentWheatSeeds.getAssetInventoryList()) {
            getAsset(AssetType.WHEAT_SEEDS).getMarket().addSupplyAssetInventory(new SupplyAssetInventory(externalAgentWheatSeeds, assetInventory, assetInventory.getQuantity(), assetInventory.getMarginalCost()));
        }

        AgentAsset externalAgentFlourBag = externalAgent.getPurchasedAgentAsset(getAsset(AssetType.FLOUR_BAG));
        for (AssetInventory assetInventory : externalAgentFlourBag.getAssetInventoryList()) {
            getAsset(AssetType.FLOUR_BAG).getMarket().addSupplyAssetInventory(new SupplyAssetInventory(externalAgentFlourBag, assetInventory, assetInventory.getQuantity(), assetInventory.getMarginalCost()));
        }

    }

    private static void simulateDemand() {
        generateDemand(wheatFarmingList);
        generateDemand(wheatMillingList);
    }

    private static void simulateMarketClearing() {

        if (LOG_MARKET_CLEARING) {
            System.out.println("\nMARKETS");
        }

        for (Asset asset : assets) {

            Market market = asset.getMarket();

            if (LOG_MARKET_CLEARING & (asset.getAssetType() == LOG_SELECTED_ASSET_TYPE || LOG_SELECTED_ASSET_TYPE == null)) {
                System.out.println("\t" + asset);
            }

            market.clearMarketWithBackstop();

            for (Agent agent : agents) {
                agent.cleanPurchasedAssetInventoryList();
                agent.cleanProducedAssetInventoryList();
            }

        }
    }

    private static void simulateProduction() {

        // PRODUCTION LINE

            // WHEAT

        for (Organization organization : wheatFarmingList) {
            AgentAsset wheatFarmingWheat = organization.getProducedAgentAsset(getAsset(AssetType.WHEAT));
            wheatFarmingWheat.addProductionLine(new ProductionLine(wheatFarmingWheat, getAsset(AssetType.WHEAT).getAssetProductionList().getFirst(), 40));
        }

            // WHEAT FLOUR

        for (Organization organization : wheatMillingList) {
            AgentAsset wheatMillingWheatFlour = organization.getProducedAgentAsset(getAsset(AssetType.WHEAT_FLOUR));
            wheatMillingWheatFlour.addProductionLine(new ProductionLine(wheatMillingWheatFlour, getAsset(AssetType.WHEAT_FLOUR).getAssetProductionList().getFirst(), 1));
        }


        // PRODUCTION

        if (LOG_PRODUCTION) {
            System.out.println("\nPRODUCTION");
        }

            // WHEAT

        for (Organization organization : wheatFarmingList) {
            if (LOG_PRODUCTION) {
                System.out.println("\t" + organization);
            }
            organization.getProducedAgentAsset(getAsset(AssetType.WHEAT)).produceAll();
        }

            // WHEAT FLOUR

        for (Organization organization : wheatMillingList) {
            if (LOG_PRODUCTION) {
                System.out.println("\t" + organization);
            }
            organization.getProducedAgentAsset(getAsset(AssetType.WHEAT_FLOUR)).produceAll();
        }

        // SUPPLY

        createSupplyAssetInventory(wheatFarmingList, getAsset(AssetType.WHEAT));
        createSupplyAssetInventory(wheatMillingList, getAsset(AssetType.WHEAT_FLOUR));

    }

    private static void initData() {
        initJobs();
        initAssets();
        initAssetProduction();
        initAssetRequirement();
        initAgents();
        initAgentAssets();
    }

    private static void initJobs() {
        jobs.add(new Job(JobType.FARMER));
        jobs.add(new Job(JobType.MILL_WORKER));
    }

    private static void initAssets() {
        assets.add(new Good(AssetType.WHEAT_SEEDS, 104, 0.50, Double.NaN, UnitOfMeasure.KILOGRAM));
        assets.add(new Good(AssetType.FARMING_LAND, 5200, 20000.0, Double.NaN, UnitOfMeasure.HECTARE));
        assets.add(new Good(AssetType.FARMING_TOOLS, 260, 800.0, Double.NaN, UnitOfMeasure.UNIT));
        assets.add(new Good(AssetType.FARMING_MACHINERY, 520, 1000.0, Double.NaN, UnitOfMeasure.UNIT));
        assets.add(new Good(AssetType.INDUSTRIAL_FLOUR_MILL, 520, 15000.0, Double.NaN, UnitOfMeasure.UNIT));
        assets.add(new Good(AssetType.FLOUR_BAG, 5200, 0.35, Double.NaN, UnitOfMeasure.UNIT));
        assets.add(new Good(AssetType.WHEAT, 26, 180.0, 0.25, UnitOfMeasure.TONNE));
        assets.add(new Good(AssetType.WHEAT_FLOUR, 52, 0.50, 0.40, UnitOfMeasure.KILOGRAM));
        assets.add(new Currency(AssetType.DOLLAR));
    }

    private static void initAssetProduction() {
        getAsset(AssetType.WHEAT).addAssetProductionList(new AssetProduction(38, 43, 16, 5.0));
        getAsset(AssetType.WHEAT_FLOUR).addAssetProductionList(new AssetProduction(Integer.MIN_VALUE, Integer.MAX_VALUE, 1, 720.0));
    }

    private static void initAssetRequirement() {

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
    }

    private static void generateWheatFarmingAssets(Organization organization) {
        organization.addPurchasedAgentAsset(new AgentAsset(organization, getAsset(AssetType.WHEAT_SEEDS)));
        organization.addPurchasedAgentAsset(new AgentAsset(organization, getAsset(AssetType.FARMING_LAND)));
        organization.addPurchasedAgentAsset(new AgentAsset(organization, getAsset(AssetType.FARMING_TOOLS)));
        organization.addPurchasedAgentAsset(new AgentAsset(organization, getAsset(AssetType.FARMING_MACHINERY)));
        organization.addProducedAgentAsset(new AgentAsset(organization, getAsset(AssetType.WHEAT)));
        organization.addCurrencyAgentAsset(new AgentAsset(organization, getAsset(AssetType.DOLLAR)));
        organization.getCurrencyAgentAsset(getAsset(AssetType.DOLLAR)).addAssetInventory(100000);
    }

    private static void generateWheatMillingAssets(Organization organization) {
        organization.addPurchasedAgentAsset(new AgentAsset(organization, getAsset(AssetType.WHEAT)));
        organization.addPurchasedAgentAsset(new AgentAsset(organization, getAsset(AssetType.INDUSTRIAL_FLOUR_MILL)));
        organization.addPurchasedAgentAsset(new AgentAsset(organization, getAsset(AssetType.FLOUR_BAG)));
        organization.addProducedAgentAsset(new AgentAsset(organization, getAsset(AssetType.WHEAT_FLOUR)));
        organization.addCurrencyAgentAsset(new AgentAsset(organization, getAsset(AssetType.DOLLAR)));
        organization.getCurrencyAgentAsset(getAsset(AssetType.DOLLAR)).addAssetInventory(100000);
    }

    private static void initAgentAssets() {
        for (Organization organization : wheatFarmingList) {
            generateWheatFarmingAssets(organization);
        }
        for (Organization organization : wheatMillingList) {
            generateWheatMillingAssets(organization);
        }
    }

    private static List<Organization> generateOrganizations(int count, JobType jobType, double cost, double salary) {

        List<Organization> organizationList = new ArrayList<>();

        for (int i = 0; i< count; i++) {
            Organization organization = new Organization(jobType.toString() + " #" + i);
            organizationList.add(organization);
            agents.add(organization);

            Person person = new Person("John", "Doe");
            agents.add(new Household(List.of(person)));

            // TODO: review difference of cost / salary / hired worker
            organization.addEmployment(new Employment(person, getJob(jobType), cost, salary));
        }

        return organizationList;
    }

    private static void initAgents() {
        wheatFarmingList = generateOrganizations(3, JobType.FARMER, 12.0, 12.0);
        wheatMillingList = generateOrganizations(15, JobType.MILL_WORKER, 14.0, 14.0);

        externalAgent = new Organization("External Agent");
        agents.add(externalAgent);
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
        for (Organization organization : organizationList) {

            // TODO: check current inventory for the produced product, and reduce production if there is already some unsold inventory
            for (AgentAsset producedAgentAsset : organization.getProducedAgentAssetList()) {
                for (AssetProduction assetProduction : producedAgentAsset.getAsset().getAssetProductionList()) {
                    for (AssetRequirement consumableAssetRequirement : assetProduction.getConsumableAssetRequirementList()) {

                        Asset requiredAsset = consumableAssetRequirement.getAsset();
                        AgentAsset requiredAgentAsset = organization.getPurchasedAgentAsset(requiredAsset);
                        double inventoryQuantity = organization.getPurchasedAgentAsset(requiredAsset).getInventoryQuantity();
                        double missingQuantity = Math.max(consumableAssetRequirement.getInitialQuantity() - inventoryQuantity, 0.0);

                        if (missingQuantity > 0) {
                            double demandPrice = requiredAsset.getReferencePrice();
                            if (!requiredAsset.getMarket().getClearingPriceList().isEmpty()) {
                                demandPrice = (requiredAsset.getReferencePrice() * 0.5) + (requiredAsset.getMarket().getClearingPriceList().getLast() * 0.5);
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

    public static void logif(boolean logging_flag, AssetType assetType, String string) {
        if (logging_flag & (assetType == MainApplication.LOG_SELECTED_ASSET_TYPE || MainApplication.LOG_SELECTED_ASSET_TYPE == null)) {
            System.out.println(string);
        }
    }

    public static void logging() {

        System.out.println("ASSETS");

        for (Asset asset : assets) {

            System.out.println("\t" + asset);

            if (LOG_PRODUCTION & (asset.getAssetType() == MainApplication.LOG_SELECTED_ASSET_TYPE || MainApplication.LOG_SELECTED_ASSET_TYPE == null)) {
                if (!asset.getAssetProductionList().isEmpty()) {
                    System.out.println("\t\tProduction:");
                    for (AssetProduction assetProduction : asset.getAssetProductionList()) {
                        if (asset instanceof Good) {
                            System.out.println("\t\t\tOutput Qty: " + assetProduction.getOutputQuantity() + " " + ((Good) asset).getUnitOfMeasure());
                        } else {
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

            if (!asset.getMarket().getTransactionList().isEmpty()) {
                logif(LOG_TRANSACTIONS, asset.getAssetType(), "\t\tTransactions:");
                double inputQuantity = 0;
                double outputQuantity = 0;
                for (Transaction transaction : asset.getMarket().getTransactionList()) {
                    inputQuantity += transaction.getInputQuantity();
                    outputQuantity += transaction.getOutputQuantity();
                    logif(LOG_TRANSACTIONS, asset.getAssetType(), "\t\t\t" + transaction.getInputAgent() + " (" + transaction.getInputQuantity() +") <-> " + transaction.getOutputAgent() + " (" + transaction.getOutputQuantity()+ " " + transaction.getOutputAsset() + ")");
                }
                System.out.println("\t\tSold " + inputQuantity + " for " + outputQuantity/inputQuantity);
            }
        }

        System.out.println("\nAGENTS");

        for (Agent agent : agents) {

            if (agent instanceof Organization) {

                System.out.println("\t" + agent);

                if (!agent.getProducedAgentAssetList().isEmpty()) {
                    for (AgentAsset producedAgentAsset : agent.getProducedAgentAssetList()) {
                        if ((producedAgentAsset.getAsset().getAssetType() == MainApplication.LOG_SELECTED_ASSET_TYPE || MainApplication.LOG_SELECTED_ASSET_TYPE == null)) {
                            boolean testQuantity = true;
                            System.out.println("\t\t" + producedAgentAsset + ": " + producedAgentAsset.getInventoryQuantity());

                            if (((Organization) agent).getName().startsWith("Farmer")) {
                                switch (producedAgentAsset.getAsset().getAssetType()) {
                                    case WHEAT -> {
                                        if (producedAgentAsset.getInventoryQuantity() < 5 || producedAgentAsset.getInventoryQuantity() > 6) testQuantity = false;
                                    }
                                    default -> { }
                                }
                            }
                            else if (((Organization) agent).getName().startsWith("Mill Worker")) {
                                switch (producedAgentAsset.getAsset().getAssetType()) {
                                    case WHEAT_FLOUR -> {
                                        if (producedAgentAsset.getInventoryQuantity() > 0) testQuantity = false;
                                    }
                                    default -> { }
                                }
                            }
                            if (!testQuantity) {
                                System.out.println("\t\t\tERROR!");
                            }
                        }

                        /*
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
                        */
                    }
                }

                if (!agent.getPurchasedAgentAssetList().isEmpty()) {
                    for (AgentAsset purchasedAgentAsset : agent.getPurchasedAgentAssetList()) {
                        if ((purchasedAgentAsset.getAsset().getAssetType() == MainApplication.LOG_SELECTED_ASSET_TYPE || MainApplication.LOG_SELECTED_ASSET_TYPE == null)) {
                            boolean testQuantity = true;
                            System.out.println("\t\t" + purchasedAgentAsset + ": " + purchasedAgentAsset.getInventoryQuantity());

                            if (((Organization) agent).getName().startsWith("Farmer")) {
                                switch (purchasedAgentAsset.getAsset().getAssetType()) {
                                    case FARMING_LAND, FARMING_MACHINERY, FARMING_TOOLS -> {
                                        if (purchasedAgentAsset.getInventoryQuantity() != 1) testQuantity = false;
                                    }
                                    case WHEAT_SEEDS -> {
                                        if (purchasedAgentAsset.getInventoryQuantity() >= 170) testQuantity = false;
                                    }
                                    default -> { }
                                }
                            }
                            else if (((Organization) agent).getName().startsWith("Mill Worker")) {
                                switch (purchasedAgentAsset.getAsset().getAssetType()) {
                                    case INDUSTRIAL_FLOUR_MILL -> {
                                        if (purchasedAgentAsset.getInventoryQuantity() != 1) testQuantity = false;
                                    }
                                    case FLOUR_BAG -> {
                                        if (purchasedAgentAsset.getInventoryQuantity() >= 29) testQuantity = false;
                                    }
                                    case WHEAT -> {
                                        if (purchasedAgentAsset.getInventoryQuantity() >= 1) testQuantity = false;
                                    }
                                    default -> { }
                                }
                            }
                            if (!testQuantity) {
                                System.out.println("\t\t\tERROR!");
                            }
                        }
                    }
                }

                if (!agent.getCurrencyAgentAssetList().isEmpty()) {
                    for (AgentAsset currencyAgentAsset : agent.getCurrencyAgentAssetList()) {
                        if ((currencyAgentAsset.getAsset().getAssetType() == MainApplication.LOG_SELECTED_ASSET_TYPE || MainApplication.LOG_SELECTED_ASSET_TYPE == null)) {
                            boolean testQuantity = true;
                            System.out.println("\t\t" + currencyAgentAsset + ": " + currencyAgentAsset.getInventoryQuantity());

                            if (((Organization) agent).getName().startsWith("Farmer")) {
                                switch (currencyAgentAsset.getAsset().getAssetType()) {
                                    case DOLLAR -> {
                                        if (currencyAgentAsset.getInventoryQuantity() == 0) testQuantity = false;
                                    }
                                    default -> { }
                                }
                            }
                            else if (((Organization) agent).getName().startsWith("Mill Worker")) {
                                switch (currencyAgentAsset.getAsset().getAssetType()) {
                                    case DOLLAR -> {
                                        if (currencyAgentAsset.getInventoryQuantity() == 0) testQuantity = false;
                                    }
                                    default -> { }
                                }
                            }
                            if (!testQuantity) {
                                System.out.println("\t\t\tERROR!");
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