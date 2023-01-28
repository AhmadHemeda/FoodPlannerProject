package com.example.foodplanner.presenter.SingleMeal;

import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.network.NetworkCallBack;

import java.util.List;

public interface GetMealPresenterInterface extends NetworkCallBack<List<MealsItem>> {
    public void getSingleMeal(String mealName);

}
