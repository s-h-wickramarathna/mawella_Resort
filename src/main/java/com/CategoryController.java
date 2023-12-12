package com;

import com.database.MySQL;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.model.Category;
import com.model.Food;
import com.model.InvoiceItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.important.*;

public class CategoryController implements Initializable {
    @FXML
    public JFXTextField txtCategoryName;
    @FXML
    public JFXTextField txtCategoryId;
    public Label idLabel;
    public JFXButton btnCategoryAdd;
    public JFXButton btnCategoryUpdate;
    public JFXButton btnCategoryDelete;
    public JFXButton btnCategoryCancel;

    @FXML
    private TableView<Category> CategoryTable;

    @FXML
    private TableColumn<Category, String> tc_ID;

    @FXML
    private TableColumn<Category, String> tc_Name;

    @FXML
    private TableColumn<Category, String> tc_CreatedAt;

    @FXML
    private TableColumn<Category, String> tc_UpdatedAt;

    private void loadTable(){
        try {
            ObservableList<Category> categoryList = FXCollections.observableArrayList();

            tc_ID.setCellValueFactory(new PropertyValueFactory<Category, String>("Id"));
            tc_Name.setCellValueFactory(new PropertyValueFactory<Category, String>("Name"));
            tc_CreatedAt.setCellValueFactory(new PropertyValueFactory<Category, String>("CreatedAt"));
            tc_UpdatedAt.setCellValueFactory(new PropertyValueFactory<Category, String>("UpdatedAt"));


            ResultSet resultSet = MySQL.Search("SELECT * FROM `category`");

            while (resultSet.next()) {
                String id= resultSet.getString("id");
                String name = resultSet.getString("name");
                String created_At = resultSet.getString("createdAt");
                String updated_At = resultSet.getString("updatedAt");

                categoryList.add(new Category(id,name,created_At,updated_At));
            }
            CategoryTable.setItems(categoryList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String validateFields(){
        if (txtCategoryName.getText().isEmpty()){
            return "Category Name Must Required";
        }

        return null;
    }

    private void resetAll(){
        idLabel.setVisible(false);
        txtCategoryName.setText("");
        txtCategoryId.setVisible(false);
        txtCategoryId.setEditable(false);
        btnCategoryAdd.setDisable(false);
        btnCategoryUpdate.setDisable(true);
        btnCategoryDelete.setDisable(true);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idLabel.setVisible(false);
        txtCategoryId.setText("");
        txtCategoryId.setVisible(false);
        txtCategoryId.setEditable(false);
        btnCategoryAdd.setDisable(false);
        btnCategoryUpdate.setDisable(true);
        btnCategoryDelete.setDisable(true);
        loadTable();

    }


    public void onCategoryAdd(ActionEvent event) {
        String error = validateFields();
        if (error != null){
            Alert alert = showErrorAlert(error);
            alert.show();
        }else {

            try {
                ResultSet resultSet = MySQL.Search("SELECT * FROM `category` WHERE `name`='"+txtCategoryName.getText()+"' ");
                if (resultSet.next()){
                    Alert alert = showErrorAlert("This Category Already Exist");
                    alert.show();
                }else {

                    MySQL.Iud("INSERT INTO `category`(`name`,`createdAt`) VALUES('"+txtCategoryName.getText()+"','"+getDate()+"') ");
                    showSuccessAlert().show();
                    loadTable();
                    resetAll();

                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void onCategoryUpdate(ActionEvent event) {
        String error = validateFields();
        if (error != null){
            Alert alert = showErrorAlert(error);
            alert.show();
        }else {

            try {
                ResultSet resultSet = MySQL.Search("SELECT * FROM `category` WHERE `id` != '"+txtCategoryId.getText()+"' AND `name`='"+txtCategoryName.getText()+"' ");
                if (resultSet.next()){
                    Alert alert = showErrorAlert("This Category Name Already Used");
                    alert.show();
                }else {

                    MySQL.Iud("UPDATE `category` SET `name`='"+txtCategoryName.getText()+"', `updatedAt`='"+getDate()+"' WHERE `id`='"+txtCategoryId.getText()+"' ");
                    showSuccessAlert().show();
                    loadTable();
                    resetAll();

                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void onCategoryDelete(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Food Category Delete Confirmation");
        alert.setContentText("Are You Sure Do You Want To Delete This Category.");
        alert.initStyle(StageStyle.UTILITY);
        ButtonType btnOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(btnOk, btnCancel);

        // Show the confirmation alert and wait for the user's response
        Optional<ButtonType> result = alert.showAndWait();

        // Handle the user's response
        if (result.isPresent() && result.get() == btnOk) {
            MySQL.Iud("DELETE FROM `category` WHERE `id`='"+txtCategoryId.getText()+"'  ");

           resetAll();
           loadTable();
        }

    }

    public void onCategoryCancel(ActionEvent event) {
        resetAll();
    }

    public void onSelectTable(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
            int row = CategoryTable.getSelectionModel().getSelectedIndex();

            if (row != -1) {
                txtCategoryId.setText(String.valueOf(tc_ID.getCellData(row)));
                txtCategoryName.setText(String.valueOf(tc_Name.getCellData(row)));


                txtCategoryName.requestFocus();
                btnCategoryAdd.setDisable(true);
                idLabel.setVisible(true);
                txtCategoryId.setVisible(true);
                btnCategoryUpdate.setDisable(false);
                btnCategoryDelete.setDisable(false);


            }


        }
    }

    public void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
