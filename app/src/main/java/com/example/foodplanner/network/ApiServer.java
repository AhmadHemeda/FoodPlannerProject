package com.example.foodplanner.network;

import com.example.foodplanner.model.RandomMeal;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServer {

    //@GET("filter.php?a=Canadian")
//    @GET("random.php")
//     Call<RandomMeal> getRandomMeal();
//
////    @GET("categories.php")
////    Call<Categorys> getCategories();
//
    @GET("filter.php")
    Single<RandomMeal> getMealByCategory(@Query("a")String category);
    // https://www.themealdb.com/api/json/v1/1/filter.php?a=britin/
//
//
//    @GET("search.php")
//    Call<RandomMeal> getMealByName(@Query("s")String mealName);
}
