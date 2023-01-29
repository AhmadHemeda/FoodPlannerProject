package com.example.foodplanner.view.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.model.pojos.area.AreaModel;

import java.util.ArrayList;
import java.util.List;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.AreaViewHolder> {

    private List<AreaModel> modelArrayList = new ArrayList<>();
    Context context;
    ViewGroup frag;

    public AreaAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<AreaModel> updatedItems) {
        this.modelArrayList = updatedItems;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public AreaAdapter.AreaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        frag = parent;
        return new AreaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.area_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AreaAdapter.AreaViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.areaTextView.setText(modelArrayList.get(position).getStrArea());
        holder.areaTextView.setOnClickListener(view -> Navigation.findNavController(frag)
                .navigate(SearchByAreaFragmentDirections.actionSearchByAreaFragmentToAreaMealsFragment(holder.areaTextView.getText().toString()).setAreaName(holder.areaTextView.getText().toString())));
    }

    @Override
    public int getItemCount() {
        if (modelArrayList != null) {
            return modelArrayList.size();
        } else {
            return 2;
        }
    }

    public class AreaViewHolder extends RecyclerView.ViewHolder {
        TextView areaTextView;

        public AreaViewHolder(@NonNull View itemView) {
            super(itemView);
            areaTextView = itemView.findViewById(R.id.area_textView);
        }
    }
}
