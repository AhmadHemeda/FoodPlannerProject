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
import com.example.foodplanner.model.pojos.area.CategoryModel;
import com.example.foodplanner.presenter.categorySearch.AllCategoriesViewInterface;
import com.example.foodplanner.presenter.categorySearch.GetAllCategoriesPresenter;
import com.example.foodplanner.presenter.categorySearch.GetAllCategoriesPresenterInterface;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class SearchByCategoryFragment extends Fragment implements AllCategoriesViewInterface {
    RecyclerView categoryRecyclerView;
    CategoryAdapter categoryAdapter;
    TextInputEditText search;
    List<CategoryModel> categoryModels = new ArrayList<>();
    List<CategoryModel> categoryModelsSearch = new ArrayList<>();
    GetAllCategoriesPresenterInterface getAllCategoriesPresenterInterface;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllCategoriesPresenterInterface = new GetAllCategoriesPresenter(this::showCategories, Repository.getInstance(requireContext()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_by_category, container, false);
        categoryRecyclerView = view.findViewById(R.id.recyclerViewCategory);
        search = view.findViewById(R.id.et_search_category);

        getAllCategoriesPresenterInterface.getAllCategories();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                categoryAdapter.setList(getAllCategoriesPresenterInterface.filteringCategories(s, categoryModelsSearch));

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }

    @Override
    public void showCategories(List<CategoryModel> categoryModels) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        categoryAdapter = new CategoryAdapter(requireContext());
        categoryModelsSearch = categoryModels;
        categoryAdapter.setList(categoryModels);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);
    }
}