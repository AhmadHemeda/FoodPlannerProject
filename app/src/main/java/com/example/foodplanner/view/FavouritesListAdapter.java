package com.example.foodplanner.view;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.List;

public class FavouritesListAdapter extends RecyclerView.Adapter<FavouritesListAdapter.MealViewHolder> {

    private List<FavouriteMeal> favouriteMealList = new ArrayList<>();
//
//    public FavouritesListAdapter(List<FavouriteMeal> favouriteMealList) {
//        this.favouriteMealList = favouriteMealList;
//    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.favourites_list_item, parent, false);
        MealViewHolder mealViewHolder = new MealViewHolder(view);
        return mealViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        FavouriteMeal favouriteMeal = favouriteMealList.get(position);
        holder.textViewMealName.setText(favouriteMeal.getMealName());
        Glide.with(holder.itemView).load(favouriteMeal.getMealImage()).into(holder.imageViewMeal);
    }

    @Override
    public int getItemCount() {
        return favouriteMealList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<FavouriteMeal> favouriteMealList) {
        this.favouriteMealList = favouriteMealList;
        notifyDataSetChanged();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewMeal;
        TextView textViewMealName;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewMeal = itemView.findViewById(R.id.imageViewFavMealThumb);
            textViewMealName = itemView.findViewById(R.id.textViewFavMealName);
        }
    }
}
