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
import com.example.foodplanner.network.ApiClient;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class IngredientsMealsFragment extends Fragment {

    RecyclerView mealOfIngredientRecyclerView;
    GridLayoutManager linearLayout;
    TextView ingredient;
    List<MealsItem> mealsItemResults = new ArrayList<>();
    TextInputEditText search;
    IndredientMealsAdapter mealAdapter;
    View view;
    private static final String TAG = "IngredientsMealsFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ingredients_meals, container, false);
        mealOfIngredientRecyclerView = view.findViewById(R.id.recyclerView_meals_ingredient);
        ingredient = view.findViewById(R.id.ingredient_name);
        search = view.findViewById(R.id.et_search_meal_ingredient);

        String ingredientName = IngredientsMealsFragmentArgs.fromBundle(getArguments()).getIngredientName();
        Log.i(TAG, "onCreateView: "+ingredientName);
        ingredient.setText(ingredientName);
        handlingRecyclerView();


        Single<RandomMeal> singleObservable = ApiClient.getInstance().getMealIngredient(ingredientName);
        singleObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->{
                            mealsItemResults = response.getMeals();
                            mealAdapter.setList(mealsItemResults);
                            Log.i(TAG, "onCreateView: "+mealsItemResults.get(0).getStrMeal());
                        },
                        error ->{error.printStackTrace();
                            Log.i(TAG, "onClick: "+ error.getMessage());
                        });


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mealAdapter.setList(
                        mealsItemResults.stream()
                                .filter(mealsItem->mealsItem.getStrMeal().startsWith(s.toString())).collect(Collectors.toList()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }
    private void handlingRecyclerView() {
        linearLayout = new GridLayoutManager(requireContext(),2);
        mealAdapter = new IndredientMealsAdapter(requireContext());
        mealOfIngredientRecyclerView.setLayoutManager(linearLayout);
        mealOfIngredientRecyclerView.setAdapter(mealAdapter);
    }
}