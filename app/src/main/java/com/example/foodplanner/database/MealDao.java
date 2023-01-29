package com.example.foodplanner.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplanner.model.FavouriteMeal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertFavMeal(FavouriteMeal favouriteMeal);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertAllFavMeal(ArrayList<FavouriteMeal> favouriteMeal);

    @Query("SELECT * FROM meal_table")
    Single<List<FavouriteMeal>> getFavMeals();

    @Query("SELECT EXISTS (SELECT 1 FROM meal_table WHERE mealID = :mealID)")
    Single<Boolean> getFavMealByID(Long mealID);

    @Delete
    Completable deleteFavMeal(FavouriteMeal favouriteMeal);

    @Query("DELETE FROM meal_table")
    Completable deleteAllFavMeals();
}
