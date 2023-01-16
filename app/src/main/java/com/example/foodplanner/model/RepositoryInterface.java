package com.example.foodplanner.model;

import com.example.foodplanner.network.NetworkCallBack;

public interface RepositoryInterface {
    void getRandomMeal(NetworkCallBack networkCallBack);

}
