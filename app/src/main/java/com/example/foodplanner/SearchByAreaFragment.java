package com.example.foodplanner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.model.pojos.area.AreaListModel;
import com.example.foodplanner.model.pojos.area.AreaModel;
import com.example.foodplanner.network.ApiClient;
import com.example.foodplanner.view.AreaAdapter;
import com.example.foodplanner.view.HomeFragmentDirections;
import com.example.foodplanner.view.SearchAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchByAreaFragment extends Fragment {

    RecyclerView areaRecyclerView;
    LinearLayoutManager linearLayoutManager;
    AreaAdapter areaAdapter;
    SearchAdapter searchAdapter;
    TextInputEditText search;
    List<AreaModel> areaModels;
    List <String> names = new ArrayList<>();
    List res;
    View view;
    private static final String TAG = "SearchByAreaFragment";

    public SearchByAreaFragment() {
        // Required empty public constructor
    }


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
        linearLayoutManager = new LinearLayoutManager(requireContext());
        areaAdapter = new AreaAdapter(requireContext());
        areaRecyclerView.setLayoutManager(linearLayoutManager);
        Single<AreaListModel> singleObservable = ApiClient.getInstance().getAllAreas();
        singleObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->{
                            areaModels =response.getMeals();
//                            names.add(response.getMeals().toString());
//                            Log.i(TAG, "onCreateView: "+names.get(0));
                            areaAdapter.setList(areaModels);
                            areaRecyclerView.setAdapter(areaAdapter);
                            for (int i = 0; i< areaModels.size();i++){
                                names.add(areaModels.get(i).getStrArea());
                                Log.i(TAG, "onCreateView: "+areaModels.get(i).getStrArea());
                            }
//                            Log.i(TAG, "onClick: "+ areaModels.get(0).getStrArea());
                            Log.i(TAG, "onCreateView: "+names.toString());

                        },
                        error ->{error.printStackTrace();
                            Log.i(TAG, "onClick: "+ error.getMessage());
                        });
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<Object> emitter) throws Throwable {
                search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        res = names.stream().filter((e->e.startsWith(charSequence.toString()))).collect(Collectors.toList());
                        Log.i(TAG, "onTextChanged: "+names.toString());
                        Log.i(TAG, "onTextChanged: "+res.toString());

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
//                        areaAdapter.setList(res);
                        searchAdapter = new SearchAdapter(requireContext(),res);
                        areaRecyclerView.setAdapter(searchAdapter);
                    }
                });
            }
        }).subscribe(e->Log.i(TAG,e.toString()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}