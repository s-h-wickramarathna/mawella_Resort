package com.model;

public class Food {

    public String No, SerialNo, FoodName, Cost, Description, SellingPrice, Category, CreatedAt, UpdatedAt;

    public Food(String no, String serialNo, String foodName, String description, String cost, String sellingPrice, String category, String createdAt, String updatedAt) {
        No = no;
        SerialNo = serialNo;
        FoodName = foodName;
        Cost = cost;
        SellingPrice = sellingPrice;
        Category = category;
        Description = description;
        CreatedAt = createdAt;
        UpdatedAt = updatedAt;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        UpdatedAt = updatedAt;
    }
}
