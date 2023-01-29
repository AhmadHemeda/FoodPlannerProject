package com.example.foodplanner.presenter.areaSearch;

import android.util.Log;

import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.network.NetworkCallBack;

import java.util.List;
import java.util.stream.Collectors;

public class AreaMealsPresenter implements AreaMealsPresenterInterface, NetworkCallBack<List<MealsItem>> {
    private final AreaMealsViewInterface _view;
    private final Repository _repo;
    private static final String TAG = "AreaMealsPresenter";

    public AreaMealsPresenter(AreaMealsViewInterface view, Repository repo, String area) {
        _view = view;
        _repo = repo;
        _repo.getMealArea(area, this);
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
    public void getRandomMeal(String area) {
        _repo.getMealArea(area, this);
    }

    @Override
    public List<MealsItem> filteringArea(CharSequence s, List<MealsItem> mealsItem) {
        return mealsItem.stream()
                .filter(response -> response
                        .getStrMeal()
                        .toLowerCase()
                        .startsWith(s.toString().toLowerCase()))
                .collect(Collectors.toList());
    }
}
