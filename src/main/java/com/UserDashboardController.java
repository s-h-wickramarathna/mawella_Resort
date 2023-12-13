package com;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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


    public void onLoginPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("JDialog/loginDialog.fxml"));

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root));
        stage.show();

        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void onMinimize(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.setIconified(true);
    }
}
