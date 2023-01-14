package com.example.foodplanner.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;

public class FavouritesListAdapter extends RecyclerView.Adapter<FavouritesListAdapter.MealViewHolder> {

    // TODO Array list from the pojo

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
        // TODO bind the view holder by new data
    }

    @Override
    public int getItemCount() {
        return 0;
        // TODO return the length of the array list
    }

    // TODO setList method

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewMeal;
        TextView textViewMealName, textViewMealDescription;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewMeal = itemView.findViewById(R.id.iv_saturday_meal);
            textViewMealName = itemView.findViewById(R.id.tv_saturday_meal_name);
            textViewMealDescription = itemView.findViewById(R.id.tv_saturday_description);
        }
    }
}
