package com.example.foodplanner.presenter.categorySearch;

import com.example.foodplanner.model.pojos.area.CategoryModel;

import java.util.List;

public interface GetAllCategoriesPresenterInterface {
    void getAllCategories();

    List<CategoryModel> filteringCategories(CharSequence s, List<CategoryModel> categoryModels);


}
