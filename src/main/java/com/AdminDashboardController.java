package com;

import javafx.application.Platform;
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
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Stage stage;

    @FXML
    private Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle resource) {

        try {
            borderPane.setCenter(FXMLLoader.load(getClass().getResource("panes/reports-view.fxml")));

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    @FXML
    public void loadFoodsPage(ActionEvent event) throws IOException {
        borderPane.setCenter(FXMLLoader.load(getClass().getResource("panes/foods-view.fxml")));
    }

    public void closeStage(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void ManageReport(ActionEvent event) throws IOException {
        borderPane.setCenter(FXMLLoader.load(getClass().getResource("panes/reports-view.fxml")));
    }

    public void loadUserPage(ActionEvent event) throws  IOException{
        borderPane.setCenter(FXMLLoader.load(getClass().getResource("panes/user-view.fxml")));
    }

    public void onSwitchUserMode(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("user-dashboard.fxml"))));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    public void onMinimize(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.setIconified(true);
    }
}
