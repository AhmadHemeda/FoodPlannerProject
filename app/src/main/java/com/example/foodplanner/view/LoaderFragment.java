package com.example.foodplanner.view;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.R;


public class LoaderFragment extends DialogFragment {

    View _view;

    public LoaderFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_loader, container, false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Navigation.findNavController(_view).navigate(LoaderFragmentDirections.actionLoaderFragmentToHomeDataActivity());
            }
        },5000);
        // Inflate the layout for this fragment
        return _view;
    }
}