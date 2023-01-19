package com.example.foodplanner.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.R;
import com.example.foodplanner.model.MealsItem;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;


public class MealFragment extends Fragment {
    private static final String TAG = "MealFragment";
    List<String> list=new ArrayList<String>();
    SingleMealAdapter singleMealAdapter;
    YouTubePlayerView mealVideo;
    RecyclerView mealRecyclerView;
    View view;
    LinearLayoutManager linearLayoutManager;
    ImageView imageViewMealImage;
    TextView mealName , textViewStepsDetails , textViewArea;
    public MealFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: iteeeem");
        super.onCreate(savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(requireContext());
        singleMealAdapter = new SingleMealAdapter(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_meal, container, false);
        mealName = view.findViewById(R.id.textViewMealName);
        imageViewMealImage = view.findViewById(R.id.imageViewMealImage);
        textViewStepsDetails = view.findViewById(R.id.textViewStepsDetails);
        mealVideo = view.findViewById(R.id.videoViewMeal);
        textViewArea = view.findViewById(R.id.textViewArea);
        getLifecycle().addObserver(mealVideo);
        MealsItem mealsItem = MealFragmentArgs.fromBundle(getArguments()).getSingleMealItem();
        Glide.with(requireContext()).load(mealsItem.getStrMealThumb())
                .placeholder(R.drawable.ic_launcher_foreground)
                .apply(new RequestOptions().override(100,100)).into(imageViewMealImage);
        mealName.setText(mealsItem.getStrMeal());
        Log.i(TAG, "onCreateView: "+mealsItem.getStrYoutube());
        String [] split = mealsItem.getStrYoutube().split("=");
        Log.i(TAG, "onCreateView: "+split[1]);
        mealVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = split[1];
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
        textViewStepsDetails.setText(mealsItem.getStrInstructions());
        textViewArea.setText(mealsItem.getStrArea());
//        singleMealAdapter.setList(mealsItem.getStrIngredient1());
//        list.add(mealsItem.getStrIngredient1());
//        list.add(mealsItem.getStrIngredient2());
//        list.add(mealsItem.getStrIngredient3());
//        list.add(mealsItem.getStrIngredient4());
//        list.add(mealsItem.getStrIngredient5());
//        list.add(mealsItem.getStrIngredient6());
//        list.add(mealsItem.getStrIngredient7());
//        list.add(mealsItem.getStrIngredient8());
//        list.add(mealsItem.getStrIngredient9());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealRecyclerView = view.findViewById(R.id.recyclerViewIngredients);
//        allRecyclerView.setLayoutManager(linearLayoutManager);
//        singleMealAdapter.setList(list);
//        allRecyclerView.setAdapter(singleMealAdapter);
    }
}