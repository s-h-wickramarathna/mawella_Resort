package com.example.navigate;

import com.example.navigate.database.MySQL;
import com.example.navigate.model.InvoiceItem;
import com.example.navigate.model.Invoices;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ReportsViewController implements Initializable {

    public JFXButton btnPrint;
    public DatePicker txtDateChooser;
    public JFXTextField txtTotalInvoices;
    public JFXTextField txtGrandTotal;

    @FXML
    private TableView<Invoices> reportTable;

    @FXML
    private TableColumn<Invoices, String> tc_No;

    @FXML
    private TableColumn<Invoices, String> tc_InvoiceNo;

    @FXML
    private TableColumn<Invoices, String> tc_PurchesedDate;

    @FXML
    private TableColumn<Invoices, String> tc_StuwardID;

    @FXML
    private TableColumn<Invoices, String> tc_StewardName;

    @FXML
    private TableColumn<Invoices, String> tc_Discount;

    @FXML
    private TableColumn<Invoices, String> tc_Amount;
    ObservableList<Invoices> list = FXCollections.observableArrayList();

    private double fullAmount = 0;


    private void tableStructure() {
        tc_No.setCellValueFactory(new PropertyValueFactory<Invoices, String>("No"));
        tc_InvoiceNo.setCellValueFactory(new PropertyValueFactory<Invoices, String>("Invoice_No"));
        tc_PurchesedDate.setCellValueFactory(new PropertyValueFactory<Invoices, String>("Purchesed_Date"));
        tc_StuwardID.setCellValueFactory(new PropertyValueFactory<Invoices, String>("Steward_ID"));
        tc_StewardName.setCellValueFactory(new PropertyValueFactory<Invoices, String>("Steward_Name"));
        tc_Discount.setCellValueFactory(new PropertyValueFactory<Invoices, String>("Discount"));
        tc_Amount.setCellValueFactory(new PropertyValueFactory<Invoices, String>("Amount"));

        reportTable.setItems(list);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableStructure();
        list.clear();

        txtDateChooser.valueProperty().addListener((observable, oldValue, newValue) -> {
            onSelectInvoices(newValue);
        });
    }

    public void onClearAll(ActionEvent event) {
    }

    public void onSelectInvoices(LocalDate date) {
        list.clear();
        fullAmount = 0;
        try {
            int rowNumber = 0;
            Double grandTotal = Double.valueOf(0);
            ResultSet resultSet = MySQL.Search("SELECT * FROM `invoice` INNER JOIN `user` ON `user`.`user_id`=`invoice`.`user_id` WHERE `purchesed_date`='" + date + "' ");

            while (resultSet.next()) {
                rowNumber += 1;
                String invoiceNo = resultSet.getString("invoice_no");
                String purchesedDate = resultSet.getString("purchesed_date");
                String Steward_ID = resultSet.getString("user_id");
                String Steward_Name = resultSet.getString("fullName");
                String Discount = resultSet.getString("discount");
                String Amount = resultSet.getString("total");

                grandTotal += Double.valueOf(Amount);

                list.add(new Invoices(String.valueOf(rowNumber), invoiceNo, purchesedDate, Steward_ID, Steward_Name, Discount, Amount));
            }

            fullAmount = grandTotal;
            txtTotalInvoices.setText(String.valueOf(rowNumber));
            txtGrandTotal.setText(String.valueOf(grandTotal));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onPrintReport(ActionEvent event) {
        try {

            ObservableList<Invoices> items = reportTable.getItems();
            Double cost = Double.valueOf(0);
            int qty = 0;
            Double profit = Double.valueOf(0);

            for (Invoices item : items) {
                ResultSet resultSet = MySQL.Search("SELECT * FROM `invoice_item` INNER JOIN `food` ON `food`.`serial_no`=`invoice_item`.`food_serial_no` WHERE `invoice_no`='" + item.getInvoice_No() + "' ");

                while (resultSet.next()) {
                    qty += resultSet.getDouble("qty");
                    cost += (resultSet.getDouble("cost") * resultSet.getDouble("qty"));

                }

            }

            System.out.println("qty " + qty + " , " + cost + " , " + fullAmount);
            profit = (fullAmount - cost);

            JasperDesign design = JRXmlLoader.load("src/main/resources/com/example/navigate/reports/report/Summery.jrxml");
            JRDesignQuery designQuery = new JRDesignQuery();
            designQuery.setText("SELECT * FROM `invoice` WHERE `purchesed_date`='" + txtDateChooser.getValue() + "' ");
            design.setQuery(designQuery);
            HashMap<String, Object> param = new HashMap<>();
            param.put("Parameter1", String.valueOf(fullAmount));
            param.put("Parameter2", String.valueOf(cost));
            param.put("Parameter3", String.valueOf(profit));
            param.put("subReport", "src/main/resources/com/example/navigate/reports/report/InvoiceItem.jasper");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, MySQL.getInstance());
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
