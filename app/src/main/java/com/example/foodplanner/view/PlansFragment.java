package com.example.foodplanner.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.model.RandomMeal;
import com.example.foodplanner.network.ApiClient;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlansFragment extends Fragment implements OnMealItemClickListener {
    private static final String TAG = "PlansFragment";
    private View view;
    private String[] days = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> adapterDays;

    private PlansAdapter plansAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_plans, container, false);

        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextViewPlans);

        adapterDays = new ArrayAdapter<>(getContext(), R.layout.dropdown_menu_list_item, days);
        autoCompleteTextView.setAdapter(adapterDays);

        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String day = parent.getItemAtPosition(position).toString();
            Toast.makeText(getContext(), "Day: " + day, Toast.LENGTH_SHORT).show();
        });

        plansAdapter = new PlansAdapter(requireContext(), this);

        return view;
    }

    @Override
    public void onMealClick(@NonNull MealsItem mealsItem) {
        {
            Single<RandomMeal> singleObservable = ApiClient.getInstance().getMealByName(mealsItem.getStrMeal());
            singleObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe( response -> {
                                List<MealsItem> singleMeal = response.getMeals();
                                NavHostFragment
                                        .findNavController(this)
                                        .navigate(HomeFragmentDirections.actionHomeFragmentToMealFragment(singleMeal.get(0)).setSingleMealItem(singleMeal.get(0)));
                                Log.i(TAG, "onClick: "+ singleMeal.get(0).getStrMeal());
                            },
                            error -> { error.printStackTrace();
                                Log.i(TAG, "onClick: "+ error.getMessage());
                            }
                    );
        }
    }
}