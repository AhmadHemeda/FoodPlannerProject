package com.example.foodplanner.view.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.R;
import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.model.RandomMeal;
import com.example.foodplanner.network.ApiClient;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MealsSearchFragment extends Fragment {
    View view;
    String s;
    RecyclerView mealsRecyclerView;
    GridLayoutManager linearLayout;
    List<MealsItem> meals = new ArrayList<>();
    TextInputEditText search;
    List<MealsItem> mealsItemResults = new ArrayList<>();
    List<MealsItem> getMeals = new ArrayList<>();
    MealsSearchAdapter mealAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_meals_search, container, false);
        search = view.findViewById(R.id.et_search_meal_tx);
        mealsRecyclerView = view.findViewById(R.id.recyclerView_meals_rv);
        linearLayout = new GridLayoutManager(requireContext(),2);
        mealAdapter = new MealsSearchAdapter(requireContext());

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() > 0 )
                    s = String.valueOf(charSequence.charAt(0));
                Observable <RandomMeal> observable = ApiClient.getInstance(requireContext()).getRootMealsBySingleLetter(s);
                observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response ->{
                                    if (response.getMeals() != null){
                                        meals = response.getMeals();
                                        mealsItemResults = meals;
                                        Observable<MealsItem> observable1 = Observable.fromIterable(meals);
                                        observable1
                                                .filter(
                                                        mealsItem -> mealsItem.getStrMeal().toLowerCase().contains(search.getText().toString().toLowerCase())
                                                ).subscribe(
                                                        mealsItem -> {
                                                            getMeals.add(mealsItem);
                                                        }
                                                );
                                    }
                                },
                                error->{
                                    return;
                                }
                        );


            }

            @Override
            public void afterTextChanged(Editable s) {
                mealAdapter.setList(mealsItemResults);
                mealsRecyclerView.setLayoutManager(linearLayout);
                mealsRecyclerView.setAdapter(mealAdapter);
            }
        });
        return view;
    }

}