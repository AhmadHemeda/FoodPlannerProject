package com.example.foodplanner.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String TAG = "ApiClient";
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static ApiServer apiServer = null;

    private ApiClient() {}

    public static ApiServer getInstance() {
        if (apiServer == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build();

            apiServer =retrofit.create(ApiServer.class);
        }
        return apiServer;
    }



}
