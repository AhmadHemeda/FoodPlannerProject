package com.example.foodplanner.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.foodplanner.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController= Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this,navController);
        getSupportActionBar().hide();
        navigationView = findViewById(R.id.bottom_nav_bar);
        navigationView.setSelectedItemId(R.id.nav_home);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.homeFragment);
                        return true;
                    case R.id.nav_search:
                        Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.allSearchesFragment);
                        return true;
                    case R.id.nav_fav:
                        Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.favouritesFragment);
                        return true;
                    case R.id.nav_plane:
                        Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.plansFragment);
                        return true;

                }
//                getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity,fragment).commit();
                return false;
            }

        });
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()){
                    case R.id.splashFragment:
                    case R.id.chooserFragment:
                    case R.id.onboardingFragment:
                    case R.id.loginFragment:
                    case R.id.signUp:
                    case R.id.loaderFragment:
                        navigationView.setVisibility(View.GONE);
                        break;
                    default:
                        navigationView.setVisibility(View.VISIBLE);

                }
            }
        });


    }
    @Override
    public boolean onSupportNavigateUp() {

        return navController.navigateUp()||super.onSupportNavigateUp();
    }
}