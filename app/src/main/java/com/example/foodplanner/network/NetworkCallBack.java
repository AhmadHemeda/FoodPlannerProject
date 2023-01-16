package com.example.foodplanner.network;

import com.example.foodplanner.model.MealsItem;

import java.util.List;

public interface NetworkCallBack {
    public void onSuccessResult(List<MealsItem> randomMeal);
    public void onFailureResult(String errorMsg);
}
