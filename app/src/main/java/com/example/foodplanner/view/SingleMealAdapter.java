package com.example.foodplanner.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.model.MealsItem;

import java.util.ArrayList;
import java.util.List;

public class SingleMealAdapter extends RecyclerView.Adapter<SingleMealAdapter.SingleMealViewHolder> {

    private List<String> modelArrayList = new ArrayList<>();
    private List<String> mesurList = new ArrayList<>();
    Context context;

    public SingleMealAdapter(Context context) {
        this.context = context;
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
        holder.textViewMeasure.setText(mesurList.get(position));
    }

    @Override
    public int getItemCount() {
        if (modelArrayList != null){
            return modelArrayList.size();
        }else {
            return 2;
        }
    }
    public void setList(List<String> updatedItems , List<String> measureList){
        this.modelArrayList = updatedItems;
        this.mesurList = measureList;
        notifyDataSetChanged();
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
