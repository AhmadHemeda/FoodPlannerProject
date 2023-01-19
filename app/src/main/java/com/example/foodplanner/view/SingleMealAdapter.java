package com.example.foodplanner.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;

public class SingleMealAdapter extends RecyclerView.Adapter<SingleMealAdapter.SingleMealViewHolder> {

    // TODO Array list from the pojo

    @NonNull
    @Override
    public SingleMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ingredient_item, parent, false);
        SingleMealViewHolder singleMealViewHolder = new SingleMealViewHolder(view);
        return singleMealViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SingleMealViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class SingleMealViewHolder extends RecyclerView.ViewHolder {

        TextView textViewIngredient, textViewMeasure;

        public SingleMealViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewIngredient = itemView.findViewById(R.id.textViewIngredient);
            textViewMeasure = itemView.findViewById(R.id.textViewMeasure);
        }
    }
}
