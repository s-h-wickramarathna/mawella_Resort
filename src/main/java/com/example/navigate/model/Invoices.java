package com.example.navigate.model;

public class Invoices {

    public String No;
    public String Invoice_No;
    public String Purchesed_Date;
    public String Steward_ID;
    public String Steward_Name;
    public String Discount;
    public String Amount;

    public Invoices(String no, String invoice_No, String purchesed_Date,  String steward_ID, String steward_Name, String discount, String amount) {
        No = no;
        Invoice_No = invoice_No;
        Purchesed_Date = purchesed_Date;
        Steward_ID = steward_ID;
        Steward_Name = steward_Name;
        Discount = discount;
        Amount = amount;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getInvoice_No() {
        return Invoice_No;
    }

    public void setInvoice_No(String invoice_No) {
        Invoice_No = invoice_No;
    }

    public String getPurchesed_Date() {
        return Purchesed_Date;
    }

    public void setPurchesed_Date(String purchesed_Date) {
        Purchesed_Date = purchesed_Date;
    }

    public String getSteward_ID() {
        return Steward_ID;
    }

    public void setSteward_ID(String steward_ID) {
        Steward_ID = steward_ID;
    }

    public String getSteward_Name() {
        return Steward_Name;
    }

    public void setSteward_Name(String steward_Name) {
        Steward_Name = steward_Name;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
