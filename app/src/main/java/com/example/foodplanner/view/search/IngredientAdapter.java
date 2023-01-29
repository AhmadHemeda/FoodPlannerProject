package com.example.foodplanner.view.search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.model.pojos.area.IngredientModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<IngredientModel> modelArrayList = new ArrayList<>();
    Context context;
    ViewGroup frag;
    private static final String TAG = "AreaAdapter";

    public IngredientAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<IngredientModel> updatedItems) {
        this.modelArrayList = updatedItems;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        frag = parent;
        return new IngredientAdapter.IngredientViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_image_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: " + modelArrayList.size());
        holder.ingredientTextView.setText(modelArrayList.get(position).getStrIngredient());

        Glide.with(context).load(String.format("https://www.themealdb.com/images/ingredients/%s-Small.png", modelArrayList.get(position).getStrIngredient()))
                .placeholder(R.drawable.ic_launcher_foreground).into(holder.ingredientImage);

        holder.itemView.setOnClickListener(view -> Navigation.findNavController(frag)
                .navigate(SearchByIngredientFragmentDirections.actionSearchByIngredientFragmentToIngredientsMealsFragment(holder.ingredientTextView.getText().toString()).setIngredientName(holder.ingredientTextView.getText().toString())));
    }

    @Override
    public int getItemCount() {
        if (modelArrayList != null) {
            return modelArrayList.size();
        } else {
            return 2;
        }
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientTextView;
        CircleImageView ingredientImage;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientTextView = itemView.findViewById(R.id.textViewIngredient);
            ingredientImage = itemView.findViewById(R.id.ingredient_image);
        }
    }
}
