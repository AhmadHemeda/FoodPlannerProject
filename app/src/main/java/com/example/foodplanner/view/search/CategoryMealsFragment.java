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
import com.example.foodplanner.presenter.categorySearch.CategoryMealsPresenter;
import com.example.foodplanner.presenter.categorySearch.CategoryMealsPresenterInterface;
import com.example.foodplanner.presenter.categorySearch.CategoryMealsViewInterface;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


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
//        handlingRecyclerView();
        String categoryName = CategoryMealsFragmentArgs.fromBundle(getArguments()).getCategoryName();
        Log.i(TAG, "onCreateView: "+ categoryName);
        categoryMealsPresenterInterface = new CategoryMealsPresenter(this::showMeals, Repository.getInstance(requireContext()),categoryName);
        categoryMealsPresenterInterface.getCategoryMeal(categoryName);
        category.setText(categoryName);
//        Single<RandomMeal> singleObservable = ApiClient.getInstance().getMealCategory(categoryName);
//        singleObservable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response ->{
//                            mealsItemResults = response.getMeals();
//                            mealAdapter.setList(mealsItemResults);
//                        },
//                        error ->{error.printStackTrace();
//                            Log.i(TAG, "onClick: "+ error.getMessage());
//                        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mealAdapter.setList(categoryMealsPresenterInterface.filteringIngredients(s,mealsItemResults));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }
    private void handlingRecyclerView() {
        linearLayout = new GridLayoutManager(requireContext(),2);
        mealAdapter = new CategoryMealsAdapter(requireContext());
        mealOfAreaRecyclerView.setLayoutManager(linearLayout);
        mealOfAreaRecyclerView.setAdapter(mealAdapter);
    }

    @Override
    public void showMeals(List<MealsItem> randomMeal) {
        linearLayout = new GridLayoutManager(requireContext(),2);
        mealAdapter = new CategoryMealsAdapter(requireContext());
        mealsItemResults = randomMeal;
        mealAdapter.setList(randomMeal);
        mealOfAreaRecyclerView.setLayoutManager(linearLayout);
        mealOfAreaRecyclerView.setAdapter(mealAdapter);
    }
}