package com.example.navigate.model;

public class InvoiceItem {

    String Serial_No;
    String Food_Item;
    String Quantity;
    String Unit_Price;

    public InvoiceItem(String serial_No, String food_Item, String quantity, String unit_Price) {
        this.Serial_No = serial_No;
        this.Food_Item = food_Item;
        this.Quantity = quantity;
        this.Unit_Price = unit_Price;
    }


    public String getSerial_No() {
        return Serial_No;
    }

    public void setSerial_No(String serial_No) {
        Serial_No = serial_No;
    }

    public String getFood_Item() {
        return Food_Item;
    }

    public void setFood_Item(String food_Item) {
        Food_Item = food_Item;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getUnit_Price() {
        return Unit_Price;
    }

    public void setUnit_Price(String unit_Price) {
        Unit_Price = unit_Price;
    }
}
