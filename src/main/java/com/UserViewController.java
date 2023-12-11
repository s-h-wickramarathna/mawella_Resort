package com;

import com.database.MySQL;
import com.model.User;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

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
    private TableColumn<User, String> tc_UserStatus;

    @FXML
    private TableColumn<User, String> tc_CreatedAt;

    @FXML
    private TableColumn<User, String> tc_UpdatedAt;

    @FXML
    private JFXTextField txtSearchUsers;

    @FXML
    private JFXButton btnAddUser;

    @FXML
    private JFXButton btnUpdateUser;

    @FXML
    private JFXButton btnDeleteUser;

    @FXML
    private JFXComboBox<String> userTypeComboBox;

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

    @FXML
    private JFXToggleButton btnactiveInactive;

    HashMap<String, Integer> userTypeMap = new HashMap<>();
    ObservableList userTypeList = FXCollections.observableArrayList();

    private void setDefaultValue() {
        txtUserID.setText(important.getUniqueID());
        newPasswordLabel.setVisible(false);
        txtNewPassword.setVisible(false);
        confirmPasswordLabel.setVisible(false);
        txtConfirmPassword.setVisible(false);
        btnactiveInactive.setVisible(false);
    }

    private void resetAll() {
        txtUserID.setText(important.getUniqueID());
        txtUserName.setText("");
        txtUserMobile.setText("");
        userTypeComboBox.setValue(null);
        txtNewPassword.setText("");
        txtConfirmPassword.setText("");
        userTypeComboBox.setEditable(false);
        btnAddUser.setDisable(false);
        btnactiveInactive.setVisible(false);
        btnUpdateUser.setDisable(true);
        btnDeleteUser.setDisable(true);

    }

    private void loadUserTable(String search) {
        try {

            ObservableList<User> list = FXCollections.observableArrayList();
            int row = 0;

            tc_No.setCellValueFactory(new PropertyValueFactory<User, String>("No"));
            tc_UserId.setCellValueFactory(new PropertyValueFactory<User, String>("UserId"));
            tc_UserName.setCellValueFactory(new PropertyValueFactory<User, String>("UserName"));
            tc_UserMobile.setCellValueFactory(new PropertyValueFactory<User, String>("UserMobile"));
            tc_UserType.setCellValueFactory(new PropertyValueFactory<User, String>("UserType"));
            tc_UserStatus.setCellValueFactory(new PropertyValueFactory<User, String>("UserStatus"));
            tc_CreatedAt.setCellValueFactory(new PropertyValueFactory<User, String>("UserCreatedAt"));
            tc_UpdatedAt.setCellValueFactory(new PropertyValueFactory<User, String>("UserUpdatedAt"));

            ResultSet resultSet = MySQL.Search("SELECT * FROM `user` INNER JOIN `user_type` ON `user_type`.`user_type_Id`=`user`.`user_type_id` WHERE `user_id`!='#f1e802' AND ( `user_id` LIKE '%" + search + "%' OR `fullName` LIKE '%" + search + "%') ");
            while (resultSet.next()) {
                row += 1;
                String userId = resultSet.getString("user_id");
                String fullName = resultSet.getString("fullName");
                String userType = resultSet.getString("user_type");
                String userMobile = resultSet.getString("mobile");
                String userStatus = resultSet.getInt("status_id") == 1 ? "Active" : "Inactive";
                String createdAt = resultSet.getString("created_At");
                String update_At = resultSet.getString("update_At");
                list.add(new User(String.valueOf(row), userId, fullName, userMobile, userType, userStatus, createdAt, update_At));
            }

            UserTable.setItems(list);

        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    private void loadUserTypeComboBox() {
        try {

            ResultSet resultSet = MySQL.Search("SELECT * FROM `user_type`");
            while (resultSet.next()) {
                userTypeList.add(resultSet.getString("user_type"));
                userTypeMap.put(resultSet.getString("user_type"), resultSet.getInt("user_type_Id"));
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
        loadUserTable("");
        userTypeComboBox.valueProperty().addListener((observable, oldValue, value) -> {
            if (value != null && value.equals("Admin")) {
                newPasswordLabel.setVisible(true);
                txtNewPassword.setVisible(true);
                confirmPasswordLabel.setVisible(true);
                txtConfirmPassword.setVisible(true);
            } else {
                newPasswordLabel.setVisible(false);
                txtNewPassword.setVisible(false);
                confirmPasswordLabel.setVisible(false);
                txtConfirmPassword.setVisible(false);
            }
        });
    }

    public void onCancelAll(ActionEvent event) {
        resetAll();
        txtSearchUsers.setText("");
    }

    private String validate(boolean isAdd) {
        if (txtUserName.getText().isEmpty()) {
            txtUserMobile.getStyleClass().remove("textField-error");
            userTypeComboBox.getStyleClass().remove("textField-error");
            txtNewPassword.getStyleClass().remove("textField-error");
            txtConfirmPassword.getStyleClass().remove("textField-error");
            txtUserName.getStyleClass().add("textField-error");
            return "User Name Must Required";

        } else if (txtUserMobile.getText().isEmpty()) {
            txtUserName.getStyleClass().remove("textField-error");
            userTypeComboBox.getStyleClass().remove("textField-error");
            txtNewPassword.getStyleClass().remove("textField-error");
            txtConfirmPassword.getStyleClass().remove("textField-error");
            txtUserMobile.getStyleClass().add("textField-error");
            return "User Mobile Must Required";

        } else if (!important.isNumeric(txtUserMobile.getText()) || txtUserMobile.getText().length() != 10) {
            txtUserName.getStyleClass().remove("textField-error");
            userTypeComboBox.getStyleClass().remove("textField-error");
            txtNewPassword.getStyleClass().remove("textField-error");
            txtConfirmPassword.getStyleClass().remove("textField-error");
            txtUserMobile.getStyleClass().add("textField-error");
            return "Invalid User Mobile";

        } else if (userTypeComboBox.getValue() == null) {
            txtUserName.getStyleClass().remove("textField-error");
            txtNewPassword.getStyleClass().remove("textField-error");
            txtConfirmPassword.getStyleClass().remove("textField-error");
            txtUserMobile.getStyleClass().remove("textField-error");
            userTypeComboBox.getStyleClass().add("textField-error");
            return "User Type Must Required";

        } else if (userTypeComboBox.getValue().equals("Admin") && isAdd) {

            if (txtNewPassword.getText().isEmpty()) {
                txtUserName.getStyleClass().remove("textField-error");
                txtNewPassword.getStyleClass().remove("textField-error");
                txtConfirmPassword.getStyleClass().remove("textField-error");
                txtUserMobile.getStyleClass().remove("textField-error");
                userTypeComboBox.getStyleClass().add("textField-error");
                return "User Password Must Required";

            } else if (txtNewPassword.getText().length() <= 5 || txtNewPassword.getText().length() >= 20) {
                txtUserName.getStyleClass().remove("textField-error");
                txtNewPassword.getStyleClass().remove("textField-error");
                txtConfirmPassword.getStyleClass().remove("textField-error");
                txtUserMobile.getStyleClass().remove("textField-error");
                userTypeComboBox.getStyleClass().add("textField-error");
                return "User Password Length Must Required 5 - 20";

            } else if (txtConfirmPassword.getText().isEmpty()) {
                txtUserName.getStyleClass().remove("textField-error");
                txtNewPassword.getStyleClass().remove("textField-error");
                txtUserMobile.getStyleClass().remove("textField-error");
                userTypeComboBox.getStyleClass().remove("textField-error");
                txtConfirmPassword.getStyleClass().add("textField-error");
                return "User Password Confirm Must Required";

            } else if (!txtNewPassword.getText().equals(txtConfirmPassword.getText())) {
                txtUserName.getStyleClass().remove("textField-error");
                txtNewPassword.getStyleClass().remove("textField-error");
                txtUserMobile.getStyleClass().remove("textField-error");
                userTypeComboBox.getStyleClass().remove("textField-error");
                txtConfirmPassword.getStyleClass().add("textField-error");
                return "Confirm Password Doesn't Match";
            }

        }
        txtUserName.getStyleClass().remove("textField-error");
        txtNewPassword.getStyleClass().remove("textField-error");
        txtUserMobile.getStyleClass().remove("textField-error");
        userTypeComboBox.getStyleClass().remove("textField-error");
        txtConfirmPassword.getStyleClass().remove("textField-error");
        return null;
    }


    public void onAddUser(ActionEvent event) {
        String error = validate(true);

        if (error != null) {
            important.showErrorAlert(error).show();

        } else {

            String userId = txtUserID.getText();
            String userFullName = txtUserName.getText();
            String userMobile = txtUserMobile.getText();
            String userType = String.valueOf(userTypeComboBox.getValue());
            String userTypeId = String.valueOf(userTypeMap.get(String.valueOf(userType)));
            String userNewPassword = null;

            try {

                ResultSet resultSet = MySQL.Search("SELECT * FROM `user` WHERE `user_id` != '" + userId + "' AND (`fullName` = '" + userFullName + "' OR `mobile`='" + userMobile + "') ");
                if (resultSet.next()) {
                    important.showErrorAlert("User Name Exist").show();

                } else {
                    if (userTypeComboBox.getValue().equals("Admin")) {
                        userNewPassword = txtNewPassword.getText();

                    }

                    MySQL.Iud("INSERT INTO `user`(`user_id`, `fullName`, `mobile`, `user_type_id`, `created_At`, `status_id`, `password`) " +
                            "VALUES('" + userId + "','" + userFullName + "','" + userMobile + "','" + userTypeId + "','" + important.getDate() + "','1','" + userNewPassword + "') ");

                    loadUserTable(txtSearchUsers.getText());
                    resetAll();
                    important.showSuccessAlert();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void onDeleteUser(ActionEvent event) {
        MySQL.Iud("DELETE FROM `user` WHERE `user_id`='" + txtUserID.getText() + "' ");
        important.showSuccessAlert().show();
        loadUserTable(txtSearchUsers.getText());
        resetAll();
    }

    public void tableRowSelected(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
            int row = UserTable.getSelectionModel().getSelectedIndex();

            if (row != -1) {
                btnAddUser.setDisable(true);
                btnUpdateUser.setDisable(false);
                btnDeleteUser.setDisable(false);
                txtUserID.setText(String.valueOf(tc_UserId.getCellData(row)));
                txtUserName.setText(String.valueOf(tc_UserName.getCellData(row)));
                txtUserMobile.setText(String.valueOf(tc_UserMobile.getCellData(row)));
                userTypeComboBox.setValue(String.valueOf(tc_UserType.getCellData(row)));
                String userStatus = String.valueOf(tc_UserStatus.getCellData(row));
                userTypeComboBox.setEditable(false);
                if (userStatus.equals("Active")) {
                    btnactiveInactive.setSelected(true);
                } else {
                    btnactiveInactive.setSelected(false);
                }
                btnactiveInactive.setVisible(true);

            }


        }
    }

    public void onUpdateUser(ActionEvent event) {
        String error = validate(false);

        if (error != null) {
            important.showErrorAlert(error).show();

        } else {

            if (!txtNewPassword.getText().isEmpty() && !txtNewPassword.getText().equals(txtConfirmPassword.getText())) {
                important.showErrorAlert("Confirm Password Doesn't Match").show();

            } else {
                String userId = txtUserID.getText();
                String userFullName = txtUserName.getText();
                String userMobile = txtUserMobile.getText();
                String userType = String.valueOf(userTypeComboBox.getValue());
                String userTypeId = String.valueOf(userTypeMap.get(String.valueOf(userType)));
                String userNewPassword = null;

                try {

                    ResultSet resultSet = MySQL.Search("SELECT * FROM `user` WHERE `user_id` != '" + userId + "' AND (`fullName` = '" + userFullName + "' OR `mobile`='" + userMobile + "') ");
                    if (resultSet.next()) {
                        important.showErrorAlert("User Name Exist").show();

                    } else {
                        if (userTypeComboBox.getValue().equals("Admin")) {
                            userNewPassword = txtNewPassword.getText();

                        }

                        MySQL.Iud("UPDATE `user` SET `fullName`='" + userFullName + "', `mobile`='" + userMobile + "', `user_type_id`='" + userTypeId + "', `update_At`='" + important.getDate() + "', `password`='" + userNewPassword + "' WHERE `user_id`='" + userId + "' ");

                        loadUserTable(txtSearchUsers.getText());
                        resetAll();
                        important.showSuccessAlert();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void onUserActiveInactive(ActionEvent event) {
        boolean status = btnactiveInactive.isSelected();
        int status_id = 0;

        if (status){
            status_id = 1;

        }else {
            status_id = 2;

        }

        MySQL.Iud("UPDATE `user` SET `status_id`='"+status_id+"' WHERE `user_id`='" + txtUserID.getText() + "' ");
        loadUserTable(txtSearchUsers.getText());
        resetAll();

    }

    public void onSearchUsers(KeyEvent keyEvent) {
        loadUserTable(txtSearchUsers.getText());
    }
}
