package com.example.foodplanner.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.database.MealDataBase;
import com.example.foodplanner.model.PlanMeal;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlansFragment extends Fragment {
    private View view;
    private MealDataBase mealDataBase;
    private PlansListAdapter plansListAdapter;
    private RecyclerView recyclerViewPlanMeal;
    private final String[] days = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> adapterDays;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plans, container, false);
        mealDataBase = MealDataBase.getInstance(getContext());

        recyclerViewPlanMeal = view.findViewById(R.id.recyclerViewDaysPlans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewPlanMeal.setLayoutManager(linearLayoutManager);
        recyclerViewPlanMeal.setHasFixedSize(true);
        plansListAdapter = new PlansListAdapter(requireContext());
        recyclerViewPlanMeal.setAdapter(plansListAdapter);

        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView);

        adapterDays = new ArrayAdapter<>(getContext(), R.layout.dropdown_menu_list_item, days);
        autoCompleteTextView.setAdapter(adapterDays);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        autoCompleteTextView.setOnItemClickListener((parent, view1, position, id) -> {
            String day = parent.getItemAtPosition(position).toString();

            mealDataBase.planMealDao().getPlanMeals(day)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<List<PlanMeal>>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<PlanMeal> planMeals) {
                            plansListAdapter.setList(planMeals);
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }
                    });
        });
    }
}