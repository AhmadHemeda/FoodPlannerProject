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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.R;
import com.example.foodplanner.database.MealDataBase;
import com.example.foodplanner.model.FavouriteMeal;
import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.model.PlanMeal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MealFragment extends Fragment {
    private MealDataBase mealDataBase;
    private static final String TAG = "MealFragment";
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    String[] days = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterDays;
    List <String> ingredients = new ArrayList<>();
    List <String> measurements = new ArrayList<>();
    SingleMealAdapter singleMealAdapter;
    YouTubePlayerView mealVideo;
    RecyclerView mealRecyclerView;
    View view;
    LinearLayoutManager linearLayoutManager;
    ImageView imageViewMealImage;
    TextView mealName , textViewStepsDetails , textViewArea;
    Button buttonFavourite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(requireContext());
        singleMealAdapter = new SingleMealAdapter(requireContext());
        mealDataBase = MealDataBase.getInstance(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_meal, container, false);
        mealName = view.findViewById(R.id.textViewMealName);
        buttonFavourite = view.findViewById(R.id.buttonAddToFavourite);
        imageViewMealImage = view.findViewById(R.id.imageViewMealImage);
        textViewStepsDetails = view.findViewById(R.id.textViewStepsDetails);
        mealVideo = view.findViewById(R.id.videoViewMeal);
        textViewArea = view.findViewById(R.id.textViewArea);
        mealRecyclerView = view.findViewById(R.id.recyclerViewIngredients);
        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView);

        getLifecycle().addObserver(mealVideo);
        MealsItem mealsItem = MealFragmentArgs.fromBundle(getArguments()).getSingleMealItem();
        Glide.with(requireContext()).load(mealsItem.getStrMealThumb())
                .placeholder(R.drawable.ic_launcher_foreground)
                .apply(new RequestOptions().override(100,100)).into(imageViewMealImage);
        mealName.setText(mealsItem.getStrMeal());
        Log.i(TAG,
                "onCreateView: "+mealsItem.getStrYoutube());
        String [] split = mealsItem.getStrYoutube().split("=");
        Log.i(TAG, "onCreateView: "+split[0]);
        mealVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = split[0];
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
        textViewStepsDetails.setText(mealsItem.getStrInstructions());
        textViewArea.setText(mealsItem.getStrArea());

//        setIngreds(mealsItem.getStrIngredient1(),mealsItem.getStrMeasure1());
        getIngredient(mealsItem.getStrIngredient1());
        getMeasurement(mealsItem.getStrMeasure1());
        getIngredient(mealsItem.getStrIngredient2());
        getMeasurement(mealsItem.getStrMeasure2());
        getIngredient(mealsItem.getStrIngredient3());
        getMeasurement(mealsItem.getStrMeasure3());
        getIngredient(mealsItem.getStrIngredient4());
        getMeasurement(mealsItem.getStrMeasure4());
        getIngredient(mealsItem.getStrIngredient5());
        getMeasurement(mealsItem.getStrMeasure5());
        getIngredient(mealsItem.getStrIngredient6());
        getMeasurement(mealsItem.getStrMeasure6());
        getIngredient(mealsItem.getStrIngredient7());
        getMeasurement(mealsItem.getStrMeasure7());
        getIngredient(mealsItem.getStrIngredient8());
        getMeasurement(mealsItem.getStrMeasure8());
        getIngredient(mealsItem.getStrIngredient9());
        getMeasurement(mealsItem.getStrMeasure9());
        getIngredient(mealsItem.getStrIngredient10());
        getMeasurement(mealsItem.getStrMeasure10());
        getIngredient(mealsItem.getStrIngredient11());
        getMeasurement(mealsItem.getStrMeasure11());
        getIngredient(mealsItem.getStrIngredient12());
        getMeasurement(mealsItem.getStrMeasure12());
        getIngredient(mealsItem.getStrIngredient13());
        getMeasurement(mealsItem.getStrMeasure13());
        getIngredient(mealsItem.getStrIngredient14());
        getMeasurement(mealsItem.getStrMeasure14());
        getIngredient(mealsItem.getStrIngredient15());
        getMeasurement(mealsItem.getStrMeasure15());
        getIngredient(mealsItem.getStrIngredient16());
        getMeasurement(mealsItem.getStrMeasure16());
        getIngredient(mealsItem.getStrIngredient17());
        getMeasurement(mealsItem.getStrMeasure17());
        getIngredient(mealsItem.getStrIngredient18());
        getMeasurement(mealsItem.getStrMeasure18());
        getIngredient(mealsItem.getStrIngredient19());
        getMeasurement(mealsItem.getStrMeasure19());
        getIngredient(mealsItem.getStrIngredient20());
        getMeasurement(mealsItem.getStrMeasure20());
        Log.i(TAG, "getIngredient: "+ingredients.size());

        buttonFavourite.setOnClickListener(view -> mealDataBase.mealDao().insertFavMeal(new FavouriteMeal(Long.parseLong(mealsItem.getIdMeal()),
                mealsItem.getStrMeal(), mealsItem.getStrMealThumb(), mealsItem.getStrArea(), new ArrayList<>(), new ArrayList<>(),
                mealsItem.getStrInstructions(), mealsItem.getStrYoutube()))

                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        db.collection("database")
                                .document(auth.getCurrentUser().getEmail())
                                .collection("Favourite")
                                .document(mealsItem.getIdMeal())
                                .set(mealsItem);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                }));

        adapterDays = new ArrayAdapter<>(getContext(), R.layout.dropdown_menu_list_item, days);
        autoCompleteTextView.setAdapter(adapterDays);

        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String day = parent.getItemAtPosition(position).toString();

            mealDataBase.planMealDao().insertPlanMeal(new PlanMeal(Long.parseLong(mealsItem.getIdMeal()),
                    mealsItem.getStrMeal(), mealsItem.getStrMealThumb(), mealsItem.getStrArea(), new ArrayList<>(), new ArrayList<>(),
                    mealsItem.getStrInstructions(), mealsItem.getStrYoutube(), day))
                            .subscribeOn(Schedulers.io())
                                    .subscribe(new CompletableObserver() {
                                        @Override
                                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                                        }

                                        @Override
                                        public void onComplete() {

                                        }

                                        @Override
                                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                                        }
                                    });

            Toast.makeText(getContext(), "Day: " + day, Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealRecyclerView = view.findViewById(R.id.recyclerViewIngredients);
        mealRecyclerView.setLayoutManager(linearLayoutManager);
        singleMealAdapter.setList(ingredients ,measurements);
        mealRecyclerView.setAdapter(singleMealAdapter);
    }
//    private List<MealIngredients> setIngreds(String ingredientName, String measurementName){
//        List <MealIngredients> mealIngredientsList = new ArrayList<>();
//        mealIngredientsList.get(0).setIngredients(ingredientName);
//        mealIngredientsList.get(0).setMeasurements(measurementName);
//        Log.i(TAG, "setIngreds: "+  mealIngredientsList.get(0).getIngredients());
//        Log.i(TAG, "setIngreds: "+  mealIngredientsList.get(0).getMeasurements());
//
//        return mealIngredientsList;
//    }
    private List<String> getIngredient(String ingredientName){
        if( ingredientName != null && !ingredientName.isEmpty())
            ingredients.add(ingredientName);
        return ingredients;
    }
    private List<String> getMeasurement(String measurementName){
        if( measurementName != null && !measurementName.isEmpty())
            measurements.add(measurementName);
        return measurements;
    }
}