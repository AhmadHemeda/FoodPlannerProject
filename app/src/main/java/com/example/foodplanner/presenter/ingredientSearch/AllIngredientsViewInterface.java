package com.example.foodplanner.presenter.ingredientSearch;

import com.example.foodplanner.model.pojos.area.CategoryModel;
import com.example.foodplanner.model.pojos.area.IngredientModel;

import java.util.List;

public interface AllIngredientsViewInterface {
    public void showIngredients(List<IngredientModel> ingredientModels);

}
