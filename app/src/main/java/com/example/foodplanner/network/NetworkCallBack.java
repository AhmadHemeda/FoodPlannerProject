package com.example.foodplanner.network;

public interface NetworkCallBack<T> {
    public void onSuccessResult(T randomMeal);

    public void onFailureResult(String errorMsg);
}
