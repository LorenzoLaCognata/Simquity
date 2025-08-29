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

        // JOBS

        final List<Job> jobs = new ArrayList<>();

        Job farmer = new Job("Farmer");
        jobs.add(farmer);

        // ASSETS

        final List<Asset> assets = new ArrayList<>();

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

        final List<Agent> agents = new ArrayList<>();

        Person johnDoe = new Person("John", "Doe");
        Person janeDoe = new Person("Jane", "Doe");

        agents.add(
            new Household(List.of(johnDoe, janeDoe))
        );

        Organization wheatFarming = new Organization("Wheat Farming");
        agents.add(wheatFarming);

        // EMPLOYMENT

        Employment johnDoeWheatFarming = new Employment(johnDoe, farmer, 12.0, 12.0);
        wheatFarming.addEmployment(johnDoeWheatFarming);

        // AGENT ASSETS

        AgentAsset wheatFarmingWheatSeeds = new AgentAsset(wheatSeeds);
        wheatFarming.addPurchasedAgentAsset(wheatFarmingWheatSeeds);

        AgentAsset wheatFarmingFarmingLand = new AgentAsset(farmingLand);
        wheatFarming.addPurchasedAgentAsset(wheatFarmingFarmingLand);

        AgentAsset wheatFarmingFarmingTools = new AgentAsset(farmingTools);
        wheatFarming.addPurchasedAgentAsset(wheatFarmingFarmingTools);

        AgentAsset wheatFarmingFarmingMachinery = new AgentAsset(farmingMachinery);
        wheatFarming.addPurchasedAgentAsset(wheatFarmingFarmingMachinery);

        AgentAsset wheatFarmingDollar = new AgentAsset(dollar);
        wheatFarming.addPurchasedAgentAsset(wheatFarmingDollar);

        AgentAsset wheatFarmingWheat = new AgentAsset(wheat);
        wheatFarming.addProducedAgentAsset(wheatFarmingWheat);

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





        //
        System.out.println("----------");

        System.out.println("\nAGENTS");
        for (Agent agent : agents) {
            System.out.println(agent);
            if (agent instanceof Organization) {
                List<Employment> employments = ((Organization) agent).getEmploymentList();
                if (!employments.isEmpty()) {
                    System.out.println("\tEmployments:");
                    for (Employment employment : employments) {
                        System.out.println("\t\t" + employment);
                    }
                }
                if (!agent.getProducedAgentAssetList().isEmpty()) {
                    System.out.println("\tProduced Assets:");
                    for (AgentAsset producedAgentAsset : agent.getProducedAgentAssetList()) {
                        System.out.println("\t\t" + producedAgentAsset);
                    }
                }
                if (!agent.getPurchasedAgentAssetList().isEmpty()) {
                    System.out.println("\tPurchase Assets:");
                    for (AgentAsset purchasedAgentAsset : agent.getPurchasedAgentAssetList()) {
                        System.out.println("\t\t" + purchasedAgentAsset);
                    }
                }
            }
        }


        ///////


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