package com.example.foodplanner.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.foodplanner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SplashFragment extends Fragment {


    View _view;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    Handler handler;
    Runnable runnable;

    public SplashFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        _view = inflater.inflate(R.layout.fragment_splash, container, false);

        return _view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        handler = new Handler();

        runnable = () -> {
            SharedPreferences sharedPref = requireContext().getSharedPreferences("setting", Context.MODE_PRIVATE);

            FirebaseUser user = firebaseAuth.getCurrentUser();
            boolean isFirst = sharedPref.getBoolean("first_look", true);

            if (!isFirst) {
                Navigation.findNavController(_view).navigate(SplashFragmentDirections.actionSplashFragmentToLoaderFragment());
            } else if (user == null) {
                Navigation.findNavController(_view).navigate(SplashFragmentDirections.actionSplashFragmentToOnboardingFragment());
            }
        };

        handler.postDelayed(runnable, 4000);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 2000);
    }
}