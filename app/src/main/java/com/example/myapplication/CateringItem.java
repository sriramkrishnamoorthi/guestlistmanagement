package com.example.myapplication;

public class CateringItem {

    private String foodItemName;
    private String catererName;
    private double catererCharge;

    public CateringItem(String foodItemName, String catererName, double catererCharge) {
        this.foodItemName = foodItemName;
        this.catererName = catererName;
        this.catererCharge = catererCharge;
    }

    public String getFoodItemName() {
        return foodItemName;
    }

    public void setFoodItemName(String foodItemName) {
        this.foodItemName = foodItemName;
    }

    public String getCatererName() {
        return catererName;
    }

    public void setCatererName(String catererName) {
        this.catererName = catererName;
    }

    public double getCatererCharge() {
        return catererCharge;
    }

    public void setCatererCharge(double catererCharge) {
        this.catererCharge = catererCharge;
    }
}
