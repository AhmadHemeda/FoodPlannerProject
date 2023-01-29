package com.example.foodplanner.view;

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
import com.example.foodplanner.model.FavouriteMeal;
import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.model.RandomMeal;
import com.example.foodplanner.network.ApiClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouritesListAdapter extends RecyclerView.Adapter<FavouritesListAdapter.MealViewHolder> {

    private List<FavouriteMeal> favouriteMealList = new ArrayList<>();
    ViewGroup frag;
    private static final String TAG = "FavouritesListAdapter";

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        frag = parent;
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

        holder.itemView.setOnClickListener(view -> {
            Single<RandomMeal> singleObservable = ApiClient.getInstance(view.getContext()).getMealByName(holder.textViewMealName.getText().toString());
            singleObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                                List<MealsItem> singleMeal = response.getMeals();
                                Navigation.findNavController(frag).navigate(FavouritesFragmentDirections.actionFavouritesFragmentToMealFragment(singleMeal.get(0)).setSingleMealItem(singleMeal.get(0)));
                                Log.i(TAG, "onClick: " + singleMeal.get(0).getStrMeal());
                            },
                            error -> {
                                error.printStackTrace();
                                Log.i(TAG, "onClick: " + error.getMessage());
                            }
                    );
        });
    }

    @Override
    public int getItemCount() {
        return favouriteMealList.size();
    }

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
