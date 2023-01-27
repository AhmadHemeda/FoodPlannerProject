package com.example.foodplanner.presenter.areaSearch;

import android.util.Log;

import com.example.foodplanner.model.Repository;
import com.example.foodplanner.model.pojos.area.AreaModel;
import com.example.foodplanner.network.NetworkCallBack;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllAreasPresenter implements GetAllAreasPresenterInterface , NetworkCallBack<List<AreaModel>> {
    private final AllAreasViewInterface _view;
    private final Repository _repo;
    private static final String TAG = "GetAllAreasPresenter";
    public GetAllAreasPresenter(AllAreasViewInterface _view, Repository _repo) {
        this._view = _view;
        this._repo = _repo;
        _repo.getAllAreas(this);
    }

    @Override
    public void onSuccessResult(List<AreaModel> randomMeal) {
        _view.showMeals(randomMeal);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        Log.i(TAG, "onFailureResult: ");
    }

    @Override
    public void getAllAreas() {
        _repo.getAllAreas(this);
    }

    @Override
    public List<AreaModel> filteringIngredients(CharSequence s, List<AreaModel> areaModels) {
        return areaModels.stream().filter((e->e.getStrArea().startsWith(s.toString()))).collect(Collectors.toList());
    }
}
