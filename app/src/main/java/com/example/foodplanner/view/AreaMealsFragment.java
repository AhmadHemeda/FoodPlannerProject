package com.example.foodplanner.view;

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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class AreaMealsFragment extends Fragment {

    RecyclerView mealOfAreaRecyclerView;
    GridLayoutManager linearLayout;
    TextView area;
    List<MealsItem> mealsItemResults = new ArrayList<>();
    TextInputEditText search;
    AreaMealsAdapter mealAdapter;
    View view;
    private static final String TAG = "AreaMealsFragment";

    public AreaMealsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_area_meals, container, false);
        mealOfAreaRecyclerView = view.findViewById(R.id.recyclerView_meals);
        area = view.findViewById(R.id.area_name);
        search = view.findViewById(R.id.et_search_meal);
        handlingRecyclerView();
        String areaName = AreaMealsFragmentArgs.fromBundle(getArguments()).getAreaName();
        Log.i(TAG, "onCreateView: "+ areaName);
        area.setText(areaName);
        Single<RandomMeal> singleObservable = ApiClient.getInstance().getMealArea(areaName);
                singleObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response ->{
                                    mealsItemResults = response.getMeals();
                                    mealAdapter.setList(mealsItemResults);
                                },
                                error ->{error.printStackTrace();
                                    Log.i(TAG, "onClick: "+ error.getMessage());
                                });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mealAdapter.setList(
                        mealsItemResults.stream()
                                .filter(mealsItem->mealsItem.getStrMeal().startsWith(charSequence.toString())).collect(Collectors.toList()));
                }


            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        return view;
    }

    private void handlingRecyclerView() {
        linearLayout = new GridLayoutManager(requireContext(),2);
        mealAdapter = new AreaMealsAdapter(requireContext());
        mealOfAreaRecyclerView.setLayoutManager(linearLayout);
        mealOfAreaRecyclerView.setAdapter(mealAdapter);
    }
}