package com.example.foodplanner.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.database.MealDataBase;
import com.example.foodplanner.model.PlanMeal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlansListAdapter extends RecyclerView.Adapter<PlansListAdapter.MealViewHolder> {
    private List<PlanMeal> planMealList = new ArrayList<>();
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private MealDataBase mealDataBase;
    @NonNull
    @Override
    public PlansListAdapter.MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewMeal = itemView.findViewById(R.id.imageViewMealPlanImage);
            textViewMealName = itemView.findViewById(R.id.textViewMealPlanTitle);
            appCompatButton = itemView.findViewById(R.id.buttonRemoveFromPlan);
        }
    }

    private void deletePlanItem(PlanMeal planMeal,int position) {
        mealDataBase.planMealDao().deletePlanMeal(planMeal)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        if (auth.getCurrentUser() !=null)
                            db.collection(MealDataBase.FIRESTORE)
                                    .document(auth.getCurrentUser().getEmail())
                                    .collection(MealDataBase.PLAN)
                                    .document(planMeal.getMealID()+"_"+planMeal.getMealDay())
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
