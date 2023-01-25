package com.example.foodplanner.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "plan_meal_table" ,primaryKeys = {"mealID","mealDay"})
public class PlanMeal {

    @NonNull
    private final long mealID;
    private final String mealName;
    private final String mealImage;
    private final String mealArea;
    private final List<String> mealIngredients;
    private final List<String> mealMeasures;
    private final String mealSteps;
    private final String mealVideo;
    @NonNull
    private final String mealDay;

    public PlanMeal(long mealID, String mealName, String mealImage, String mealArea, List<String> mealIngredients, List<String> mealMeasures, String mealSteps, String mealVideo, String mealDay) {
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
