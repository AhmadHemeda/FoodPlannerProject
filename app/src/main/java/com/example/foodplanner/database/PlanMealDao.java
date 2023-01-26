package com.example.foodplanner.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplanner.model.FavouriteMeal;
import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.model.PlanMeal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface PlanMealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertPlanMeal(PlanMeal planMeal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllPlanMeal(ArrayList<PlanMeal> planMeal);

    @Query("SELECT * FROM plan_meal_table WHERE mealDay = :mealDay")
    Single<List<PlanMeal>> getPlanMeals(String mealDay);

    @Delete
    Completable deletePlanMeal(PlanMeal planMeal);

    @Query("DELETE FROM plan_meal_table")
    Completable deleteAllPlanMeals();

}
