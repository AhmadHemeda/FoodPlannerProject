package com.example.foodplanner.network;

import com.example.foodplanner.model.RandomMeal;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServer {
    @GET("random.php")
     Call<RandomMeal> getRandomMeal();
}
