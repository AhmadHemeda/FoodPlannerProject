package com.example.foodplanner.model;

import android.content.Context;
import android.util.Log;

import com.example.foodplanner.model.pojos.area.AreaListModel;
import com.example.foodplanner.model.pojos.area.AreaModel;
import com.example.foodplanner.network.ApiClient;
import com.example.foodplanner.network.ApiServer;
import com.example.foodplanner.network.NetworkCallBack;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class Repository{
    private static Repository repo = null;
    private ApiServer apiServer;

    public static Repository getInstance(Context context) {
        if (repo == null) {
            repo = new Repository(context);
        }
        return repo;
    }

    private Repository(Context context) {
        apiServer = ApiClient.getInstance();
    }

    public void getMealByCategory(String area,NetworkCallBack<List<MealsItem>> networkCallBack) {


            Single<RandomMeal> singleObservable = apiServer.getMealArea(area);
            singleObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<RandomMeal>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull RandomMeal randomMeal) {
                            networkCallBack.onSuccessResult(randomMeal.getMeals());
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            networkCallBack.onFailureResult(e.getMessage());
                        }
                    }
            );

        Log.i("ApiClient", "Repository getRandomMeal: ");

    }
    public void getAllAreas(NetworkCallBack<List<AreaModel>> networkCallBack) {




    }

}
