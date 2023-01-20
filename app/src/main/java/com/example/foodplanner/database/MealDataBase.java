package com.example.foodplanner.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.foodplanner.model.FavouriteMeal;

@Database(entities = {FavouriteMeal.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class MealDataBase extends RoomDatabase {

    private static MealDataBase instance = null;
    public abstract MealDao mealDao();

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
