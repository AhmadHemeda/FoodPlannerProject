package com.example.foodplanner.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplanner.model.FavouriteMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MealDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertFavMeal(FavouriteMeal favouriteMeal);

    @Query("SELECT * FROM meal_table")
    Single<List<FavouriteMeal>> getFavMeals();
}
