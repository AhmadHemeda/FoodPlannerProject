package com.example.foodplanner.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.foodplanner.R;
import com.example.foodplanner.database.MealDataBase;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserFragment extends Fragment {
    private AppCompatButton appCompatButtonLogout;
    private View view;
    private MealDataBase roomDb;
    private FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);

        appCompatButtonLogout = view.findViewById(R.id.buttonLogout);

        auth = FirebaseAuth.getInstance();
        roomDb = MealDataBase.getInstance(requireContext());
        if(auth.getCurrentUser() == null){
            appCompatButtonLogout.setText("Sign Up");
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appCompatButtonLogout.setOnClickListener(v -> {
            logoutAction();
            deleteAllFavouriteMeals();
            deleteAllPlanMeals();
            Toast.makeText(requireContext(), "Done", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigate(UserFragmentDirections.actionUserFragmentToSplashFragment());
        });
    }

    private void logoutAction() {
        auth.signOut();
    }

    private void deleteAllPlanMeals() {
        roomDb.planMealDao().deleteAllPlanMeals().subscribeOn(Schedulers.io())
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

    private void deleteAllFavouriteMeals() {
        roomDb.mealDao().deleteAllFavMeals().subscribeOn(Schedulers.io())
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