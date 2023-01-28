package com.example.foodplanner.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.model.RandomMeal;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.network.ApiClient;
import com.example.foodplanner.network.ApiServer;
import com.example.foodplanner.network.NetworkCallBack;
import com.example.foodplanner.presenter.SingleMeal.GetMealViewInterface;
import com.example.foodplanner.presenter.randomMeals.GetRandomMealInterfacePresenter;
import com.example.foodplanner.presenter.randomMeals.GetRandomMealPresenterPresenter;
import com.example.foodplanner.presenter.randomMeals.RandomMealViewInterface;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class HomeFragment extends Fragment implements RandomMealViewInterface {
    private static final String TAG = "HomeFragment";
    RecyclerView allRecyclerView;
    GridLayoutManager linearLayout;
    MealAdapter mealAdapter;
    View view;
    GetRandomMealInterfacePresenter getRandomMealInterfacePresenter;
    ImageView imageViewRandomMealThumb;
    TextView textViewRandomMealTitle;

    Repository repository;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getRandomMealInterfacePresenter = new GetRandomMealPresenterPresenter(this, Repository.getInstance(requireContext()));


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allRecyclerView = view.findViewById(R.id.recyclerView);
        imageViewRandomMealThumb = view.findViewById(R.id.imageViewRandomMeal);
        textViewRandomMealTitle = view.findViewById(R.id.textViewRandomMeal);
        getRandomMealInterfacePresenter.getRandomMeal();
        Single<RandomMeal> mealsItemSingle = ApiClient.getInstance(requireContext()).getInspirationMeal();
        mealsItemSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealsitem-> {mealsitem.getMeals();
                textViewRandomMealTitle.setText(mealsitem.getMeals().get(0).getStrMeal());
                    Glide.with(requireContext()).load(mealsitem.getMeals().get(0).getStrMealThumb()).into(imageViewRandomMealThumb);
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void showMeals(List<MealsItem> randomMeal) {
        linearLayout = new GridLayoutManager(requireContext(), 2);
        mealAdapter = new MealAdapter(requireContext(), randomMeal1 -> {
            Navigation.findNavController(view).navigate(HomeFragmentDirections.actionHomeFragmentToMealFragment(randomMeal1.get(0)).setSingleMealItem(randomMeal1.get(0)));
        });
        mealAdapter.setList(randomMeal);
        allRecyclerView.setLayoutManager(linearLayout);
        allRecyclerView.setAdapter(mealAdapter);
    }
}