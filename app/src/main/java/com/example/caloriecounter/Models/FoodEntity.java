package com.example.caloriecounter.Models;

public class FoodEntity {
    private int fats;
    private int carbohydrates;
    private int proteins;
    private int name;
    private String date;
    private String ingestionTime;

    public FoodEntity(int fats, int carbohydrates, int proteins, int name, String date, String ingestionTime) {
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.proteins = proteins;
        this.name = name;
        this.date = date;
        this.ingestionTime = ingestionTime;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getProteins() {
        return proteins;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIngestionTime() {
        return ingestionTime;
    }

    public void setIngestionTime(String ingestionTime) {
        this.ingestionTime = ingestionTime;
    }
}
