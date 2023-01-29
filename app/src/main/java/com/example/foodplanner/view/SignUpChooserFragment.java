package com.example.foodplanner.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.foodplanner.R;
import com.example.foodplanner.database.MealDataBase;
import com.example.foodplanner.model.FavouriteMeal;
import com.example.foodplanner.model.PlanMeal;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignUpChooserFragment extends Fragment {
    TextView logInBtn;
    Button googleSignUp;
    Button guestBtn;
    Button gotoSignup;
    boolean isConnected = false;
    private FirebaseAuth auth;
    private MealDataBase roomDb;
    private FirebaseFirestore db;
    View _view;
    ActivityResultLauncher<Intent> registerGoogleForActivityResult;
    private static final String TAG = "SignUpChooserFragment";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        auth = FirebaseAuth.getInstance();
        registerGoogleForActivityResult = registerForActivityResult(new ActivityResultContract<Intent, Task<GoogleSignInAccount>>() {
            @Override
            public Task<GoogleSignInAccount> parseResult(int i, @Nullable Intent intent) {
                if (i != Activity.RESULT_OK) {
                    return null;
                }
                return GoogleSignIn.getSignedInAccountFromIntent(intent);
            }

            @NonNull
            @Override
            public Intent createIntent(@NonNull Context context, Intent intent) {
                return intent;
            }
        }, result -> {
            if (result == null) {
            } else {

                auth.signInWithCredential(GoogleAuthProvider.getCredential(result.getResult().getIdToken(), null))
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                if (authResult.getUser()!=null) {
                                    String email = authResult.getUser().getEmail();
                                    Toast.makeText(requireContext(), "done", Toast.LENGTH_SHORT).show();
                                    gatherAllFavoriteData(email);
                                    gatherAllPlanData(email);
                                    Navigation.findNavController(_view).navigate(SignUpChooserFragmentDirections.actionChooserFragmentToLoaderFragment());
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logInBtn = view.findViewById(R.id.btn_goLogIn);
        gotoSignup = view.findViewById(R.id.buttonEmail);
        guestBtn = view.findViewById(R.id.buttonGuest);

        ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected())
            isConnected = true;

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        guestBtn.setOnClickListener(view1 -> Navigation.findNavController(_view).navigate(SignUpChooserFragmentDirections.actionChooserFragmentToLoaderFragment()));
        gotoSignup.setOnClickListener(view12 -> Navigation.findNavController(view12).navigate(SignUpChooserFragmentDirections.actionSignUpFragmentToSignUp()));
        logInBtn.setOnClickListener(view13 -> Navigation.findNavController(view13).navigate(SignUpChooserFragmentDirections.actionSignUpFragmentToLoginFragment()));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_sign_up_chooser, container, false);
        googleSignUp = _view.findViewById(R.id.btn_google_signup);
        roomDb = MealDataBase.getInstance(requireContext());
        db = FirebaseFirestore.getInstance();

        googleSignUp.setOnClickListener(view -> registration());

        return _view;
    }

    private void registration() {
        registerGoogleForActivityResult.launch(GoogleSignIn.getClient(requireContext(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()).getSignInIntent());
    }

    private void gatherAllFavoriteData(String email) {
            ArrayList<FavouriteMeal> favouriteMealArrayList = new ArrayList<>();
            db.collection(MealDataBase.FIRESTORE)
                    .document(email)
                    .collection(MealDataBase.FAV)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            favouriteMealArrayList.add(document.toObject(FavouriteMeal.class));

                        }
                        insertAllFavouriteMeals(favouriteMealArrayList);
                    }).addOnFailureListener(e -> Log.i(TAG, "onFailure: "));
//        }
    }

    private void insertAllFavouriteMeals(ArrayList<FavouriteMeal> favouriteMealArrayList) {
        roomDb.mealDao().insertAllFavMeal(favouriteMealArrayList)
                .subscribeOn(Schedulers.io())
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

    private void gatherAllPlanData(String email) {
        ArrayList<PlanMeal> planMealArrayList = new ArrayList<>();
        db.collection(MealDataBase.FIRESTORE)
                .document(email)
                .collection(MealDataBase.PLAN)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        PlanMeal planMeal = document.toObject(PlanMeal.class);
                        planMealArrayList.add(planMeal);
                    }
                    insertAllPlaneMeals(planMealArrayList);
                    Log.i(TAG, "onSuccess: ");

                }).addOnFailureListener(e -> Log.i(TAG, "onFailure: "));
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