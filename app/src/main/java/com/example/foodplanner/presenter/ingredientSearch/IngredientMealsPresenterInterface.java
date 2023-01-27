package com.example.foodplanner.presenter.ingredientSearch;

import com.example.foodplanner.model.MealsItem;

import java.util.List;

public interface IngredientMealsPresenterInterface {
    public void getIngredientMeal(String ingredient);
    public List<MealsItem> filteringIngredients(CharSequence s , List<MealsItem> mealsItem);

}
