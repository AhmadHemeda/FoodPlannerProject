package com.example.foodplanner.view.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.presenter.areaSearch.AreaMealsPresenter;
import com.example.foodplanner.presenter.areaSearch.AreaMealsPresenterInterface;
import com.example.foodplanner.presenter.areaSearch.AreaMealsViewInterface;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class AreaMealsFragment extends Fragment implements AreaMealsViewInterface {

    RecyclerView mealOfAreaRecyclerView;
    GridLayoutManager linearLayout;
    TextView area;
    ProgressBar progressBar;
    List<MealsItem> mealsItemResults = new ArrayList<>();
    TextInputEditText search;
    AreaMealsAdapter mealAdapter;
    AreaMealsPresenterInterface areaMealsPresenterInterface;
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_area_meals, container, false);
        mealOfAreaRecyclerView = view.findViewById(R.id.recyclerView_meals);
        area = view.findViewById(R.id.area_name);
        search = view.findViewById(R.id.et_search_meal);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String areaName = AreaMealsFragmentArgs.fromBundle(getArguments()).getAreaName();

        areaMealsPresenterInterface = new AreaMealsPresenter(this::showMeals, Repository.getInstance(requireContext()), areaName);
        progressBar.setVisibility(View.GONE);
        area.setText(areaName);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mealAdapter.setList(areaMealsPresenterInterface.filteringArea(charSequence, mealsItemResults));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void showMeals(List<MealsItem> randomMeal) {
        linearLayout = new GridLayoutManager(requireContext(), 2);
        mealAdapter = new AreaMealsAdapter(requireContext());
        mealsItemResults = randomMeal;
        mealAdapter.setList(randomMeal);
        mealOfAreaRecyclerView.setLayoutManager(linearLayout);
        mealOfAreaRecyclerView.setAdapter(mealAdapter);
    }
}