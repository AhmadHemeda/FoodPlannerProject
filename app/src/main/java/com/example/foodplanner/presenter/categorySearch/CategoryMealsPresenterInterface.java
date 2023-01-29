package com.example.foodplanner.presenter.categorySearch;

import com.example.foodplanner.model.MealsItem;

import java.util.List;

public interface CategoryMealsPresenterInterface {
    void getCategoryMeal(String category);

    List<MealsItem> filteringIngredients(CharSequence s, List<MealsItem> mealsItem);

}
