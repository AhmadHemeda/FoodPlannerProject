package com.example.foodplanner.presenter;

import android.util.Log;

import com.example.randommeal.model.MealsItem;
import com.example.randommeal.model.RandomMeal;
import com.example.randommeal.model.RepositoryInterface;
import com.example.randommeal.network.NetworkCallBack;
import com.example.randommeal.view.RandomMealViewInterface;

import java.util.List;

public class GetRandomMealPresenterPresenter implements GetRandomMealInterfacePresenter, NetworkCallBack {

    private final RandomMealViewInterface _view;
    private final RepositoryInterface _repo;

    public GetRandomMealPresenterPresenter(RandomMealViewInterface _view, RepositoryInterface _repo) {
        this._view = _view;
        this._repo = _repo;
        _repo.getRandomMeal(this);
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
        _repo.getRandomMeal(this);
        Log.i("ApiClient", "getRandomMeal: ");
    }

    @Override
    public void addToFav(RandomMeal randomMeal) {

    }
}
