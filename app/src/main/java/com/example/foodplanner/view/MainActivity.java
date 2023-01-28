package com.example.foodplanner.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.foodplanner.InternetConnection;
import com.example.foodplanner.R;
import com.example.foodplanner.network.ApiClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    FirebaseAuth auth;
    MutableLiveData<Boolean> isConnected = new MutableLiveData<>();
    private boolean isUser = false;
    private static final String TAG = "MainActivity";
    BottomNavigationView navigationView;

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null) {
            isUser = true;
            navigationView.setSelectedItemId(R.id.nav_home);
        }
        ConstraintLayout constraintLayout = findViewById(R.id.mainActivity);
        Snackbar snackbar =  Snackbar
        .make(constraintLayout,"",Snackbar.LENGTH_LONG);
        snackbar.setTextColor(getColor(R.color.white));

        View snackbarLayout = snackbar.getView();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lp.setMargins(0, 0, 0, 0);

        snackbarLayout.setLayoutParams(lp);

        new InternetConnection(this).observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (isConnected) {
                    snackbar.setText("Internet Is Connected");
                    snackbar.show();
                    snackbar.setBackgroundTint(getColor(R.color.green_dark));
                }
                else {
                    snackbar.setBackgroundTint(getColor(R.color.red));
                    snackbar.setText("Internet Is Not Connected");
                    snackbar.show();
                    android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(getApplicationContext());
                    builder1.setTitle("No Internet");
                    builder1.setMessage("Check Your Internet Connection.");
                    builder1.setCancelable(true);

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

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
//        navigationView.setSelectedItemId(R.id.nav_home);
        auth = FirebaseAuth.getInstance();


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