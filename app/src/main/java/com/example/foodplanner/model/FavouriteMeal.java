package com.example.foodplanner.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "meal_table")
public class FavouriteMeal {
    @PrimaryKey
    private long mealID;
    private String mealName;
    private String mealImage;
    private String mealArea;
    private List<String> mealIngredients;
    private List<String> mealMeasures;
    private String mealSteps;
    private String mealVideo;

    public FavouriteMeal() {
    }

    public FavouriteMeal(long mealID, String mealName, String mealImage, String mealArea,
                         List<String> mealIngredients, List<String> mealMeasures,
                         String mealSteps, String mealVideo) {
        this.mealID = mealID;
        this.mealName = mealName;
        this.mealImage = mealImage;
        this.mealArea = mealArea;
        this.mealIngredients = mealIngredients;
        this.mealMeasures = mealMeasures;
        this.mealSteps = mealSteps;
        this.mealVideo = mealVideo;
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
}
