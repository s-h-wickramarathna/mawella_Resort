module com {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.sql;
    requires com.jfoenix;
    requires jasperreports;

    opens com to javafx.fxml;
    exports com;
    exports com.database;
    opens com.database to javafx.fxml;
    opens com.model to javafx.base;
}