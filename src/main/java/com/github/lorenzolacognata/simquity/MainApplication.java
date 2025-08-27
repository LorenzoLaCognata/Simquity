package com.github.lorenzolacognata.simquity;

import com.github.lorenzolacognata.simquity.agent.Agent;
import com.github.lorenzolacognata.simquity.agent.Household;
import com.github.lorenzolacognata.simquity.agent.Organization;
import com.github.lorenzolacognata.simquity.agent.Person;
import com.github.lorenzolacognata.simquity.asset.*;
import com.github.lorenzolacognata.simquity.inventory.AgentAsset;
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

        AssetProduction wheatProduction = new AssetProduction(38, 43, 16, 5.0);
        wheat.addAssetProductionList(wheatProduction);

        AssetRequirement wheatWheatSeedsRequirement = new AssetRequirement(wheatSeeds, 170.0, 0.0, 0.0);
        wheatProduction.addConsumableAssetRequirement(wheatWheatSeedsRequirement);

        AssetRequirement wheatFarmingLandRequirement = new AssetRequirement(farmingLand, 1.0, 0.0, 0.0);
        wheatProduction.addDurableAssetRequirement(wheatFarmingLandRequirement);

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
            }
        }

        final List<Agent> agents = new ArrayList<>();

        agents.add(
            new Organization("Wheat Farming")
        );
        agents.add(
            new Household(List.of(
                new Person("John", "Doe"),
                new Person("Jane", "Doe"))
            )
        );

        System.out.println("\nAGENTS:\n"+agents);

        List<Currency> currencies = assets.stream()
            .filter(a -> a instanceof Currency)
            .map(a -> (Currency) a)
            .toList();

        for (Agent agent : agents) {
            for (Currency currency : currencies) {
                agent.addPurchasedAgentAsset(new AgentAsset(currency));
            }
        }

        System.out.println("\nPURCHASED AGENT ASSET LIST:");
        for (Agent agent : agents) {
            System.out.println(agent + ":" + agent.getPurchasedAgentAssetList());
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