package com.example.navigate;

import com.example.navigate.database.MySQL;
import com.example.navigate.model.Food;
import com.example.navigate.model.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {
    @FXML
    private JFXTextField txtUserID;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private TableView<User> UserTable;

    @FXML
    private TableColumn<User, String> tc_No;

    @FXML
    private TableColumn<User, String> tc_UserId;

    @FXML
    private TableColumn<User, String> tc_UserName;

    @FXML
    private TableColumn<User, String> tc_UserMobile;

    @FXML
    private TableColumn<User, String> tc_UserType;

    @FXML
    private TableColumn<User, String> tc_CreatedAt;

    @FXML
    private TableColumn<User, String> tc_UpdatedAt;

    @FXML
    private JFXTextField txtSearchFoods;

    @FXML
    private JFXButton btnFoodAdd;

    @FXML
    private JFXButton btnFoodUpdate;

    @FXML
    private JFXComboBox<?> userTypeComboBox;

    @FXML
    private JFXPasswordField txtNewPassword;

    @FXML
    private JFXPasswordField txtConfirmPassword;

    @FXML
    private JFXTextField txtUserMobile;

    @FXML
    private Label newPasswordLabel;

    @FXML
    private Label confirmPasswordLabel;

    HashMap<String,Integer> userTypeMap = new HashMap<>();
    ObservableList userTypeList = FXCollections.observableArrayList();

    private void setDefaultValue() {
        txtUserID.setText(important.getUniqueID());
        newPasswordLabel.setVisible(false);
        txtNewPassword.setVisible(false);
        confirmPasswordLabel.setVisible(false);
        txtConfirmPassword.setVisible(false);
    }

    private void resetAll() {
        txtUserID.setText(important.getUniqueID());
        txtUserName.setText("");
        txtUserMobile.setText("");
        userTypeComboBox.setValue(null);
        txtNewPassword.setText("");
        txtConfirmPassword.setText("");

    }

    private void setTableStructure() {
        try {
            ObservableList<User> list = FXCollections.observableArrayList();
            int row = 0;

            tc_No.setCellValueFactory(new PropertyValueFactory<User, String>("No"));
            tc_UserId.setCellValueFactory(new PropertyValueFactory<User, String>("UserId"));
            tc_UserName.setCellValueFactory(new PropertyValueFactory<User, String>("UserName"));
            tc_UserMobile.setCellValueFactory(new PropertyValueFactory<User, String>("UserMobile"));
            tc_UserType.setCellValueFactory(new PropertyValueFactory<User, String>("UserType"));
            tc_CreatedAt.setCellValueFactory(new PropertyValueFactory<User, String>("UserCreatedAt"));
            tc_UpdatedAt.setCellValueFactory(new PropertyValueFactory<User, String>("UserUpdatedAt"));

            ResultSet resultSet = MySQL.Search("SELECT * FROM `user` INNER JOIN `user_type` ON `user_type`.`user_type_Id`=`user`.`user_type_id` WHERE `status_id`='1'");
            while (resultSet.next()) {
                row += 1;
                String userId = resultSet.getString("user_id");
                String fullName = resultSet.getString("fullName");
                String userType = resultSet.getString("user_type");
                String userMobile = resultSet.getString("mobile");
                String createdAt = resultSet.getString("created_At");
                String update_At = resultSet.getString("update_At");
                list.add(new User(String.valueOf(row),userId,fullName,userMobile,userType,createdAt,update_At));
            }

            UserTable.setItems(list);

        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    private void loadUserTypeComboBox(){
        try {

            ResultSet resultSet = MySQL.Search("SELECT * FROM `user_type`");
            while (resultSet.next()) {
                userTypeList.add(resultSet.getString("user_type"));
                userTypeMap.put(resultSet.getString("user_type"),resultSet.getInt("user_type_Id"));
            }

            userTypeComboBox.setItems(userTypeList);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDefaultValue();
        loadUserTypeComboBox();
        setTableStructure();
        userTypeComboBox.valueProperty().addListener((observable, oldValue, value) -> {
            if (value.equals("Admin")){
                newPasswordLabel.setVisible(true);
                txtNewPassword.setVisible(true);
                confirmPasswordLabel.setVisible(true);
                txtConfirmPassword.setVisible(true);
            }else{
                newPasswordLabel.setVisible(false);
                txtNewPassword.setVisible(false);
                confirmPasswordLabel.setVisible(false);
                txtConfirmPassword.setVisible(false);
            }
        });
    }

    public void onCancelAll(ActionEvent event) {
    }
}
