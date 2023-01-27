package com.example.foodplanner.presenter.ingredientSearch;

import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.model.pojos.area.AreaModel;
import com.example.foodplanner.model.pojos.area.IngredientModel;

import java.util.List;

public interface GetAllIngredientsPresenterInterface {
    public void getAllIngredients();
    public List<IngredientModel> filteringIngredients(CharSequence s , List<IngredientModel> ingredientModels);

}
