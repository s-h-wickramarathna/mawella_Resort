module com.example.navigate {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.sql;
    requires com.jfoenix;
    requires jasperreports;

    opens com.example.navigate to javafx.fxml;
    exports com.example.navigate;
    exports com.example.navigate.database;
    opens com.example.navigate.database to javafx.fxml;
    opens com.example.navigate.model to javafx.base;
}