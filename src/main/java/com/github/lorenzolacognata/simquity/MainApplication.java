package com.github.lorenzolacognata.simquity;

import com.github.lorenzolacognata.simquity.agent.Agent;
import com.github.lorenzolacognata.simquity.agent.Household;
import com.github.lorenzolacognata.simquity.agent.Organization;
import com.github.lorenzolacognata.simquity.agent.Person;
import com.github.lorenzolacognata.simquity.asset.Asset;
import com.github.lorenzolacognata.simquity.asset.Currency;
import com.github.lorenzolacognata.simquity.asset.Good;
import com.github.lorenzolacognata.simquity.asset.UnitOfMeasure;
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

        final List<Asset> assets = new ArrayList<>();

        assets.add(
                new Good("Wheat Seeds", UnitOfMeasure.KILOGRAM)
        );
        assets.add(
                new Currency("Dollar")
        );

        final Label label = new Label();
        label.setText(agents + "\n\n" + assets);
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