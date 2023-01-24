package com.example.foodplanner.view.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.R;
import com.example.foodplanner.model.pojos.area.IngredientListModel;
import com.example.foodplanner.model.pojos.area.IngredientModel;
import com.example.foodplanner.network.ApiClient;
import com.example.foodplanner.view.search.IngredientAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class SearchByIngredientFragment extends Fragment {
    RecyclerView ingredientRecyclerView;
    IngredientAdapter ingredientAdapter;
    TextInputEditText search;
    List<IngredientModel> ingredientModels = new ArrayList<>();
    List<IngredientModel> ingredientModelsSearch = new ArrayList<>();
    private static final String TAG = "SearchByIngredientFragm";
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_by_ingredient, container, false);
        ingredientRecyclerView = view.findViewById(R.id.recyclerViewIngredients);
        search = view.findViewById(R.id.et_search_ingredient);
        handlingRecyclerView();

        Single<IngredientListModel> singleObservable = ApiClient.getInstance().getAllIngredient();
        singleObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->{
                    ingredientModels = response.getMeals();
                    ingredientAdapter.setList(ingredientModels);

                },error ->{error.printStackTrace();
                    Log.i(TAG, "onClick: "+ error.getMessage());
                });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ingredientModelsSearch = ingredientModels.stream().filter((e->e.getStrIngredient().startsWith(s.toString()))).collect(Collectors.toList());

            }

            @Override
            public void afterTextChanged(Editable s) {
                ingredientAdapter.setList(ingredientModelsSearch);
            }
        });
        return view;
    }

    private void handlingRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        ingredientAdapter = new IngredientAdapter(requireContext());
        ingredientRecyclerView.setAdapter(ingredientAdapter);
        ingredientRecyclerView.setLayoutManager(linearLayoutManager);
    }

}