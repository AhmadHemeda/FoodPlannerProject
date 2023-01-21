package com.example.foodplanner.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.SearchByAreaFragmentDirections;
import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.model.RandomMeal;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.model.pojos.area.AreaListModel;
import com.example.foodplanner.model.pojos.area.AreaModel;
import com.example.foodplanner.network.ApiClient;
import com.example.foodplanner.network.NetworkCallBack;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.AreaViewHolder>{

    private List<AreaModel> modelArrayList;
    Context context;
    ViewGroup frag;
    private static final String TAG = "AreaAdapter";
    public AreaAdapter(Context context) {
        this.context = context;
    }
    public void setList(List<AreaModel> updatedItems){
        this.modelArrayList = updatedItems;
    }


    @NonNull
    @Override
    public AreaAdapter.AreaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        frag = parent;
        return new AreaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.area_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AreaAdapter.AreaViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.i(TAG, "onBindViewHolder: " +modelArrayList.size());
        holder.areaTextView.setText(modelArrayList.get(position).getStrArea());
        holder.areaTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(frag)
                                            .navigate(SearchByAreaFragmentDirections.actionSearchByAreaFragmentToAreaMealsFragment(holder.areaTextView.getText().toString()).setAreaName(holder.areaTextView.getText().toString()));
//                Single<RandomMeal> singleObservable = ApiClient.getInstance().getMealArea(holder.areaTextView.getText().toString());
//                singleObservable
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(response ->{
//                                   List<MealsItem> mealsItem = response.getMeals();
////
//                                    Log.i(TAG, "onClick: "+ mealsItem.get(position).getStrMeal());
//                                    Log.i(TAG, "onClick: "+ mealsItem.get(position+1).getStrMeal());
//                                },
//                                error ->{error.printStackTrace();
//                                    Log.i(TAG, "onClick: "+ error.getMessage());
//                                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if (modelArrayList != null){
            return modelArrayList.size();
        }else {
            return 2;
        }
    }

    public class AreaViewHolder extends RecyclerView.ViewHolder{
        TextView areaTextView;

        public AreaViewHolder(@NonNull View itemView) {
            super(itemView);
            areaTextView = itemView.findViewById(R.id.area_textView);
        }
    }
}
