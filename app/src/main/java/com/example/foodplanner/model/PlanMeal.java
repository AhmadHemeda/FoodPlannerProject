package com.example.foodplanner.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.util.List;

@Entity(tableName = "plan_meal_table", primaryKeys = {"mealID", "mealDay"})
public class PlanMeal {

    @NonNull
    private long mealID;
    private String mealName;
    private String mealImage;
    private String mealArea;
    private List<String> mealIngredients;
    private List<String> mealMeasures;
    private String mealSteps;
    private String mealVideo;
    @NonNull
    private String mealDay;

    public PlanMeal() {
    }

    public PlanMeal(long mealID, String mealName, String mealImage, String mealArea,
                    List<String> mealIngredients, List<String> mealMeasures,
                    String mealSteps, String mealVideo, String mealDay) {
        this.mealID = mealID;
        this.mealName = mealName;
        this.mealImage = mealImage;
        this.mealArea = mealArea;
        this.mealIngredients = mealIngredients;
        this.mealMeasures = mealMeasures;
        this.mealSteps = mealSteps;
        this.mealVideo = mealVideo;
        this.mealDay = mealDay;
    }

    public void setMealID(long mealID) {
        this.mealID = mealID;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public void setMealImage(String mealImage) {
        this.mealImage = mealImage;
    }

    public void setMealArea(String mealArea) {
        this.mealArea = mealArea;
    }

    public void setMealIngredients(List<String> mealIngredients) {
        this.mealIngredients = mealIngredients;
    }

    public void setMealMeasures(List<String> mealMeasures) {
        this.mealMeasures = mealMeasures;
    }

    public void setMealSteps(String mealSteps) {
        this.mealSteps = mealSteps;
    }

    public void setMealVideo(String mealVideo) {
        this.mealVideo = mealVideo;
    }

    public void setMealDay(@NonNull String mealDay) {
        this.mealDay = mealDay;
    }

    public long getMealID() {
        return mealID;
    }

    public String getMealName() {
        return mealName;
    }

    public String getMealImage() {
        return mealImage;
    }

    public String getMealArea() {
        return mealArea;
    }

    public List<String> getMealIngredients() {
        return mealIngredients;
    }

    public List<String> getMealMeasures() {
        return mealMeasures;
    }

    public String getMealSteps() {
        return mealSteps;
    }

    public String getMealVideo() {
        return mealVideo;
    }

    public String getMealDay() {
        return mealDay;
    }
}
