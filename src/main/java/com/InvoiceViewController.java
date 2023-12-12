package com;

import com.database.MySQL;
import com.model.InvoiceItem;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.important.*;

import net.sf.jasperreports.engine.design.JasperDesign;

public class InvoiceViewController implements Initializable {

    //    TextFields
    @FXML
    public JFXTextField txtInvoiceDate;
    public JFXTextField txtInvoiceNumber;
    public JFXTextField txtFoodItem;
    public JFXTextField txtQty;
    public JFXTextField txtSerialNo;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtTotalPrice;
    public JFXTextField txtServiceCharge;
    public Label GrandPriceLabel;
    public JFXTextField txtPaidAmount;
    public JFXTextField txtBalance;
//    TextFields

    //    Table
    @FXML
    private TableView<InvoiceItem> invoiceTable;

    @FXML
    private TableColumn<InvoiceItem, String> tc_SerialNo;

    @FXML
    private TableColumn<InvoiceItem, String> tc_FoodItem;

    @FXML
    private TableColumn<InvoiceItem, String> tc_Quantity;

    @FXML
    private TableColumn<InvoiceItem, String> tc_UnitPrice;

    @FXML
    private JFXComboBox<String> stewardsComboBox;

//    Table

    //    Button
    @FXML
    JFXButton btnInvoiceAddItem, btnUpdateItem, btnDeleteItem, btnResetAddItemFields, btnMakePayment;
//    Button

    ObservableList<InvoiceItem> list = FXCollections.observableArrayList();
    ObservableList<String> stewardList = FXCollections.observableArrayList();
    private HashMap<String, String> stewardMap = new HashMap<>();

    public String[] foodNames = new String[]{};

    public void addData(String newData) {
        String[] newArray = new String[foodNames.length + 1];
        System.arraycopy(foodNames, 0, newArray, 0, foodNames.length);
        newArray[foodNames.length] = newData;
        foodNames = newArray;
    }

    private String validate() {
        if (txtFoodItem.getText().isEmpty()) {
            txtFoodItem.getStyleClass().add("textField-error");
            txtQty.getStyleClass().remove("textField-error");
            return "Food Item Must Required";

        } else if (txtQty.getText().isEmpty()) {
            txtQty.getStyleClass().add("textField-error");
            txtFoodItem.getStyleClass().remove("textField-error");
            return "Quantity Item Must Required";

        } else if (!isNumeric(txtQty.getText())) {
            txtQty.getStyleClass().add("textField-error");
            txtFoodItem.getStyleClass().remove("textField-error");
            return "Invalid Quantity Count";

        }
        txtQty.getStyleClass().remove("textField-error");
        txtFoodItem.getStyleClass().remove("textField-error");
        return null;
    }

    private void stewardLoad() {
        try {
            ResultSet resultSet = MySQL.Search("SELECT * FROM `user` WHERE `status_id`='1' AND `user_id`!='#f1e802' ");
            while (resultSet.next()) {
                String stewardName = resultSet.getString("fullName");
                stewardList.add(stewardName);
                stewardMap.put(stewardName, resultSet.getString("user_id"));
                System.out.println(resultSet.getString("fullName"));

            }
            stewardsComboBox.setItems(stewardList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSuggetions() {
        try {
            ResultSet resultSet = MySQL.Search("SELECT * FROM `food` ");

            while (resultSet.next()) {
                addData(resultSet.getString("food_name"));
            }
            TextFields.bindAutoCompletion(txtFoodItem, foodNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        txtInvoiceDate.setText(getDate());
        txtInvoiceNumber.setText(getUniqueID());
        txtServiceCharge.setText("10");
        btnUpdateItem.setDisable(true);
        btnDeleteItem.setDisable(true);
        btnMakePayment.setDisable(true);
    }

    public void clearAll() {
        txtInvoiceDate.setText(getDate());
        txtInvoiceNumber.setText(getUniqueID());
        txtFoodItem.setText("");
        txtQty.setText("");
        txtSerialNo.setText("");
        txtUnitPrice.setText("");
        txtTotalPrice.setText("");
        txtServiceCharge.setText("10");
        GrandPriceLabel.setText("0.0");
        txtPaidAmount.setText("");
        txtBalance.setText("");
        stewardsComboBox.setValue(null);
        btnInvoiceAddItem.setDisable(false);
        btnUpdateItem.setDisable(true);
        btnDeleteItem.setDisable(true);
        btnMakePayment.setDisable(true);
        list.clear();
        invoiceTable.refresh();

    }

    public void clearAddItemFields() {
        txtFoodItem.setText("");
        txtQty.setText("");
        txtSerialNo.setText("");
        txtUnitPrice.setText("");
        btnInvoiceAddItem.setDisable(false);
        btnUpdateItem.setDisable(true);
        btnDeleteItem.setDisable(true);
    }

    public void setTableStructure() {
        tc_SerialNo.setCellValueFactory(new PropertyValueFactory<InvoiceItem, String>("Serial_No"));
        tc_FoodItem.setCellValueFactory(new PropertyValueFactory<InvoiceItem, String>("Food_Item"));
        tc_Quantity.setCellValueFactory(new PropertyValueFactory<InvoiceItem, String>("Quantity"));
        tc_UnitPrice.setCellValueFactory(new PropertyValueFactory<InvoiceItem, String>("Unit_Price"));

        invoiceTable.setItems(list);
    }

    private boolean isSerialNumberExists(String serialNumber) {
        ObservableList<InvoiceItem> items = invoiceTable.getItems();

        for (InvoiceItem item : items) {
            if (item.getSerial_No().equals(serialNumber)) {
                return true; // Serial number already exists
            }
        }

        return false; // Serial number doesn't exist
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDefaultValues();
        loadSuggetions();
        setTableStructure();
        stewardLoad();
        txtFoodItem.textProperty().addListener((observable, oldValue, newValue) -> {
            selectFoods(newValue);
        });


    }


    @FXML
    public void invoiceAddTableItem(ActionEvent event) {

        String error = validate();
        if (error != null) {
            Alert alert = showErrorAlert(error);
            alert.show();

        } else {
            if (!isSerialNumberExists(txtSerialNo.getText())) {
                // Serial number doesn't exist, proceed with insertion
                list.add(new InvoiceItem(txtSerialNo.getText(), txtFoodItem.getText(), txtQty.getText(), txtUnitPrice.getText()));
                invoiceTable.setItems(list);
                calculateQuantitySum();
                if (GrandPriceLabel.getText().equals("0.0")) {
                    btnMakePayment.setDisable(true);
                } else {
                    btnMakePayment.setDisable(false);
                }
                clearAddItemFields();

            } else {
                showWarningAlert("Serial number already exists in the table.").show();
            }
        }
    }

    public void resetInvoicePage(ActionEvent event) {
        clearAll();
    }

    public void resetAddItemFields(ActionEvent event) {
        clearAddItemFields();
        invoiceTable.setItems(list);
    }

    public void onInvoiceTableRowSelected(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
            int row = invoiceTable.getSelectionModel().getSelectedIndex();

            if (row != -1) {
                btnInvoiceAddItem.setDisable(true);
                btnUpdateItem.setDisable(false);
                btnDeleteItem.setDisable(false);
                txtSerialNo.setText(String.valueOf(tc_SerialNo.getCellData(row)));
                txtFoodItem.setText(String.valueOf(tc_FoodItem.getCellData(row)));
                txtQty.setText(String.valueOf(tc_Quantity.getCellData(row)));
                txtUnitPrice.setText(String.valueOf(tc_UnitPrice.getCellData(row)));

            }


        }


    }

    public void selectFoods(String sugg) {

        try {
            ResultSet resultSet = MySQL.Search("SELECT * FROM `food` WHERE `food_name`='" + sugg + "' ");

            if (resultSet.next()) {
                txtSerialNo.setText(resultSet.getString("serial_no"));
                txtUnitPrice.setText(resultSet.getString("selling_price"));
                txtQty.setText("1");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onDeleteRow(ActionEvent event) {

        // Get the selected item
        InvoiceItem selectedPerson = invoiceTable.getSelectionModel().getSelectedItem();

        if (selectedPerson != null) {
            // Remove the selected item from the list
            list.remove(selectedPerson);
            invoiceTable.setItems(list);
            calculateQuantitySum();
            if (GrandPriceLabel.getText().equals("0.0")) {
                btnMakePayment.setDisable(true);
            } else {
                btnMakePayment.setDisable(false);
            }
            clearAddItemFields();
        }
    }


    public void onUpdateRow(ActionEvent event) {
        String error = validate();
        if (error != null) {
            Alert alert = showErrorAlert(error);
            alert.show();

        } else {
            InvoiceItem invoiceItem = invoiceTable.getSelectionModel().getSelectedItem();

            if (invoiceItem != null) {
                // Update the selected item
                invoiceItem.setQuantity(txtQty.getText());

                // Refresh the TableView
                invoiceTable.refresh();
                calculateQuantitySum();
                clearAddItemFields();
            }
        }
    }

    private void calculateQuantitySum() {
        ObservableList<InvoiceItem> items = invoiceTable.getItems();

        Double totalPrice = Double.valueOf(0);

        for (InvoiceItem item : items) {
            Double itemQty = Double.parseDouble(item.getQuantity());
            Double unitPrice = Double.parseDouble(item.getUnit_Price());

            totalPrice += (itemQty * unitPrice);
        }
        txtTotalPrice.setText(String.valueOf(totalPrice));
        getAllCalculationValues();

    }

    private void getAllCalculationValues() {
        if (!txtTotalPrice.getText().isEmpty()) {
            if (txtServiceCharge.getText().isEmpty()) {
                GrandPriceLabel.setText(String.valueOf(txtTotalPrice.getText()));

            } else {
                if (!isNumeric(txtServiceCharge.getText())) {
                    showErrorAlert("Invalid Discount").show();

                } else {
                    Double txtTotal = Double.parseDouble(txtTotalPrice.getText());
                    Double serviceCharge = Double.parseDouble(txtServiceCharge.getText());

                    Double discount = ((serviceCharge * txtTotal) / 100);
                    GrandPriceLabel.setText(String.valueOf(txtTotal + discount ));
                }
            }

        }

    }

    public void onSetDiscount(KeyEvent keyEvent) {
        getAllCalculationValues();

    }

    private void printInvoice(String invoiceNo) {

        try {

            JasperDesign design = JRXmlLoader.load("src/main/resources/com/example/navigate/reports/invoice.jrxml");
            JRDesignQuery designQuery = new JRDesignQuery();
            designQuery.setText("SELECT *," +
                    "`mawellaresort`.`invoice_item`.`invoice_no` AS `inv_no`" +
                    "FROM `mawellaresort`.`invoice`" +
                    "INNER JOIN `mawellaresort`.`invoice_item` ON " +
                    " `mawellaresort`.`invoice`.`invoice_no` = `mawellaresort`.`invoice_item`.`invoice_no` " +
                    "INNER JOIN `mawellaresort`.`food` ON " +
                    " `mawellaresort`.`food`.`serial_no` = `mawellaresort`.`invoice_item`.`food_serial_no`" +
                    "WHERE `invoice`.`invoice_no`='" + invoiceNo + "' ");
            design.setQuery(designQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, MySQL.getInstance());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void orderRecipts(String invoiceNo) {
        try {

            JasperDesign design = JRXmlLoader.load("src/main/resources/com/example/navigate/reports/Order.jrxml");
            JRDesignQuery designQuery = new JRDesignQuery();
            designQuery.setText("SELECT *," +
                    "`mawellaresort`.`invoice_item`.`invoice_no` AS `inv_no`" +
                    "FROM `mawellaresort`.`invoice`" +
                    "INNER JOIN `mawellaresort`.`invoice_item` ON " +
                    " `mawellaresort`.`invoice`.`invoice_no` = `mawellaresort`.`invoice_item`.`invoice_no` " +
                    "INNER JOIN `mawellaresort`.`food` ON " +
                    " `mawellaresort`.`food`.`serial_no` = `mawellaresort`.`invoice_item`.`food_serial_no`" +
                    "WHERE `invoice`.`invoice_no`='" + invoiceNo + "' ");
            design.setQuery(designQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(design);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, MySQL.getInstance());

            for (int x = 0; x < 2; x++) {
                JasperViewer.viewReport(jasperPrint, false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onmakePayment(ActionEvent event) {
        if (stewardsComboBox.getValue() == null) {
            stewardsComboBox.getStyleClass().add("textField-error");
            stewardsComboBox.requestFocus();
            showErrorAlert("Please Select Steward").show();

        } else {
            stewardsComboBox.getStyleClass().remove("textField-error");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Payment Appointment");
            alert.setContentText("Are You Sure Do You Want Make The Payment");
            alert.initStyle(StageStyle.UTILITY);
            ButtonType btnOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            ButtonType btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(btnOk, btnCancel);

            // Show the confirmation alert and wait for the user's response
            Optional<ButtonType> result = alert.showAndWait();

            // Handle the user's response
            if (result.isPresent() && result.get() == btnOk) {

                String invoiceNo = txtInvoiceNumber.getText();
                String invoiceDate = txtInvoiceDate.getText();
                String invoiceDiscount = txtServiceCharge.getText().isEmpty() ? "10" : txtServiceCharge.getText();
                String invoiceGrandTotal = GrandPriceLabel.getText();
                String stewardID = stewardMap.get(stewardsComboBox.getValue());

                MySQL.Iud("INSERT INTO `invoice` (`invoice_no`, `discount`, `purchesed_date`, `total`, `user_id`)" +
                        "VALUES ('" + invoiceNo + "','" + invoiceDiscount + "','" + invoiceDate + "','" + invoiceGrandTotal + "','" + stewardID + "') ");

                ObservableList<InvoiceItem> items = invoiceTable.getItems();

                for (InvoiceItem tableItem : items) {
                    String itemQty = tableItem.getQuantity();
                    String itemUnitPrice = tableItem.getUnit_Price();
                    String itemSerailNo = tableItem.getSerial_No();

                    MySQL.Iud("INSERT INTO `invoice_item` (`qty`, `unit_price`, `invoice_no`, `food_serial_no`)" +
                            "VALUES ('" + itemQty + "','" + itemUnitPrice + "','" + invoiceNo + "','" + itemSerailNo + "') ");
                }

                printInvoice(invoiceNo);
                orderRecipts(invoiceNo);

                clearAll();
            }

        }
    }

    public void onMakeChanges(KeyEvent keyEvent) {
        if (isNumeric(txtPaidAmount.getText())) {

            if (!txtPaidAmount.getText().isEmpty() && !GrandPriceLabel.getText().equals("0.0")) {
                Double paidAmount = Double.parseDouble(txtPaidAmount.getText());
                Double grandTotal = Double.parseDouble(GrandPriceLabel.getText());

                txtBalance.setText(String.valueOf(paidAmount-grandTotal));

            }else {
                txtBalance.setText("0.0");

            }
        } else {
            showErrorAlert("Only Numbers Must Required");

        }
    }
}

