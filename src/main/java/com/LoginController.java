package com;

import com.database.MySQL;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.ResourceBundle;
import org.mindrot.jbcrypt.BCrypt;

public class LoginController implements Initializable {


    public JFXTextField txtuserName;
    public JFXPasswordField txtPassword;

    private String validate() {
        if (txtuserName.getText().isEmpty()) {
            txtuserName.getStyleClass().add("textField-error");
            txtPassword.getStyleClass().remove("textField-error");
            return "UserName Or Mobile No Must Required";

        } else if (txtPassword.getText().isEmpty()) {
            txtPassword.getStyleClass().add("textField-error");
            txtuserName.getStyleClass().remove("textField-error");
            return "Password Must Required";

        }

        txtPassword.getStyleClass().remove("textField-error");
        txtuserName.getStyleClass().remove("textField-error");
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void closeStage(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("user-dashboard.fxml"))));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        String imagePath = "/images/Hotel-logo.png";
        URL imageUrl = getClass().getResource(imagePath);
        Image image = new Image(imageUrl.toExternalForm());
        stage.getIcons().add(image);
        stage.setTitle("User Dashboard");
        stage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

    }

    public void onlogin(ActionEvent event) throws IOException {

        String error = validate();

        if (error != null){
            important.showErrorAlert(error).show();

        }else{

            try {
                ResultSet resultSet = MySQL.Search("SELECT * FROM `user` WHERE `fullName` = '"+txtuserName.getText()+"' OR `mobile` = '"+txtuserName.getText()+"'");

                if (resultSet.next()){
                    
                    if (resultSet.getInt("status_id") == 2){
                        important.showErrorAlert("Access Denied, Your Account Is Suspended Contact Admin ").show();

                    } else if (!BCrypt.checkpw(txtPassword.getText(),resultSet.getString("password"))){
                        important.showErrorAlert("Access Denied, Invalid Password ").show();

                    }else{
                        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("admin-dashboard.fxml"))));
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        String imagePath = "/images/Hotel-logo.png";
                        URL imageUrl = getClass().getResource(imagePath);
                        Image image = new Image(imageUrl.toExternalForm());
                        stage.getIcons().add(image);
                        stage.setTitle("Admin Dashboard");
                        stage.show();
                        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
                    }
//

                }else{
                    important.showErrorAlert("Access Denied, Invalid Username Or Mobile Number").show();
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }



    }
}
