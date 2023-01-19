package com.example.foodplanner.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.foodplanner.R;
import com.example.foodplanner.view.FavouritesFragment;
import com.example.foodplanner.view.HomeFragment;
import com.example.foodplanner.view.PlansFragment;
import com.example.foodplanner.view.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeDataActivity extends AppCompatActivity {
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_data);

    }
    @Override
    public boolean onSupportNavigateUp() {

        return navController.navigateUp()||super.onSupportNavigateUp();
    }
}