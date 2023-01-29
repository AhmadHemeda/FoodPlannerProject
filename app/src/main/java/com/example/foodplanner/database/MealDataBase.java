package com.example.foodplanner.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.foodplanner.model.FavouriteMeal;
import com.example.foodplanner.model.PlanMeal;

@Database(entities = {FavouriteMeal.class, PlanMeal.class}, version = 2, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class MealDataBase extends RoomDatabase {
    public static final String PLAN = "planMeal";
    public static final String FAV = "Favourite";
    public static final String FIRESTORE = "database";
    private static MealDataBase instance = null;

    public abstract MealDao mealDao();

    public abstract PlanMealDao planMealDao();

    public static synchronized MealDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MealDataBase.class, "meal_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
