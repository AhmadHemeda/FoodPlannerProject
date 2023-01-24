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
import com.example.foodplanner.model.pojos.area.AreaListModel;
import com.example.foodplanner.model.pojos.area.AreaModel;
import com.example.foodplanner.network.ApiClient;
import com.example.foodplanner.view.search.AreaAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchByAreaFragment extends Fragment {

    RecyclerView areaRecyclerView;
    AreaAdapter areaAdapter;
    TextInputEditText search;
    List<AreaModel> areaModels = new ArrayList<>();
    List<AreaModel> areaModelsSearch = new ArrayList<>();
    View view;
    private static final String TAG = "SearchByAreaFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_by_area, container, false);
        areaRecyclerView = view.findViewById(R.id.recyclerViewAreas);
        search = view.findViewById(R.id.et_search_area);
        handlingRecyclerView();

        Single<AreaListModel> singleObservable = ApiClient.getInstance().getAllAreas();
        singleObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->{
                            areaModels =response.getMeals();
                            areaAdapter.setList(areaModels);
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
                areaModelsSearch = areaModels.stream().filter((e->e.getStrArea().startsWith(charSequence.toString()))).collect(Collectors.toList());

            }

            @Override
            public void afterTextChanged(Editable editable) {
                areaAdapter.setList(areaModelsSearch);
            }
        });
        return view;
    }

    private void handlingRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        areaAdapter = new AreaAdapter(requireContext());
        areaRecyclerView.setAdapter(areaAdapter);
        areaRecyclerView.setLayoutManager(linearLayoutManager);
    }

}