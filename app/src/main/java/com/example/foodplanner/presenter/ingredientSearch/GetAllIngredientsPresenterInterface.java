package com.example.foodplanner.presenter.ingredientSearch;

import com.example.foodplanner.model.pojos.area.IngredientModel;

import java.util.List;

public interface GetAllIngredientsPresenterInterface {
    void getAllIngredients();

    List<IngredientModel> filteringIngredients(CharSequence s, List<IngredientModel> ingredientModels);

}
