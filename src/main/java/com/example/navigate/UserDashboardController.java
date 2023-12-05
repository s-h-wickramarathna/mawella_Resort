package com.example.navigate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserDashboardController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private Stage stage;

    @FXML
    private Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle resource) {

        try {
            borderPane.setCenter(FXMLLoader.load(getClass().getResource("panes/invoice-view.fxml")));

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    public void closeStage(ActionEvent event) {
        System.exit(0);
    }

    public void loadInvoicePage(ActionEvent event) throws IOException {
        borderPane.setCenter(FXMLLoader.load(getClass().getResource("panes/invoice-view.fxml")));
    }

    public void goToAdminPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("admin-dashboard.fxml"))));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }
}
