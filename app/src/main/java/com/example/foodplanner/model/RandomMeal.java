package com.example.foodplanner.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RandomMeal {

    @SerializedName("meals")
    private List<MealsItem> meals;

    public List<MealsItem> getMeals() {
        Log.i("ApiClient", "Response: ");
        return meals;
    }
}