package com.example.foodplanner.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SplashFragment extends Fragment {


    View _view;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        _view = inflater.inflate(R.layout.fragment_splash, container, false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPref = requireContext().getSharedPreferences("setting", Context.MODE_PRIVATE);

                FirebaseUser user = firebaseAuth.getCurrentUser();
                boolean isFirst = sharedPref.getBoolean("first_look", true);

                if (!isFirst) {
                    Navigation.findNavController(_view).navigate(SplashFragmentDirections.actionSplashFragmentToLoaderFragment());
                } else if (user == null) {
                    Navigation.findNavController(_view).navigate(SplashFragmentDirections.actionSplashFragmentToOnboardingFragment());


                }
                else {
                Navigation.findNavController(_view).navigate(SplashFragmentDirections.actionSplashFragmentToLoaderFragment());
                }
            }
        },4000);
        return _view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }
}