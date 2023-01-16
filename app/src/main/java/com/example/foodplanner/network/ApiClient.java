package com.example.foodplanner.network;

import android.util.Log;

import com.example.foodplanner.model.RandomMeal;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient implements RemoteSource{

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static Retrofit retrofit = null;
    private static ApiClient client = null;

    private ApiClient() {}

    public static ApiClient getInstance() {
        if (client == null) {
            client = new ApiClient();
        }
        return client;
    }

private static final String TAG = "ApiClient";
    @Override
    public void enqueueCall(NetworkCallBack networkCallBack) {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        ApiServer apiServer =retrofit.create(ApiServer.class);
        Call<RandomMeal> call = apiServer.getRandomMeal();
        call.enqueue(new Callback<RandomMeal>() {
            @Override
            public void onResponse(Call<RandomMeal> call, Response<RandomMeal> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, response.body().toString());
                    networkCallBack.onSuccessResult(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<RandomMeal> call, Throwable t) {
                Log.i("ApiClient", "Failed: ");
                networkCallBack.onFailureResult(t.getMessage());
            }
        });
    }
}
