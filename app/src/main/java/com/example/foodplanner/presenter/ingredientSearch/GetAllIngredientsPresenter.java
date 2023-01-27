package com.example.foodplanner.presenter.ingredientSearch;

import android.util.Log;

import com.example.foodplanner.model.Repository;
import com.example.foodplanner.model.pojos.area.AreaModel;
import com.example.foodplanner.model.pojos.area.IngredientModel;
import com.example.foodplanner.network.NetworkCallBack;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllIngredientsPresenter implements GetAllIngredientsPresenterInterface, NetworkCallBack<List<IngredientModel>> {

    private final AllIngredientsViewInterface _view;
    private final Repository _repo;
    private static final String TAG = "GetAllIngredientsPresen";
    public GetAllIngredientsPresenter(AllIngredientsViewInterface view, Repository repo) {
        _view = view;
        _repo = repo;
        _repo.getAllIngredients(this);
    }

    @Override
    public void onSuccessResult(List<IngredientModel> randomMeal) {
        _view.showIngredients(randomMeal);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        Log.i(TAG, "onFailureResult: ");
    }

    @Override
    public void getAllIngredients() {
        _repo.getAllIngredients(this);
    }

    @Override
    public List<IngredientModel> filteringIngredients(CharSequence s, List<IngredientModel> ingredientModels) {
        return ingredientModels.stream().filter((e->e.getStrIngredient().startsWith(s.toString()))).collect(Collectors.toList());
    }
}
