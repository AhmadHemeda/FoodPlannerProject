package com.example.foodplanner.view.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.R;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.model.pojos.area.AreaModel;
import com.example.foodplanner.presenter.areaSearch.AllAreasViewInterface;
import com.example.foodplanner.presenter.areaSearch.GetAllAreasPresenter;
import com.example.foodplanner.presenter.areaSearch.GetAllAreasPresenterInterface;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchByAreaFragment extends Fragment implements AllAreasViewInterface {

    RecyclerView areaRecyclerView;
    AreaAdapter areaAdapter;
    TextInputEditText search;
    List<AreaModel> areaModels = new ArrayList<>();
    List<AreaModel> areaModelsSearch = new ArrayList<>();
    View view;
    GetAllAreasPresenterInterface getAllAreasPresenterInterface;
    private static final String TAG = "SearchByAreaFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllAreasPresenterInterface = new GetAllAreasPresenter(this, Repository.getInstance(requireContext())) ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_by_area, container, false);
        areaRecyclerView = view.findViewById(R.id.recyclerViewAreas);
        search = view.findViewById(R.id.et_search_area);
//        handlingRecyclerView();
        getAllAreasPresenterInterface.getAllAreas();
//        Single<AreaListModel> singleObservable = ApiClient.getInstance().getAllAreas();
//        singleObservable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response ->{
//                            areaModels =response.getMeals();
//                            areaAdapter.setList(areaModels);
//                        },
//                        error ->{error.printStackTrace();
//                            Log.i(TAG, "onClick: "+ error.getMessage());
//                        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                areaAdapter.setList(getAllAreasPresenterInterface.filteringIngredients(charSequence,areaModelsSearch));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        return view;
    }


    @Override
    public void showMeals(List<AreaModel> areaModels) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        areaAdapter = new AreaAdapter(requireContext());
        areaModelsSearch = areaModels;
        areaAdapter.setList(areaModels);
        areaRecyclerView.setAdapter(areaAdapter);
        areaRecyclerView.setLayoutManager(linearLayoutManager);
    }
}