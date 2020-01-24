package com.example.caloriecounter.Models;

public class User {
    private String gender;
    private int weight;
    private int height;
    private int age;
    private double exerciseCoefficient;

    public User(String gender, int weight, int height, int age, double exerciseCoefficient) {
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.exerciseCoefficient = exerciseCoefficient;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getExerciseCoefficient() {
        return exerciseCoefficient;
    }

    public void setExerciseCoefficient(double exerciseCoefficient) {
        this.exerciseCoefficient = exerciseCoefficient;
    }
}
