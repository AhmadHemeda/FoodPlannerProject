package com.example.foodplanner.view;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.R;


public class AllSearchesFragment extends Fragment {

    CardView areaCardView;
    CardView categoryCardView;
    CardView ingredientCardView;
    View view;
    public AllSearchesFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_searches, container, false);

        ingredientCardView = view.findViewById(R.id.ingredient_cardView);
        ingredientCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(AllSearchesFragmentDirections.actionAllSearchesFragmentToSearchByIngredientFragment());
            }
        });

        categoryCardView = view.findViewById(R.id.category_cardView);
        categoryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(AllSearchesFragmentDirections.actionAllSearchesFragmentToSearchByAreaFragment());
            }
        });

        areaCardView = view.findViewById(R.id.area_cardView);
        areaCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(AllSearchesFragmentDirections.actionAllSearchesFragmentToSearchByAreaFragment());
            }
        });
        return view;
    }
}