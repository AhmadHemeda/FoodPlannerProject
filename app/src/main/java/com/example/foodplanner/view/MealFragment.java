package com.example.foodplanner.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MealFragment extends Fragment {
    private MealDataBase mealDataBase;
    private static final String TAG = "MealFragment";
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private String day;
    private TextInputLayout textInputLayout;
    private TextView textView;
    String[] days = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterDays;
    List<String> ingredients = new ArrayList<>();
    List<String> measurements = new ArrayList<>();
    SingleMealAdapter singleMealAdapter;
    YouTubePlayerView mealVideo;
    RecyclerView mealRecyclerView;
    View view;
    LinearLayoutManager linearLayoutManager;
    ImageView imageViewMealImage;
    TextView mealName, textViewStepsDetails, textViewArea , textViewInstructions;
    Button buttonFavourite;
    CardView cardViewMealVideo;

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
        textInputLayout = view.findViewById(R.id.textInputLayoutSelect);
        textView = view.findViewById(R.id.textViewSelectDay);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView);
        cardViewMealVideo = view.findViewById(R.id.cardViewMealVideo);
        textViewInstructions = view.findViewById(R.id.textViewInstructions);
        if( auth.getCurrentUser() == null) {
            textInputLayout.setVisibility(View.GONE);
            buttonFavourite.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }

        getLifecycle().addObserver(mealVideo);
        MealsItem mealsItem = MealFragmentArgs.fromBundle(getArguments()).getSingleMealItem();
        Glide.with(requireContext()).load(mealsItem.getStrMealThumb())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imageViewMealImage);
        mealName.setText(mealsItem.getStrMeal());
        if(mealsItem.getStrYoutube() !=null && !mealsItem.getStrYoutube().isEmpty()){
            String [] split = mealsItem.getStrYoutube().split("=");
            mealVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    String videoId = split[1];
                    youTubePlayer.loadVideo(videoId, 0);
                }
            });

        }else{
            mealVideo.setVisibility(View.GONE);
            cardViewMealVideo.setVisibility(View.GONE);
            textViewInstructions.setVisibility(View.GONE);
        }
        textViewStepsDetails.setText(mealsItem.getStrInstructions());
        textViewArea.setText(mealsItem.getStrArea());

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

        FavouriteMeal favouriteMealItem = new FavouriteMeal(Long.parseLong(mealsItem.getIdMeal()),
                mealsItem.getStrMeal(), mealsItem.getStrMealThumb(), mealsItem.getStrArea(), new ArrayList<>(), new ArrayList<>(),
                mealsItem.getStrInstructions(), mealsItem.getStrYoutube());
        mealDataBase.mealDao().getFavMealByID(favouriteMealItem.getMealID()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Boolean isItem) {
                        if (isItem) {
                            buttonFavourite.setText("Remove");
                            buttonFavourite.setBackgroundColor(getContext().getColor(R.color.red));
                        } else {

                            buttonFavourite.setText("Favorite");
                            buttonFavourite.setBackgroundColor(getContext().getColor(R.color.green_dark));

                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
        buttonFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealDataBase.mealDao().getFavMealByID(favouriteMealItem.getMealID()).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<Boolean>() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Boolean isItem) {
                                if (isItem) {
                                    deleteFavoriteItem(favouriteMealItem);
                                    buttonFavourite.setText("Favorite");
                                    buttonFavourite.setBackgroundColor(getContext().getColor(R.color.green_dark));
                                } else {

                                    insertFavoriteItem(favouriteMealItem);
                                    buttonFavourite.setText("Remove");
                                    buttonFavourite.setBackgroundColor(getContext().getColor(R.color.red));
                                }
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                            }
                        });

            }
        });


        adapterDays = new ArrayAdapter<>(getContext(), R.layout.dropdown_menu_list_item, days);
        autoCompleteTextView.setAdapter(adapterDays);

        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            day = parent.getItemAtPosition(position).toString();
            PlanMeal planMealItem = new PlanMeal(Long.parseLong(mealsItem.getIdMeal()),
                    mealsItem.getStrMeal(), mealsItem.getStrMealThumb(), mealsItem.getStrArea(), new ArrayList<>(), new ArrayList<>(),
                    mealsItem.getStrInstructions(), mealsItem.getStrYoutube(), day);


            mealDataBase.planMealDao().insertPlanMeal(planMealItem)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            if (auth.getCurrentUser() != null)
                                db.collection(MealDataBase.FIRESTORE)
                                        .document(auth.getCurrentUser().getEmail())
                                        .collection(MealDataBase.PLAN)
                                        .document(planMealItem.getMealID() +"_"+ day)
                                        .set(planMealItem);
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
        singleMealAdapter.setList(ingredients, measurements);
        mealRecyclerView.setAdapter(singleMealAdapter);
    }

    private List<String> getIngredient(String ingredientName) {
        if (ingredientName != null && !ingredientName.isEmpty())
            ingredients.add(ingredientName);
        return ingredients;
    }

    private List<String> getMeasurement(String measurementName) {
        if (measurementName != null && !measurementName.isEmpty())
            measurements.add(measurementName);
        return measurements;
    }

    private void insertFavoriteItem(FavouriteMeal favouriteMealItem) {
        mealDataBase.mealDao().insertFavMeal(favouriteMealItem)

                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        if (auth.getCurrentUser() != null)
                            db.collection(MealDataBase.FIRESTORE)
                                    .document(auth.getCurrentUser().getEmail())
                                    .collection(MealDataBase.FAV)
                                    .document(favouriteMealItem.getMealID() + "")
                                    .set(favouriteMealItem);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                });
    }

    private void deleteFavoriteItem(FavouriteMeal favouriteMealItem) {
        mealDataBase.mealDao().deleteFavMeal(favouriteMealItem)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        if (auth.getCurrentUser() != null)
                            db.collection(MealDataBase.FIRESTORE)
                                    .document(auth.getCurrentUser().getEmail())
                                    .collection(MealDataBase.FAV)
                                    .document(favouriteMealItem.getMealID() + "")
                                    .delete();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

}