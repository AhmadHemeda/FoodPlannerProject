package com.example.foodplanner.presenter.areaSearch;

import com.example.foodplanner.model.pojos.area.AreaModel;
import com.example.foodplanner.model.pojos.area.IngredientModel;

import java.util.List;

public interface GetAllAreasPresenterInterface {
    void getAllAreas();

    List<AreaModel> filteringIngredients(CharSequence s, List<AreaModel> areaModels);


}
