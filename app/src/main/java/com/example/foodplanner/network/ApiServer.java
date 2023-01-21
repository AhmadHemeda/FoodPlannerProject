package com.example.foodplanner.network;

import com.example.foodplanner.model.RandomMeal;
import com.example.foodplanner.model.pojos.area.AreaListModel;

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
    Single<RandomMeal> getMealArea(@Query("a")String category);

    @GET("list.php?a=list")
    Single<AreaListModel> getAllAreas();
//www.themealdb.com/api/json/v1/1/list.php?a=list

//
//
    @GET("search.php")
    Single<RandomMeal> getMealByName(@Query("s")String mealName);
}
