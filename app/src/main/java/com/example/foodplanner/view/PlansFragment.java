package com.example.foodplanner.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.foodplanner.R;
import com.example.foodplanner.database.MealDataBase;
import com.example.foodplanner.model.PlanMeal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_plans, container, false);
        mealDataBase = MealDataBase.getInstance(getContext());

        recyclerViewPlanMeal = view.findViewById(R.id.recyclerViewDaysPlans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewPlanMeal.setLayoutManager(linearLayoutManager);
        recyclerViewPlanMeal.setHasFixedSize(true);
        plansListAdapter = new PlansListAdapter();
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

//    private void deleteToRoomAndFirestorePlan(PlanMeal planMeal) {
//        mealDataBase.planMealDao().deletePlanMeal(planMeal)
//                .subscribeOn(Schedulers.io())
//                .subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        if (auth.getCurrentUser() !=null)
//                            db.collection(MealDataBase.FIRESTORE)
//                                    .document(auth.getCurrentUser().getEmail())
//                                    .collection(MealDataBase.PLAN)
//                                    .document(planMeal.getMealID()+"")
//                                    .delete();
//                    }
//
//                    @Override
//                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//
//                    }
//                });
//    }
}