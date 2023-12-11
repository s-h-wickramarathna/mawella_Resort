package com;

import com.database.MySQL;
import com.jfoenix.controls.JFXDialog;
import com.model.Food;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static com.important.*;

public class FoodsViewController implements Initializable {

    @FXML
    private Stage stage;
    @FXML
    private JFXTextField txtSerialNo;

    @FXML
    private JFXTextField txtFoodName;

    @FXML
    private JFXTextField txtCost;

    @FXML
    private JFXTextArea txtDescription;

    @FXML
    private JFXTextField txtSearchFoods;

    @FXML
    private JFXTextField txtSellingPrice;

    @FXML
    private TableView<Food> FoodTable;

    @FXML
    private TableColumn<Food, String> no_Column;

    @FXML
    private TableColumn<Food, String> serialNo_Column;

    @FXML
    private TableColumn<Food, String> foodName_Column;

    @FXML
    private TableColumn<Food, String> description_Column;

    @FXML
    private TableColumn<Food, String> sellingPrice_Column;

    @FXML
    private TableColumn<Food, String> unitPrice_Column;

    @FXML
    private TableColumn<Food, String> createdAt_Column;

    @FXML
    private TableColumn<Food, String> updatedAt_Column;

    @FXML
    private JFXButton btnFoodUpdate, btnFoodAdd, btnFoodDelete, btnManageFoodCategory;

    @FXML
    private void setDefaultValues() {
        txtSerialNo.setText(getUniqueID());
    }

    private void loadFoodTable(String search) {
        try {
            ObservableList<Food> foodTableList = FXCollections.observableArrayList();

            no_Column.setCellValueFactory(new PropertyValueFactory<Food, String>("No"));
            serialNo_Column.setCellValueFactory(new PropertyValueFactory<Food, String>("SerialNo"));
            foodName_Column.setCellValueFactory(new PropertyValueFactory<Food, String>("FoodName"));
            sellingPrice_Column.setCellValueFactory(new PropertyValueFactory<Food, String>("SellingPrice"));
            description_Column.setCellValueFactory(new PropertyValueFactory<Food, String>("Description"));
            unitPrice_Column.setCellValueFactory(new PropertyValueFactory<Food, String>("Cost"));
            createdAt_Column.setCellValueFactory(new PropertyValueFactory<Food, String>("CreatedAt"));
            updatedAt_Column.setCellValueFactory(new PropertyValueFactory<Food, String>("UpdatedAt"));


            ResultSet resultSet = MySQL.Search("SELECT * FROM `food` WHERE `food_name` LIKE '%"+search+"%' OR `serial_no` LIKE '%"+search+"%' ORDER BY `created_At` DESC");
            int x = 0;
            while (resultSet.next()) {
                String no = String.valueOf(x += 1);
                String serialNo = resultSet.getString("serial_no");
                String food_name = resultSet.getString("food_name");
                String cost = resultSet.getString("cost");
                String selling_price = resultSet.getString("selling_price");
                String description = resultSet.getString("description");
                String created_At = resultSet.getString("created_At");
                String updated_At = resultSet.getString("updated_At");

                foodTableList.add(new Food(no, serialNo, food_name, description, cost, selling_price, created_At, updated_At));
            }
            FoodTable.setItems(foodTableList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String validateFoodFields() {

        if (txtFoodName.getText().isEmpty()) {
            txtFoodName.getStyleClass().add("textField-error");
            txtCost.getStyleClass().remove("textField-error");
            txtSellingPrice.getStyleClass().remove("textField-error");
            return "Food Name Must Required";

        } else if (txtCost.getText().isEmpty()) {
            txtCost.getStyleClass().add("textField-error");
            txtFoodName.getStyleClass().remove("textField-error");
            txtSellingPrice.getStyleClass().remove("textField-error");
            return "Food Cost Must Required";

        } else if (!isNumeric(txtCost.getText())) {
            txtCost.getStyleClass().add("textField-error");
            txtFoodName.getStyleClass().remove("textField-error");
            txtSellingPrice.getStyleClass().remove("textField-error");
            return "Invalid Food Cost";

        } else if (txtSellingPrice.getText().isEmpty()) {
            txtSellingPrice.getStyleClass().add("textField-error");
            txtCost.getStyleClass().remove("textField-error");
            txtFoodName.getStyleClass().remove("textField-error");
            return "Food Cost Must Required";

        } else if (!isNumeric(txtSellingPrice.getText())) {
            txtSellingPrice.getStyleClass().add("textField-error");
            txtCost.getStyleClass().remove("textField-error");
            txtFoodName.getStyleClass().remove("textField-error");
            return "Invalid Selling Price";

        }
        txtSellingPrice.getStyleClass().remove("textField-error");
        txtCost.getStyleClass().remove("textField-error");
        txtFoodName.getStyleClass().remove("textField-error");
        return null;
    }

    private void resetAll() {

        txtSerialNo.setText(getUniqueID());
        txtFoodName.setText("");
        txtCost.setText("");
        txtSellingPrice.setText("");
        txtDescription.setText("");

        FoodTable.setDisable(false);
        txtFoodName.requestFocus();
        btnFoodAdd.setDisable(false);
        btnFoodUpdate.setDisable(true);
        btnFoodDelete.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDefaultValues();
        loadFoodTable("");
    }


    public void onAddNewFood(ActionEvent event) {
        String error = validateFoodFields();
        if (error != null) {
            Alert alert = showErrorAlert(error);
            alert.show();

        } else {

            try {
                ResultSet resultSet = MySQL.Search("SELECT * FROM food WHERE `serial_no` != '" + txtSerialNo.getText() + "' AND `food_name`='" + txtFoodName.getText() + "'");
                if (resultSet.next()) {
                    showErrorAlert("Value Is Already Exist").show();

                } else {
                    MySQL.Iud("INSERT INTO `food`(`serial_no`,`food_name`, `cost`, `selling_price`, `description`, `created_At`)" +
                            "VALUES('" + txtSerialNo.getText() + "','" + txtFoodName.getText() + "','" + txtCost.getText() + "','" + txtSellingPrice.getText() + "','" + txtDescription.getText() + "','" + getDate() + "') ");

                    showSuccessAlert().show();
                    resetAll();
                    loadFoodTable("");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    @FXML
    private void onSelectTable(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
            int row = FoodTable.getSelectionModel().getSelectedIndex();

            if (row != -1) {
                txtSerialNo.setText(String.valueOf(serialNo_Column.getCellData(row)));
                txtFoodName.setText(String.valueOf(foodName_Column.getCellData(row)));
                txtCost.setText(String.valueOf(unitPrice_Column.getCellData(row)));
                txtSellingPrice.setText(String.valueOf(sellingPrice_Column.getCellData(row)));
                txtDescription.setText(String.valueOf(description_Column.getCellData(row)));

                txtFoodName.requestFocus();
                btnFoodAdd.setDisable(true);
                btnFoodUpdate.setDisable(false);
                btnFoodDelete.setDisable(false);

            }


        }


    }

    public void onUpdateFood(ActionEvent event) {
        String error = validateFoodFields();
        if (error != null) {
            Alert alert = showErrorAlert(error);
            alert.show();

        } else {
            try {
                ResultSet resultSet = MySQL.Search("SELECT * FROM food WHERE `serial_no` != '" + txtSerialNo.getText() + "' AND `food_name`='" + txtFoodName.getText() + "'");
                if (resultSet.next()) {
                    showErrorAlert("Value Is Already Exist").show();

                } else {
                    MySQL.Iud("UPDATE `food` SET `food_name`='" + txtFoodName.getText() + "', `cost`='" + txtCost.getText() + "',`description`='" + txtDescription.getText() + "',`updated_At`='" + getDate() + "' WHERE `serial_no` = '" + txtSerialNo.getText() + "' ");
                    showSuccessAlert().show();
                    resetAll();
                    loadFoodTable(txtSearchFoods.getText());

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void onFoodDelete(ActionEvent event) {
        MySQL.Iud("DELETE FROM `food` WHERE `serial_no`='" + txtSerialNo.getText() + "' ");
        showSuccessAlert().show();
        resetAll();
        loadFoodTable("");
    }

    public void onCancelAll(ActionEvent event) {
        resetAll();
        loadFoodTable("");
    }


    public void searchFoods(KeyEvent keyEvent) {
        String search = txtSearchFoods.getText();
        loadFoodTable(search);
    }


  public void onManagefoodCategory(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("JDialog/category.fxml"));

      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setTitle("second");
      stage.setScene(new Scene(root));
      stage.show();
  }

}
