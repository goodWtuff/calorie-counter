package com.example.caloriecounter.Models;

public class FoodEntity {
    private String name;
    private double  calories;
    private double fats;
    private double  carbohydrates;
    private double  proteins;
    private String ingestionName;

    public FoodEntity(String name, double calories, double fats, double carbohydrates, double proteins, String ingestionTime) {
        this.name = name;
        this.calories = calories;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.proteins = proteins;
        this.ingestionName = ingestionTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public String getIngestionName() {
        return ingestionName;
    }

    public void setIngestionName(String ingestionName) {
        this.ingestionName = ingestionName;
    }
}
