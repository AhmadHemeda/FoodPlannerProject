package com.example.foodplanner.presenter.categorySearch;

import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.model.pojos.area.CategoryModel;
import com.example.foodplanner.model.pojos.area.IngredientModel;

import java.util.List;

public interface GetAllCategoriesPresenterInterface {
    public void getAllCategories();
    public List<CategoryModel> filteringCategories(CharSequence s , List<CategoryModel> categoryModels);


}
