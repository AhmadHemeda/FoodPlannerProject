package com.example.foodplanner.view.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.model.pojos.area.IngredientModel;
import com.example.foodplanner.presenter.ingredientSearch.AllIngredientsViewInterface;
import com.example.foodplanner.presenter.ingredientSearch.GetAllIngredientsPresenter;
import com.example.foodplanner.presenter.ingredientSearch.GetAllIngredientsPresenterInterface;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class SearchByIngredientFragment extends Fragment implements AllIngredientsViewInterface {
    RecyclerView ingredientRecyclerView;
    IngredientAdapter ingredientAdapter;
    TextInputEditText search;
    List<IngredientModel> ingredientModelsSearch = new ArrayList<>();
    GetAllIngredientsPresenterInterface getAllIngredientsPresenterInterface;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllIngredientsPresenterInterface = new GetAllIngredientsPresenter(this::showIngredients, Repository.getInstance(requireContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_by_ingredient, container, false);
        ingredientRecyclerView = view.findViewById(R.id.recyclerViewIngredients);
        search = view.findViewById(R.id.et_search_ingredient);

        getAllIngredientsPresenterInterface.getAllIngredients();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ingredientAdapter.setList(getAllIngredientsPresenterInterface.filteringIngredients(s, ingredientModelsSearch));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return view;
    }

    @Override
    public void showIngredients(List<IngredientModel> ingredientModels) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        ingredientAdapter = new IngredientAdapter(requireContext());
        ingredientModelsSearch = ingredientModels;
        ingredientAdapter.setList(ingredientModels);
        ingredientRecyclerView.setAdapter(ingredientAdapter);
        ingredientRecyclerView.setLayoutManager(linearLayoutManager);
    }
}