package com.example.foodplanner.presenter.categorySearch;

import android.util.Log;

import com.example.foodplanner.model.Repository;
import com.example.foodplanner.model.pojos.area.CategoryModel;
import com.example.foodplanner.network.NetworkCallBack;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllCategoriesPresenter implements GetAllCategoriesPresenterInterface, NetworkCallBack<List<CategoryModel>> {
    private final AllCategoriesViewInterface _view;
    private final Repository _repo;
    private static final String TAG = "GetAllCategoriesPresent";

    public GetAllCategoriesPresenter(AllCategoriesViewInterface _view, Repository _repo) {
        this._view = _view;
        this._repo = _repo;
        _repo.getAllCategories(this);
    }

    @Override
    public void onSuccessResult(List<CategoryModel> randomMeal) {
        _view.showCategories(randomMeal);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        Log.i(TAG, "onFailureResult: ");
    }

    @Override
    public void getAllCategories() {
        _repo.getAllCategories(this);
    }

    @Override
    public List<CategoryModel> filteringCategories(CharSequence s, List<CategoryModel> categoryModels) {
        return categoryModels.stream()
                .filter((e -> e.getStrCategory().toLowerCase().startsWith(s.toString().toLowerCase())))
                .collect(Collectors.toList());
    }

}
