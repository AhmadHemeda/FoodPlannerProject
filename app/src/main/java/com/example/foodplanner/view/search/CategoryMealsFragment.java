package com.example.foodplanner.view.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.presenter.categorySearch.CategoryMealsPresenter;
import com.example.foodplanner.presenter.categorySearch.CategoryMealsPresenterInterface;
import com.example.foodplanner.presenter.categorySearch.CategoryMealsViewInterface;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class CategoryMealsFragment extends Fragment implements CategoryMealsViewInterface {

    RecyclerView mealOfAreaRecyclerView;
    GridLayoutManager linearLayout;
    TextView category;
    List<MealsItem> mealsItemResults = new ArrayList<>();
    TextInputEditText search;
    CategoryMealsAdapter mealAdapter;
    CategoryMealsPresenterInterface categoryMealsPresenterInterface;
    View view;
    private static final String TAG = "CategoryMealsFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category_meals, container, false);

        mealOfAreaRecyclerView = view.findViewById(R.id.recyclerView_meals_category);
        category = view.findViewById(R.id.category_name);
        search = view.findViewById(R.id.et_search_meal_category);

        String categoryName = CategoryMealsFragmentArgs.fromBundle(getArguments()).getCategoryName();
        Log.i(TAG, "onCreateView: " + categoryName);

        categoryMealsPresenterInterface = new CategoryMealsPresenter(this::showMeals, Repository.getInstance(requireContext()), categoryName);
        categoryMealsPresenterInterface.getCategoryMeal(categoryName);
        category.setText(categoryName);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mealAdapter.setList(categoryMealsPresenterInterface.filteringIngredients(s, mealsItemResults));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @Override
    public void showMeals(List<MealsItem> randomMeal) {
        linearLayout = new GridLayoutManager(requireContext(), 2);
        mealAdapter = new CategoryMealsAdapter(requireContext());
        mealsItemResults = randomMeal;
        mealAdapter.setList(randomMeal);
        mealOfAreaRecyclerView.setLayoutManager(linearLayout);
        mealOfAreaRecyclerView.setAdapter(mealAdapter);
    }
}