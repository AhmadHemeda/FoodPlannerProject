package com.example.foodplanner.view;

import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.foodplanner.InternetConnection;
import com.example.foodplanner.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    FirebaseAuth auth;
    private boolean isUser = false;
    BottomNavigationView navigationView;

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null) {
            isUser = true;
            navigationView.setSelectedItemId(R.id.nav_home);
        }

        ConstraintLayout constraintLayout = findViewById(R.id.mainActivity);
        Snackbar snackbar = Snackbar
                .make(constraintLayout, "", Snackbar.LENGTH_LONG);
        snackbar.setTextColor(getColor(R.color.white));

        View snackbarLayout = snackbar.getView();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lp.setMargins(0, 0, 0, 0);

        snackbarLayout.setLayoutParams(lp);

        new InternetConnection(this).observeForever(isConnected -> {
            if (isConnected) {
                snackbar.setText("Internet Is Connected");
                snackbar.show();
                snackbar.setBackgroundTint(getColor(R.color.green_dark));
            } else {
                snackbar.setBackgroundTint(getColor(R.color.red));
                snackbar.setText("Internet Is Not Connected");
                snackbar.show();
            }

        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController);
        getSupportActionBar().hide();
        navigationView = findViewById(R.id.bottom_nav_bar);
        auth = FirebaseAuth.getInstance();


        navigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.homeFragment);
                    return true;

                case R.id.nav_search:
                    Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.allSearchesFragment);
                    return true;

                case R.id.nav_fav:
                    Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.favouritesFragment);
                    return true;

                case R.id.nav_plane:
                    Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.plansFragment);
                    return true;

                case R.id.nav_user:
                    Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.userFragment);
                    return true;
            }
            return false;
        });
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            switch (destination.getId()) {
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
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}