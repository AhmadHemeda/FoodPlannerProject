package com.example.foodplanner.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class Converters {

    @TypeConverter
    public String fromListToString(List<String> stringList) {
        return new Gson().toJson(stringList);
    }

    @TypeConverter
    public List<String> fromStringToList(String s) {
        return new Gson().fromJson(s, new TypeToken<List<String>>() {
        });
    }
}
