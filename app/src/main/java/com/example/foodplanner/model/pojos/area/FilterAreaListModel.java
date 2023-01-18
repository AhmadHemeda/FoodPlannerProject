package com.example.foodplanner.model.pojos.area;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FilterAreaListModel {

	@SerializedName("meals")
	private List<FilterAreaModel> meals;

	public List<FilterAreaModel> getMeals(){
		return meals;
	}
}