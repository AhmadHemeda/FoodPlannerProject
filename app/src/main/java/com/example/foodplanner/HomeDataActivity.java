package com.example.foodplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.foodplanner.view.FavouritesFragment;
import com.example.foodplanner.view.HomeFragment;
import com.example.foodplanner.view.PlansFragment;
import com.example.foodplanner.view.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeDataActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_data);
        getSupportActionBar().hide();
        navigationView = findViewById(R.id.bottom_nav_bar);
        getSupportFragmentManager().beginTransaction().replace(R.id.home_activity,new HomeFragment()).commit();
        navigationView.setSelectedItemId(R.id.nav_home);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;

                    case R.id.nav_search:
                        fragment = new SearchFragment();
                        break;
                    case R.id.nav_fav:
                        fragment = new FavouritesFragment();
                        break;
                    case R.id.nav_plane:
                        fragment = new PlansFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.home_activity,fragment).commit();
                return true;
            }
        });
    }
}