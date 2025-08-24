package com.github.lorenzolacognata.simquity;

import com.github.lorenzolacognata.simquity.agent.Agent;
import com.github.lorenzolacognata.simquity.agent.Household;
import com.github.lorenzolacognata.simquity.agent.Organization;
import com.github.lorenzolacognata.simquity.agent.Person;
import com.github.lorenzolacognata.simquity.asset.Asset;
import com.github.lorenzolacognata.simquity.asset.Currency;
import com.github.lorenzolacognata.simquity.asset.Good;
import com.github.lorenzolacognata.simquity.asset.UnitOfMeasure;
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

        assets.add(
                new Good("Wheat Seeds", 104, UnitOfMeasure.KILOGRAM)
        );
        assets.add(
                new Currency("Dollar")
        );

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

        List<Currency> currencies = assets.stream()
            .filter(a -> a instanceof Currency)
            .map(a -> (Currency) a)
            .toList();

        for (Agent agent : agents) {
            for (Currency currency : currencies) {
                agent.addPurchasedAgentAsset(new AgentAsset(currency));
            }
        }

        final Label label = new Label();
        label.setText(label.getText() + "\n\n" + assets);
        label.setText(label.getText() + "\n\n" + agents);
        for (Agent agent : agents) {
            label.setText(label.getText() + "\n\n" + agent + ":" + agent.getPurchasedAgentAssetList());
        }
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