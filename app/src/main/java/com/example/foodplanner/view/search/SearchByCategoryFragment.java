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
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.model.pojos.area.CategoryListModel;
import com.example.foodplanner.model.pojos.area.CategoryModel;
import com.example.foodplanner.model.pojos.area.IngredientListModel;
import com.example.foodplanner.model.pojos.area.IngredientModel;
import com.example.foodplanner.network.ApiClient;
import com.example.foodplanner.presenter.categorySearch.AllCategoriesViewInterface;
import com.example.foodplanner.presenter.categorySearch.GetAllCategoriesPresenter;
import com.example.foodplanner.presenter.categorySearch.GetAllCategoriesPresenterInterface;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchByCategoryFragment extends Fragment implements AllCategoriesViewInterface {
    RecyclerView categoryRecyclerView;
    CategoryAdapter categoryAdapter;
    TextInputEditText search;
    List<CategoryModel> categoryModels = new ArrayList<>();
    List<CategoryModel> categoryModelsSearch = new ArrayList<>();
    GetAllCategoriesPresenterInterface getAllCategoriesPresenterInterface;
    private static final String TAG = "SearchByCategoryFragment";
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllCategoriesPresenterInterface = new GetAllCategoriesPresenter(this::showCategories, Repository.getInstance(requireContext()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_search_by_category, container, false);
        categoryRecyclerView = view.findViewById(R.id.recyclerViewCategory);
        search = view.findViewById(R.id.et_search_category);
//        handlingRecyclerView();
        getAllCategoriesPresenterInterface.getAllCategories();
//        Single<CategoryListModel> singleObservable = ApiClient.getInstance().getAllCategories();
//        singleObservable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response ->{
//                    categoryModels = response.getMeals();
//                    categoryAdapter.setList(categoryModels);
//                    Log.i(TAG, "onCreateView: "+categoryModels.get(0).getStrCategory());
//                    Log.i(TAG, "onCreateView: "+categoryModels.get(1).getStrCategory());
//
//                },error ->{error.printStackTrace();
//                    Log.i(TAG, "onClick: "+ error.getMessage());
//                });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                categoryAdapter.setList(getAllCategoriesPresenterInterface.filteringCategories(s,categoryModelsSearch));

            }

            @Override
            public void afterTextChanged(Editable s) {
//                categoryAdapter.setList(categoryModelsSearch);
            }
        });

        return view;
    }
    private void handlingRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        categoryAdapter = new CategoryAdapter(requireContext());
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);
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