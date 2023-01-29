package com.example.foodplanner.presenter.randomMeals;

import android.util.Log;


import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.network.NetworkCallBack;

import java.util.List;

public class GetRandomMealPresenterPresenter implements GetRandomMealInterfacePresenter, NetworkCallBack<List<MealsItem>> {

    private final RandomMealViewInterface _view;
    private final Repository _repo;

    public GetRandomMealPresenterPresenter(RandomMealViewInterface _view, Repository _repo) {
        this._view = _view;
        this._repo = _repo;
        _repo.getMealByCategory("American", this);
        Log.i("ApiClient", "GetRandomMealPresenterPresenter: ");
    }

    @Override
    public void onSuccessResult(List<MealsItem> randomMeal) {
        _view.showMeals(randomMeal);
        Log.i("ApiClient", "onSuccessResult: ");
    }

    @Override
    public void onFailureResult(String errorMsg) {
        Log.i("ApiClient", "onFailureResult: ");
    }

    @Override
    public void getRandomMeal() {
        _repo.getMealByCategory("American", this);
        Log.i("ApiClient", "getRandomMeal: ");
    }

}
