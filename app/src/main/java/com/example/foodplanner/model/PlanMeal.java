package com.example.foodplanner.model;

import androidx.room.Entity;

import java.util.List;

@Entity(tableName = "plan_meal_table")
public class PlanMeal extends FavouriteMeal {

    private final String mealDay;

    public PlanMeal(long mealID, String mealName, String mealImage, String mealArea, List<String> mealIngredients, List<String> mealMeasures, String mealSteps, String mealVideo, String mealDay) {
        super(mealID, mealName, mealImage, mealArea, mealIngredients, mealMeasures, mealSteps, mealVideo);
        this.mealDay = mealDay;
    }

    public String getMealDay() {
        return mealDay;
    }
}
