package com.example.foodplanner.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.foodplanner.R;
import com.example.foodplanner.database.MealDataBase;
import com.example.foodplanner.model.FavouriteMeal;
import com.example.foodplanner.model.PlanMeal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginFragment extends Fragment {
    private MealDataBase roomDb;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private View view;
    private EditText logInEmail, logInPassword;
    private AppCompatButton logInBtn;
    private TextView signupBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        roomDb = MealDataBase.getInstance(getContext());
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logInEmail = view.findViewById(R.id.et_email);
        logInPassword = view.findViewById(R.id.et_password);
        logInBtn = view.findViewById(R.id.btn_login);
        signupBtn = view.findViewById(R.id.btn_goSignUp);

        SharedPreferences sharedPref = requireContext().getSharedPreferences(
                "setting", Context.MODE_PRIVATE);

        sharedPref.edit().putBoolean("first_look", true).apply();

        logInBtn.setOnClickListener(view1 -> {
            String email = logInEmail.getText().toString();
            String password = logInPassword.getText().toString();
            if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (!password.isEmpty()) {
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnSuccessListener(authResult -> {
                                        gatherAllFavoriteData();
                                        gatherAllPlanData();
                                        Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_LONG).show();
                                        Navigation.findNavController(view1)
                                                .navigate(LoginFragmentDirections.actionLoginFragmentToLoaderFragment());
                                    }
                            ).addOnFailureListener(e -> Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_LONG).show());
                } else {
                    logInPassword.setError("Password can't be empty");
                }
            } else if (email.isEmpty()) {
                logInEmail.setError("email can't be empty");
            } else {
                logInEmail.setError("Please Enter Valid Email");
            }
        });

        signupBtn.setOnClickListener(v -> {
            Navigation.findNavController(view)
                    .navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment());
        });
    }

    private void gatherAllFavoriteData() {
        if (auth.getCurrentUser() == null)
            return;

        ArrayList<FavouriteMeal> favouriteMealArrayList = new ArrayList<>();
        db.collection(MealDataBase.FIRESTORE)
                .document(auth.getCurrentUser().getEmail())
                .collection(MealDataBase.FAV)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        favouriteMealArrayList.add(document.toObject(FavouriteMeal.class));
                    }
                    insertAllFavouriteMeals(favouriteMealArrayList);
                }).addOnFailureListener(e -> {

                });
    }

    private void insertAllFavouriteMeals(ArrayList<FavouriteMeal> favouriteMealArrayList) {
        roomDb.mealDao().insertAllFavMeal(favouriteMealArrayList).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
    }

    private void gatherAllPlanData() {
        if (auth.getCurrentUser() == null)
            return;
        ArrayList<PlanMeal> planMealArrayList = new ArrayList<>();
        db.collection(MealDataBase.FIRESTORE)
                .document(auth.getCurrentUser().getEmail())
                .collection(MealDataBase.PLAN)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        PlanMeal planMeal = document.toObject(PlanMeal.class);
                        planMealArrayList.add(planMeal);
                    }
                    insertAllPlaneMeals(planMealArrayList);

                }).addOnFailureListener(e -> {

                });
    }

    private void insertAllPlaneMeals(ArrayList<PlanMeal> planMealArrayList) {
        roomDb.planMealDao().insertAllPlanMeal(planMealArrayList).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
    }

}