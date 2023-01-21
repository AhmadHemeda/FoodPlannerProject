package com.example.foodplanner.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;

import java.util.List;

public class SingleMealAdapter extends RecyclerView.Adapter<SingleMealAdapter.SingleMealViewHolder> {

    private List<String> modelArrayList;
    Context context;

    public SingleMealAdapter(Context context) {
        this.context = context;
    }
    public void setList(List<String> updatedItems){
        this.modelArrayList = updatedItems;
    }
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
        holder.textViewIngredient.setText(modelArrayList.get(position));
        holder.textViewMeasure.setText(modelArrayList.get(position));
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
