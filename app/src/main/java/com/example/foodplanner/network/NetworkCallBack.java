package com.example.foodplanner.network;

import com.example.foodplanner.model.MealsItem;

import java.util.List;

public interface NetworkCallBack<T> {
    public void onSuccessResult(T randomMeal);
    public void onFailureResult(String errorMsg);
}
