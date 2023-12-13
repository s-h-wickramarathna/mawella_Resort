package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class InitialController extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InitialController.class.getResource("user-dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        String imagePath = "/images/Hotel-logo.png";
        URL imageUrl = getClass().getResource(imagePath);
        Image image = new Image(imageUrl.toExternalForm());
        stage.getIcons().add(image);
        stage.setTitle("User Dashboard");
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}