package com.example.foodplanner.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.R;
import com.example.foodplanner.model.MealsItem;

import java.util.List;

public class PlansAdapter extends RecyclerView.Adapter<PlansAdapter.PlansViewHolder> {
    private List<MealsItem> mealsItemList;
    private Context context;
    private ViewGroup viewGroup;
    private OnMealItemClickListener onMealItemClickListener;

    public PlansAdapter(Context context, OnMealItemClickListener onMealItemClickListener) {
        this.context = context;
        this.onMealItemClickListener = onMealItemClickListener;
    }

    @NonNull
    @Override
    public PlansAdapter.PlansViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewGroup = parent;
        return new PlansViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_meal_list_item, parent,false));
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull PlansAdapter.PlansViewHolder holder, int position) {

        if (mealsItemList != null) {
            if(!mealsItemList.get(position).getStrMealThumb().isEmpty()){
                Glide.with(context).load(mealsItemList.get(position).getStrMealThumb())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .apply(new RequestOptions().override(100,100)).into(holder.imageViewPlanMeal);
            }
        }

        holder.textViewPlanMeal.setText(mealsItemList.get(position).getStrMeal());

        holder.itemView.setOnClickListener(view -> onMealItemClickListener.onMealClick(mealsItemList.get(position)));
    }

    @Override
    public int getItemCount() {
        return mealsItemList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<MealsItem> mealsItemList) {
        this.mealsItemList = mealsItemList;
        notifyDataSetChanged();
    }

    public static class PlansViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewPlanMeal;
        TextView textViewPlanMeal;

        public PlansViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewPlanMeal = itemView.findViewById(R.id.imageViewMealPlanImage);
            textViewPlanMeal = itemView.findViewById(R.id.textViewMealPlanTitle);
        }
    }
}
