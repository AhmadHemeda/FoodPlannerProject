package com.example.foodplanner.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    FirebaseAuth auth;
    private boolean isUser = false;
    private static final String TAG = "MainActivity";
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController);
        getSupportActionBar().hide();
        navigationView = findViewById(R.id.bottom_nav_bar);
        navigationView.setSelectedItemId(R.id.nav_home);
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {

            isUser = true;
        }

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.homeFragment);
                        return true;

                    case R.id.nav_search:
                        Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.allSearchesFragment);
                        return true;

                    case R.id.nav_fav:
//                        if (!isUser) {
//                            showDialog();
//                            Toast.makeText(MainActivity.this, "No User", Toast.LENGTH_SHORT).show();
//                            isUser = false;
//                        } else {
                            Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.favouritesFragment);
//                        }
                        return true;

                    case R.id.nav_plane:
//                        if (!isUser) {
//                            showDialog();
//                            Toast.makeText(MainActivity.this, "No User", Toast.LENGTH_SHORT).show();
//                            isUser = false;
//                        } else if(auth !=null){
                            Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.plansFragment);
//                        }
                        return true;

                    case R.id.nav_user:
//                        if (!isUser) {
//                            showDialog();
//                            Toast.makeText(MainActivity.this, "No User", Toast.LENGTH_SHORT).show();
//                            isUser = false;
//                        } else {
                            Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.userFragment);
//                        }
                        return true;
                }
//                getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity,fragment).commit();
                return false;
            }

        });
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
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
            }
        });
    }

    public void showDialog() {
        ShowAlertDialog showAlertDialog = new ShowAlertDialog();
        showAlertDialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public boolean onSupportNavigateUp() {

        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}