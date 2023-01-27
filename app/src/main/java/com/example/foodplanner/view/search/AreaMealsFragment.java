package com.example.foodplanner.view.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodplanner.R;
import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.model.RandomMeal;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.network.ApiClient;
import com.example.foodplanner.presenter.areaSearch.AreaMealsPresenter;
import com.example.foodplanner.presenter.areaSearch.AreaMealsPresenterInterface;
import com.example.foodplanner.presenter.areaSearch.AreaMealsViewInterface;
import com.example.foodplanner.view.search.AreaMealsAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class AreaMealsFragment extends Fragment implements AreaMealsViewInterface {

    RecyclerView mealOfAreaRecyclerView;
    GridLayoutManager linearLayout;
    TextView area;
    List<MealsItem> mealsItemResults = new ArrayList<>();
    TextInputEditText search;
    AreaMealsAdapter mealAdapter;
    AreaMealsPresenterInterface areaMealsPresenterInterface;
    View view;
    private static final String TAG = "AreaMealsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_area_meals, container, false);
        mealOfAreaRecyclerView = view.findViewById(R.id.recyclerView_meals);
        area = view.findViewById(R.id.area_name);
        search = view.findViewById(R.id.et_search_meal);
        String areaName = AreaMealsFragmentArgs.fromBundle(getArguments()).getAreaName();
        areaMealsPresenterInterface = new AreaMealsPresenter(this::showMeals, Repository.getInstance(requireContext()),areaName);

        area.setText(areaName);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mealAdapter.setList(areaMealsPresenterInterface.filteringArea(charSequence,mealsItemResults));
                        }


            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        return view;
    }

    @Override
    public void showMeals(List<MealsItem> randomMeal) {
        linearLayout = new GridLayoutManager(requireContext(),2);
        mealAdapter = new AreaMealsAdapter(requireContext());
        mealAdapter.setList(randomMeal);
        mealOfAreaRecyclerView.setLayoutManager(linearLayout);
        mealOfAreaRecyclerView.setAdapter(mealAdapter);
    }
}