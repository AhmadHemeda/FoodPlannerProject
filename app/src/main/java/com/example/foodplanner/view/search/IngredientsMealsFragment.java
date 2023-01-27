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
import com.example.foodplanner.presenter.ingredientSearch.IngredientMealsPresenter;
import com.example.foodplanner.presenter.ingredientSearch.IngredientMealsPresenterInterface;
import com.example.foodplanner.presenter.ingredientSearch.IngredientMealsViewInterface;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class IngredientsMealsFragment extends Fragment implements IngredientMealsViewInterface {

    RecyclerView mealOfIngredientRecyclerView;
    GridLayoutManager linearLayout;
    TextView ingredient;
    List<MealsItem> mealsItemResults = new ArrayList<>();
    TextInputEditText search;
    IndredientMealsAdapter mealAdapter;
    IngredientMealsPresenterInterface ingredientMealsPresenterInterface;
    View view;
    private static final String TAG = "IngredientsMealsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ingredients_meals, container, false);
        mealOfIngredientRecyclerView = view.findViewById(R.id.recyclerView_meals_ingredient);
        ingredient = view.findViewById(R.id.ingredient_name);
        search = view.findViewById(R.id.et_search_meal_ingredient);

        String ingredientName = IngredientsMealsFragmentArgs.fromBundle(getArguments()).getIngredientName();
        ingredient.setText(ingredientName);

        ingredientMealsPresenterInterface = new IngredientMealsPresenter(this::showMeals, Repository.getInstance(requireContext()),ingredientName);
        ingredientMealsPresenterInterface.getIngredientMeal(ingredientName);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mealAdapter.setList(ingredientMealsPresenterInterface.filteringIngredients(s,mealsItemResults));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @Override
    public void showMeals(List<MealsItem> randomMeal) {
        linearLayout = new GridLayoutManager(requireContext(),2);
        mealAdapter = new IndredientMealsAdapter(requireContext());
        mealsItemResults = randomMeal;
        mealAdapter.setList(randomMeal);
        mealOfIngredientRecyclerView.setLayoutManager(linearLayout);
        mealOfIngredientRecyclerView.setAdapter(mealAdapter);
    }
}