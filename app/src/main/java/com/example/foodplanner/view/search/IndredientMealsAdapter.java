package com.example.foodplanner.view.search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.model.RandomMeal;
import com.example.foodplanner.network.ApiClient;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class IndredientMealsAdapter extends RecyclerView.Adapter<IndredientMealsAdapter.IndredientMealViewHolder> {
    private List<MealsItem> modelArrayList;
    Context context;
    ViewGroup frag;
    private static final String TAG = "IngredientMealsAdapter";

    public IndredientMealsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public IndredientMealsAdapter.IndredientMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        frag = parent;
        return new IndredientMealsAdapter.IndredientMealViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IndredientMealsAdapter.IndredientMealViewHolder holder, int position) {
        if (modelArrayList != null) {
            if (!modelArrayList.get(position).getStrMealThumb().isEmpty()) {
                Glide.with(context).load(modelArrayList.get(position).getStrMealThumb())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(holder.imageViewMeal);
            }
            holder.textViewMealName.setText(modelArrayList.get(position).getStrMeal());

            holder.itemView.setOnClickListener(view -> {
                Single<RandomMeal> singleObservable = ApiClient.getInstance(context).getMealByName(holder.textViewMealName.getText().toString());
                singleObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                                    List<MealsItem> singleMeal = response.getMeals();
                                    Navigation.findNavController(frag).navigate(IngredientsMealsFragmentDirections.actionIngredientsMealsFragmentToMealFragment(singleMeal.get(0)).setSingleMealItem(singleMeal.get(0)));
                                    Log.i(TAG, "onClick: " + singleMeal.get(0).getStrMeal());
                                },
                                error -> {
                                    error.printStackTrace();
                                    Log.i(TAG, "onClick: " + error.getMessage());
                                }
                        );
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

    public class IndredientMealViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewMeal;
        TextView textViewMealName;

        public IndredientMealViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewMeal = itemView.findViewById(R.id.category_image);
            textViewMealName = itemView.findViewById(R.id.category_text);
        }
    }
}
