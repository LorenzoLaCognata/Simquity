package com.github.lorenzolacognata.simquity;

import com.github.lorenzolacognata.simquity.agent.Agent;
import com.github.lorenzolacognata.simquity.agent.Household;
import com.github.lorenzolacognata.simquity.agent.Organization;
import com.github.lorenzolacognata.simquity.agent.Person;
import com.github.lorenzolacognata.simquity.asset.*;
import com.github.lorenzolacognata.simquity.inventory.AgentAsset;
import com.github.lorenzolacognata.simquity.inventory.AssetInventory;
import com.github.lorenzolacognata.simquity.inventory.ProductionLine;
import com.github.lorenzolacognata.simquity.labor.Employment;
import com.github.lorenzolacognata.simquity.labor.Job;
import com.github.lorenzolacognata.simquity.labor.LaborRequirement;
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

    public static void main(String[] args) {


        final List<Job> jobs = new ArrayList<>();

        Job farmer = new Job("Farmer");
        jobs.add(farmer);

        System.out.println("\nJOBS:\n"+farmer);



        final List<Asset> assets = new ArrayList<>();

        Asset wheatSeeds = new Good("Wheat Seeds", 104, UnitOfMeasure.KILOGRAM);
        assets.add(wheatSeeds);

        Asset farmingLand = new Good("Farming Land", 5200, UnitOfMeasure.HECTARE);
        assets.add(farmingLand);

        Asset wheat = new Good("Wheat", 26, UnitOfMeasure.TONNE);
        assets.add(wheat);

        Asset dollar = new Currency("Dollar");
        assets.add(dollar);

        System.out.println("\nASSETS:\n"+assets);



        final List<Agent> agents = new ArrayList<>();

        Person johnDoe = new Person("John", "Doe");
        Person janeDoe = new Person("Jane", "Doe");

        agents.add(
            new Household(List.of(johnDoe, janeDoe))
        );

        Organization wheatFarming = new Organization("Wheat Farming");
        agents.add(wheatFarming);

        System.out.println("\nAGENTS:\n"+agents);


        Employment johnDoeWheatFarming = new Employment(johnDoe, farmer, 12.0, 12.0);
        wheatFarming.addEmployment(johnDoeWheatFarming);

        System.out.println("\nEMPLOYMENTS:");

        for (Agent agent : agents) {
            if (agent instanceof Organization) {
                System.out.println(agent + ": " + ((Organization) agent).getEmploymentList());
            }
        }


        List<Currency> currencies = assets.stream()
                .filter(a -> a instanceof Currency)
                .map(a -> (Currency) a)
                .toList();

        for (Agent agent : agents) {
            for (Currency currency : currencies) {
                agent.addPurchasedAgentAsset(new AgentAsset(currency));
            }
        }

        AgentAsset wheatFarmingWheatSeeds = new AgentAsset(wheatSeeds);
        wheatFarming.addPurchasedAgentAsset(wheatFarmingWheatSeeds);

        AgentAsset wheatFarmingFarmingLand = new AgentAsset(farmingLand);
        wheatFarming.addPurchasedAgentAsset(wheatFarmingFarmingLand);

        System.out.println("\nPURCHASED AGENT ASSET LIST:");
        for (Agent agent : agents) {
            System.out.println(agent + ":" + agent.getPurchasedAgentAssetList());
        }



        AgentAsset wheatFarmingWheat = new AgentAsset(wheat);
        wheatFarming.addProducedAgentAsset(wheatFarmingWheat);

        System.out.println("\nPRODUCED AGENT ASSET LIST:");
        for (Agent agent : agents) {
        System.out.println(agent + ":" + agent.getProducedAgentAssetList());
        }



        AssetProduction wheatProduction = new AssetProduction(38, 43, 16, 5.0);
        wheat.addAssetProductionList(wheatProduction);

        AssetRequirement wheatWheatSeedsRequirement = new AssetRequirement(wheatSeeds, 170.0, 0.0, 0.0);
        wheatProduction.addConsumableAssetRequirement(wheatWheatSeedsRequirement);

        AssetRequirement wheatFarmingLandRequirement = new AssetRequirement(farmingLand, 1.0, 0.0, 0.0);
        wheatProduction.addDurableAssetRequirement(wheatFarmingLandRequirement);

        LaborRequirement wheatFarmerRequirement = new LaborRequirement(farmer, 0.05, 40.0);
        wheatProduction.addLaborRequirement(wheatFarmerRequirement);

        System.out.println("\nASSET PRODUCTION:");
        for (Asset asset : assets) {
            System.out.println(asset);
            for (AssetProduction assetProduction : asset.getAssetProductionList()) {
                System.out.println(assetProduction);
                for (AssetRequirement consumableAssetRequirement : assetProduction.getConsumableAssetRequirementList()) {
                    System.out.println(consumableAssetRequirement);
                }
                for (AssetRequirement durableAssetRequirement : assetProduction.getDurableAssetRequirementList()) {
                    System.out.println(durableAssetRequirement);
                }
                for (LaborRequirement laborRequirement : assetProduction.getLaborRequirementList()) {
                    System.out.println(laborRequirement);
                }
            }
        }



        ProductionLine wheatProductionLine = new ProductionLine(wheatProduction, 40);
        wheatFarmingWheat.addProductionLine(wheatProductionLine);

        AssetInventory wheatFarmingWheatSeedsInventory = wheatFarmingWheatSeeds.getAssetInventoryList().getFirst();
        wheatProductionLine.addConsumableAssetInventory(wheatFarmingWheatSeedsInventory);

        AssetInventory wheatFarmingFarmingLandInventory = wheatFarmingFarmingLand.getAssetInventoryList().getFirst();
        wheatProductionLine.addDurableAssetInventory(wheatFarmingFarmingLandInventory);

        wheatProductionLine.addEmployment(johnDoeWheatFarming);

        System.out.println("\nAGENT ASSET PRODUCTION LINE:");
        for (Agent agent : agents) {
            for (AgentAsset agentAsset : agent.getProducedAgentAssetList()) {
                System.out.println(agentAsset);
                for (ProductionLine assetProductionLine : agentAsset.getProductionLineList()) {
                    System.out.println(assetProductionLine);
                    for (AssetInventory consumableAssetInventory : assetProductionLine.getConsumableAssetInventoryList()) {
                        System.out.println(consumableAssetInventory.getAsset() + ": " + consumableAssetInventory);
                    }
                    for (AssetInventory durableAssetInventory : assetProductionLine.getDurableAssetInventoryList()) {
                        System.out.println(durableAssetInventory.getAsset() + ": " + durableAssetInventory);
                    }
                    for (Employment employment : assetProductionLine.getEmploymentList()) {
                        System.out.println(employment);
                    }
                }
            }
        }



        final Label label = new Label();
        label.setText("Simquity");
        root.getChildren().add(label);

        launch();
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