package com.example.foodplanner.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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

        if (mesurList.size() > position)
            holder.textViewMeasure.setText(mesurList.get(position));
        else
            holder.textViewMeasure.setText("");

        Glide.with(context).load(String.format("https://www.themealdb.com/images/ingredients/%s-Small.png", modelArrayList.get(position)))
                .placeholder(R.drawable.ic_launcher_foreground).into(holder.ingredientImage);
    }

    @Override
    public int getItemCount() {
        if (modelArrayList != null) {
            return modelArrayList.size();
        } else {
            return 2;
        }
    }

    public void setList(List<String> updatedItems, List<String> measureList) {
        this.modelArrayList = updatedItems;
        this.mesurList = measureList;
        notifyDataSetChanged();
    }

    public static class SingleMealViewHolder extends RecyclerView.ViewHolder {

        TextView textViewIngredient, textViewMeasure;
        CircleImageView ingredientImage;

        public SingleMealViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewIngredient = itemView.findViewById(R.id.textViewIngredient);
            textViewMeasure = itemView.findViewById(R.id.textViewMeasure);
            ingredientImage = itemView.findViewById(R.id.ingredient_image);

        }
    }
}