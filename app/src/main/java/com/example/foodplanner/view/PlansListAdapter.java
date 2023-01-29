package com.example.foodplanner.view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.database.MealDataBase;
import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.model.PlanMeal;
import com.example.foodplanner.model.RandomMeal;
import com.example.foodplanner.network.ApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlansListAdapter extends RecyclerView.Adapter<PlansListAdapter.MealViewHolder> {
    private List<PlanMeal> planMealList = new ArrayList<>();
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    Context context;
    ViewGroup frag;
    private static final String TAG = "PlansListAdapter";
    private MealDataBase mealDataBase;


    public PlansListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PlansListAdapter.MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        frag = parent;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.plan_meal_list_item, parent, false);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mealDataBase = MealDataBase.getInstance(parent.getContext());
        MealViewHolder mealViewHolder = new MealViewHolder(view);
        return mealViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlansListAdapter.MealViewHolder holder, int position) {
        PlanMeal planMeal = planMealList.get(position);

        holder.textViewMealName.setText(planMeal.getMealName());

        Glide.with(holder.itemView).load(planMeal.getMealImage()).into(holder.imageViewMeal);
        holder.appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePlanItem(planMeal,position);
            }
        });
        holder.appCompatButtonCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendarEvent = Calendar.getInstance();
                Intent i = new Intent(Intent.ACTION_EDIT);
                i.setType("vnd.android.cursor.item/event");
                i.putExtra("beginTime", calendarEvent.getTimeInMillis());
                i.putExtra("allDay", false);
                i.putExtra("rule", "FREQ=YEARLY");
                i.putExtra("endTime", calendarEvent.getTimeInMillis() + 60 * 60 * 1000);
                i.putExtra("title", planMeal.getMealName());
                context.startActivity(i);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Single<RandomMeal> singleObservable = ApiClient.getInstance(view.getContext()).getMealByName(holder.textViewMealName.getText().toString());
                singleObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( response ->{
                                    List<MealsItem> singleMeal =response.getMeals();
                                    Navigation.findNavController(frag).navigate(PlansFragmentDirections.actionPlansFragmentToMealFragment(singleMeal.get(0)).setSingleMealItem(singleMeal.get(0)));
                                    Log.i(TAG, "onClick: "+ singleMeal.get(0).getStrMeal());
                                },
                                error ->{error.printStackTrace();
                                    Log.i(TAG, "onClick: "+ error.getMessage());
                                }
                        );
            }
        });
    }

    @Override
    public int getItemCount() {
        return planMealList.size();
    }

    public void setList(List<PlanMeal> planMealList) {
        this.planMealList = planMealList;
        notifyDataSetChanged();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewMeal;
        TextView textViewMealName;
        AppCompatButton appCompatButton;
        AppCompatButton appCompatButtonCalender;
        public MealViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewMeal = itemView.findViewById(R.id.imageViewMealPlanImage);
            textViewMealName = itemView.findViewById(R.id.textViewMealPlanTitle);
            appCompatButton = itemView.findViewById(R.id.buttonRemoveFromPlan);
            appCompatButtonCalender = itemView.findViewById(R.id.buttonAddToCalenderPlan);

        }
    }

    private void deletePlanItem(PlanMeal planMeal, int position) {
        mealDataBase.planMealDao().deletePlanMeal(planMeal)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        if (auth.getCurrentUser() != null)
                            db.collection(MealDataBase.FIRESTORE)
                                    .document(auth.getCurrentUser().getEmail())
                                    .collection(MealDataBase.PLAN)
                                    .document(planMeal.getMealID() + "_" + planMeal.getMealDay())
                                    .delete();
                        planMealList.remove(planMeal);
                        notifyItemRemoved(position);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }
}
