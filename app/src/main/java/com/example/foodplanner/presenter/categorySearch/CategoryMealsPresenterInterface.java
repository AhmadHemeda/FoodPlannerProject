package com.example.foodplanner.presenter.categorySearch;

import com.example.foodplanner.model.MealsItem;

import java.util.List;

public interface CategoryMealsPresenterInterface {
    public void getCategoryMeal(String category);
    public List<MealsItem> filteringIngredients(CharSequence s , List<MealsItem> mealsItem);

}
