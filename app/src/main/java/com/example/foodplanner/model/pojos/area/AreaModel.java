package com.example.foodplanner.model.pojos.area;

import com.google.gson.annotations.SerializedName;

public class AreaModel {

	@SerializedName("strArea")
	private String strArea;

	public String getStrArea(){
		return strArea;
	}
}