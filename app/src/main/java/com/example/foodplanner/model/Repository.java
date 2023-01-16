package com.example.foodplanner.model;

import android.content.Context;
import android.util.Log;

import com.example.randommeal.network.NetworkCallBack;
import com.example.randommeal.network.RemoteSource;

public class Repository implements RepositoryInterface{
    Context context;
    RemoteSource remoteSource;
    private static Repository repo = null;

    public static Repository getInstance(RemoteSource remoteSource, Context context) {
        if (repo == null) {
            repo = new Repository(context,remoteSource);
            Log.i("ApiClient", "getInstance: ");
        }
        return repo;
    }

    public Repository(Context context, RemoteSource remoteSource) {
        this.context = context;
        this.remoteSource = remoteSource;
        Log.i("ApiClient", "Repository: ");

    }

    @Override
    public void getRandomMeal(NetworkCallBack networkCallBack) {
        remoteSource.enqueueCall(networkCallBack);
        Log.i("ApiClient", "Repository getRandomMeal: ");

    }
}
