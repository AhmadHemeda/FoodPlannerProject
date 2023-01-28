package com.example.foodplanner;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.Observer;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        new InternetConnection(this).observeForever(new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean isConnected) {
//                if (isConnected){
//                    Toast.makeText(MyApplication.this, "Network in Connected", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(MyApplication.this, "Network in Not Connected", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });
    }
}
