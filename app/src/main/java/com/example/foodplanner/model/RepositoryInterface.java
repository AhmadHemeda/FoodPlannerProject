package com.example.foodplanner.model;

import com.example.randommeal.network.NetworkCallBack;

public interface RepositoryInterface {
    void getRandomMeal(NetworkCallBack networkCallBack);

}
