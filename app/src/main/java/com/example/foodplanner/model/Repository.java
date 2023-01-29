package com.example.foodplanner.model;

import android.content.Context;
import android.util.Log;

import com.example.foodplanner.model.pojos.area.AreaListModel;
import com.example.foodplanner.model.pojos.area.AreaModel;
import com.example.foodplanner.model.pojos.area.CategoryListModel;
import com.example.foodplanner.model.pojos.area.CategoryModel;
import com.example.foodplanner.model.pojos.area.IngredientListModel;
import com.example.foodplanner.model.pojos.area.IngredientModel;
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


public class Repository {
    private static Repository repo = null;
    private ApiServer apiServer;

    public static Repository getInstance(Context context) {
        if (repo == null) {
            repo = new Repository(context);
        }
        return repo;
    }

    private Repository(Context context) {
        apiServer = ApiClient.getInstance(context);
    }

    public void getRandomMeal(NetworkCallBack<List<MealsItem>> networkCallBack) {

        Single<RandomMeal> singleObservable = apiServer.getInspirationMeal();
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
                });
    }

    public void getMealByCategory(String area, NetworkCallBack<List<MealsItem>> networkCallBack) {

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
        Single<AreaListModel> singleObservable = apiServer.getAllAreas();
        singleObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<AreaListModel>() {
                               @Override
                               public void onSubscribe(@NonNull Disposable d) {

                               }

                               @Override
                               public void onSuccess(@NonNull AreaListModel areaListModel) {
                                   networkCallBack.onSuccessResult(areaListModel.getMeals());
                               }

                               @Override
                               public void onError(@NonNull Throwable e) {
                                   networkCallBack.onFailureResult(e.getMessage());
                               }
                           }
                );
    }

    public void getAllCategories(NetworkCallBack<List<CategoryModel>> networkCallBack) {
        Single<CategoryListModel> singleObservable = apiServer.getAllCategories();
        singleObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CategoryListModel>() {
                               @Override
                               public void onSubscribe(@NonNull Disposable d) {

                               }

                               @Override
                               public void onSuccess(@NonNull CategoryListModel categoryListModel) {
                                   networkCallBack.onSuccessResult(categoryListModel.getMeals());

                               }

                               @Override
                               public void onError(@NonNull Throwable e) {
                                   networkCallBack.onFailureResult(e.getMessage());
                               }
                           }
                );
    }

    public void getAllIngredients(NetworkCallBack<List<IngredientModel>> networkCallBack) {
        Single<IngredientListModel> singleObservable = apiServer.getAllIngredient();
        singleObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<IngredientListModel>() {
                               @Override
                               public void onSubscribe(@NonNull Disposable d) {

                               }

                               @Override
                               public void onSuccess(@NonNull IngredientListModel ingredientListModel) {
                                   networkCallBack.onSuccessResult(ingredientListModel.getMeals());

                               }

                               @Override
                               public void onError(@NonNull Throwable e) {
                                   networkCallBack.onFailureResult(e.getMessage());
                               }
                           }
                );
    }

    public void getMealArea(String area, NetworkCallBack<List<MealsItem>> networkCallBack) {

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

    public void getMealCategory(String category, NetworkCallBack<List<MealsItem>> networkCallBack) {

        Single<RandomMeal> singleObservable = apiServer.getMealCategory(category);
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

    public void getMealIngredient(String ingredient, NetworkCallBack<List<MealsItem>> networkCallBack) {

        Single<RandomMeal> singleObservable = apiServer.getMealIngredient(ingredient);
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
    }

    public void getMealByName(String meal, NetworkCallBack<List<MealsItem>> networkCallBack) {
        Single<RandomMeal> singleObservable = apiServer.getMealByName(meal);
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
    }
}
