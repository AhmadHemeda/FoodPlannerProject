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
import com.example.foodplanner.R;
import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.presenter.SingleMeal.GetMealPresenter;
import com.example.foodplanner.presenter.SingleMeal.GetMealViewInterface;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private List<MealsItem> modelArrayList;
    Context context;
    ViewGroup frag;
    GetMealPresenter presenter;

    public MealAdapter(Context context, GetMealViewInterface getMealViewInterface) {
        presenter = new GetMealPresenter(getMealViewInterface, context);
        this.context = context;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        frag = parent;
        return new MealViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MealAdapter.MealViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (modelArrayList != null) {
            if (!modelArrayList.get(position).getStrMealThumb().isEmpty()) {
                Glide.with(context).load(modelArrayList.get(position).getStrMealThumb())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(holder.imageViewMeal);
            }

            holder.textViewMealName.setText(modelArrayList.get(position).getStrMeal());

            holder.itemView.setOnClickListener(view -> {
                String mealName = holder.textViewMealName.getText().toString();
                presenter.getSingleMeal(mealName);
            });
        }
    }

    @Override
    public int getItemCount() {
        if (modelArrayList != null) {
            return modelArrayList.size();
        } else {
            return 2;
        }
    }

    public void setList(List<MealsItem> updatedItems) {
        this.modelArrayList = updatedItems;
        notifyDataSetChanged();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewMeal;
        TextView textViewMealName;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewMeal = itemView.findViewById(R.id.category_image);
            textViewMealName = itemView.findViewById(R.id.category_text);
        }
    }
}
