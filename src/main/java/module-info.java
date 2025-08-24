module com.github.lorenzolacognata.simquity {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;

    exports com.github.lorenzolacognata.simquity;
    opens com.github.lorenzolacognata.simquity to javafx.fxml;
}