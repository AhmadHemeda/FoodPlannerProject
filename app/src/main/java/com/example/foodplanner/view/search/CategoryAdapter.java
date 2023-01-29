package com.example.foodplanner.view.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.model.pojos.area.CategoryModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<CategoryModel> modelArrayList = new ArrayList<>();
    Context context;
    ViewGroup frag;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<CategoryModel> updatedItems) {
        this.modelArrayList = updatedItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        frag = parent;
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_image_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        holder.areaTextView.setText(modelArrayList.get(position).getStrCategory());

        Glide.with(context).load(String.format("https://www.themealdb.com/images/category/%s.png", modelArrayList.get(position).getStrCategory()).toLowerCase())
                .placeholder(R.drawable.ic_launcher_foreground).into(holder.ingredientImage);

        holder.itemView.setOnClickListener(view -> Navigation.findNavController(frag)
                .navigate(SearchByCategoryFragmentDirections.actionSearchByCategoryFragmentToCategoryMealsFragment(holder.areaTextView.getText().toString()).setCategoryName(holder.areaTextView.getText().toString())));
    }

    @Override
    public int getItemCount() {
        if (modelArrayList != null) {
            return modelArrayList.size();
        } else {
            return 2;
        }
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView areaTextView;
        CircleImageView ingredientImage;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            areaTextView = itemView.findViewById(R.id.textViewIngredient);
            ingredientImage = itemView.findViewById(R.id.ingredient_image);
        }
    }
}
