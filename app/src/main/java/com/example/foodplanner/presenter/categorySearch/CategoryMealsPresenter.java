package com.example.foodplanner.presenter.categorySearch;

import android.util.Log;

import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.network.NetworkCallBack;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMealsPresenter implements CategoryMealsPresenterInterface , NetworkCallBack<List<MealsItem>> {

    private final CategoryMealsViewInterface _view;
    private final Repository _repo;
    private static final String TAG = "CategoryMealsPresenter";
    public CategoryMealsPresenter(CategoryMealsViewInterface view, Repository repo , String category) {
        _view = view;
        _repo = repo;
        _repo.getMealCategory(category,this);
    }

    @Override
    public void onSuccessResult(List<MealsItem> randomMeal) {
        _view.showMeals(randomMeal);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        Log.i(TAG, "onFailureResult: ");
    }

    @Override
    public void getCategoryMeal(String category) {
        _repo.getMealCategory(category,this);
    }

    @Override
    public List<MealsItem> filteringIngredients(CharSequence s, List<MealsItem> mealsItem) {
        return mealsItem.stream()
                .filter(response->response.getStrMeal().toLowerCase().startsWith(s.toString().toLowerCase())).collect(Collectors.toList());
    }
}
