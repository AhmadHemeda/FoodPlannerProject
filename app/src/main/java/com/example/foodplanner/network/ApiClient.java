package com.example.foodplanner.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static ApiServer apiServer = null;


    private ApiClient() {
    }

    public static ApiServer getInstance(Context context) {

        if (apiServer == null) {
            Interceptor onlineInterceptor = chain -> {
                Response response = chain.proceed(chain.request());
                int maxAge = 60;
                return response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .removeHeader("Pragma")
                        .build();
            };

            Interceptor offlineInterceptor = chain -> {
                Request request = chain.request();
                if (!isInternetAvailable(context)) {
                    int maxStale = 60 * 60 * 24 * 30;
                    request = request.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return chain.proceed(request);
            };

            long cashSize = 10 * 1024 * 1024;
            Cache cache = new Cache(context.getCacheDir(), cashSize);

            OkHttpClient client = new OkHttpClient.Builder()
                    .cache(cache)
                    .addInterceptor(offlineInterceptor)
                    .addNetworkInterceptor(onlineInterceptor)
                    .build();

            Gson gson = new GsonBuilder().setLenient().create();
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build();

            apiServer = retrofit.create(ApiServer.class);
        }
        return apiServer;
    }


    public static boolean isInternetAvailable(Context context) {
        boolean isConnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected())
            isConnected = true;
        return isConnected;
    }
}
