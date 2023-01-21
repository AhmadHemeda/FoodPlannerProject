package com.example.foodplanner.view;

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

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>{
    private Context context;
    public static String TAG = "Adapter";
    private List<String> names;
    ViewGroup frag;


    public SearchAdapter(Context context, List<String> namesList) {
        this.context = context;
        this.names = namesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        frag = parent;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.area_item,parent,false);
        Log.d(TAG, "onCreateViewHolder: ");
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.item.setText((CharSequence)names.get(position));
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(frag)
                        .navigate(SearchByAreaFragmentDirections.actionSearchByAreaFragmentToAreaMealsFragment(holder.item.getText().toString()).setAreaName(holder.item.getText().toString()));
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ");
        return names.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView item;
        public MyViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.area_textView);

        }
    }
}
