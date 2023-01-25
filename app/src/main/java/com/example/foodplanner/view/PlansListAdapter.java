package com.example.foodplanner.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.model.FavouriteMeal;
import com.example.foodplanner.model.PlanMeal;

import java.util.ArrayList;
import java.util.List;

public class PlansListAdapter extends RecyclerView.Adapter<PlansListAdapter.MealViewHolder> {

    private List<PlanMeal> planMealList = new ArrayList<>();

    @NonNull
    @Override
    public PlansListAdapter.MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.plan_meal_list_item, parent, false);
        MealViewHolder mealViewHolder = new MealViewHolder(view);
        return mealViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlansListAdapter.MealViewHolder holder, int position) {
        PlanMeal planMeal = planMealList.get(position);
        holder.textViewMealName.setText(planMeal.getMealName());
        Glide.with(holder.itemView).load(planMeal.getMealImage()).into(holder.imageViewMeal);
    }

    @Override
    public int getItemCount() {
        return planMealList.size();
    }

    public void setList(List<PlanMeal> planMealList) {
        this.planMealList = planMealList;
        notifyDataSetChanged();
    }

    public class MealViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewMeal;
        private TextView textViewMealName;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewMeal = itemView.findViewById(R.id.imageViewMealPlanImage);
            textViewMealName = itemView.findViewById(R.id.textViewMealPlanTitle);
        }
    }
}
