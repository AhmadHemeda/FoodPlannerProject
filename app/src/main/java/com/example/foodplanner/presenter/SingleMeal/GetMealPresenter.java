package com.example.foodplanner.presenter.SingleMeal;

import android.util.Log;

import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.network.NetworkCallBack;

import java.util.List;

public class GetMealPresenter implements GetMealPresenterInterface , NetworkCallBack<List<MealsItem>> {

    private final GetMealViewInterface _view;
    private final Repository _repo;
    private static final String TAG = "GetMealPresenter";

    public GetMealPresenter(GetMealViewInterface view, Repository repo, String mealName) {
        _view = view;
        _repo = repo;
        _repo.getMealByName(mealName , this);
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
    public void getSingleMeal(String mealName) {
        _repo.getMealByName(mealName , this);
    }
}
