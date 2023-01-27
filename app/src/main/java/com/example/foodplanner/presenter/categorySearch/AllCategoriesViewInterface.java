package com.example.foodplanner.presenter.categorySearch;

import com.example.foodplanner.model.pojos.area.AreaModel;
import com.example.foodplanner.model.pojos.area.CategoryModel;

import java.util.List;

public interface AllCategoriesViewInterface {

    public void showCategories(List<CategoryModel> categoryModels);
}
