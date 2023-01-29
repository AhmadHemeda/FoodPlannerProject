package com.example.foodplanner.presenter.areaSearch;

import com.example.foodplanner.model.MealsItem;

import java.util.List;

public interface AreaMealsPresenterInterface {
    void getRandomMeal(String area);

    List<MealsItem> filteringArea(CharSequence s, List<MealsItem> mealsItem);

}
