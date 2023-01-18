package com.example.foodplanner.model.pojos.area;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AreaListModel {

	@SerializedName("meals")
	private List<AreaModel> meals;

	public List<AreaModel> getMeals(){
		return meals;
	}
}