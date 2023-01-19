package com.example.foodplanner.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;
import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.network.ApiClient;
import com.example.foodplanner.presenter.GetRandomMealInterfacePresenter;
import com.example.foodplanner.presenter.GetRandomMealPresenterPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeFragment extends Fragment implements RandomMealViewInterface{
    private static final String TAG = "HomeFragment";
    RecyclerView allRecyclerView;
    GridLayoutManager linearLayout;
    MealAdapter mealAdapter;
    GetRandomMealInterfacePresenter getRandomMealInterfacePresenter;


    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linearLayout = new GridLayoutManager(requireContext(),2);
        mealAdapter = new MealAdapter(requireContext());
        getRandomMealInterfacePresenter =  new GetRandomMealPresenterPresenter(this, Repository.getInstance(requireContext()));


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allRecyclerView = view.findViewById(R.id.recyclerView);
        allRecyclerView.setLayoutManager(linearLayout);
        getRandomMealInterfacePresenter.getRandomMeal();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void showMeals(List<MealsItem> randomMeal) {
        Log.i(TAG, "showMeals:adabter "+randomMeal.get(0).getStrMeal());
        mealAdapter.setList(randomMeal);
        allRecyclerView.setAdapter(mealAdapter);
//        mealAdapter.setList(randomMeal);
    }
}