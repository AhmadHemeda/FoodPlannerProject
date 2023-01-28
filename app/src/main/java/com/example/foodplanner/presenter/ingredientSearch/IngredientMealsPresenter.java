package com.example.foodplanner.presenter.ingredientSearch;

import android.util.Log;

import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.network.NetworkCallBack;

import java.util.List;
import java.util.stream.Collectors;

public class IngredientMealsPresenter implements IngredientMealsPresenterInterface , NetworkCallBack<List<MealsItem>> {

    private final IngredientMealsViewInterface _view;
    private final Repository _repo;
    private static final String TAG = "IngredientMealsPresente";

    public IngredientMealsPresenter(IngredientMealsViewInterface view, Repository repo ,String ingredient) {
        _view = view;
        _repo = repo;
        _repo.getMealIngredient(ingredient,this);
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
    public void getIngredientMeal(String ingredient) {
        _repo.getMealIngredient(ingredient,this);

    }

    @Override
    public List<MealsItem> filteringIngredients(CharSequence s, List<MealsItem> mealsItem) {
        return mealsItem.stream()
                .filter(response->response.getStrMeal().toLowerCase().startsWith(s.toString().toLowerCase())).collect(Collectors.toList());
    }


}
