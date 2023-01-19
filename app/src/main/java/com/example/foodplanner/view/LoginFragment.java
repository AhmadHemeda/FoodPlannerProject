package com.example.foodplanner.view;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.model.MealsItem;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.network.ApiClient;
import com.example.foodplanner.presenter.GetRandomMealInterfacePresenter;
import com.example.foodplanner.presenter.GetRandomMealPresenterPresenter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class LoginFragment extends Fragment{

    private static final String TAG = "LoginFragment";
    private FirebaseAuth auth;
    private EditText logInEmail,logInPassword;
    private AppCompatButton logInBtn;
    TextView signupBtn;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        logInEmail = view.findViewById(R.id.et_email);
        logInPassword = view.findViewById(R.id.et_password);

        logInBtn = view.findViewById(R.id.btn_login);
        signupBtn = view.findViewById(R.id.btn_goSignUp);
        SharedPreferences sharedPref = requireContext().getSharedPreferences(
                "setting", Context.MODE_PRIVATE);
        sharedPref.edit().putBoolean("first_look", true).apply();
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = logInEmail.getText().toString();
                String password = logInPassword.getText().toString();
                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if(!password.isEmpty()){
                        auth.signInWithEmailAndPassword(email,password)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(getContext(),"Login Successful",Toast.LENGTH_LONG).show();
                                        Navigation.findNavController(view).navigate(LoginFragmentDirections.actionLoginFragmentToLoaderFragment());

                                    }
                                }
                                ).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();

                                    }
                                });
                    }else {
                        logInPassword.setError("Password can't be empty");
                    }
                }
                else if (email.isEmpty()){
                    logInEmail.setError("email can't be empty");
                }
                else {
                    logInEmail.setError("Please Enter Valid Email");
                }
            }
        });
        signupBtn.setOnClickListener(v->{
            NavDirections navDirections =LoginFragmentDirections.actionLoginFragmentToSignUp();
            NavController navController = Navigation.findNavController(v);
            navController.navigate(navDirections);
        });

    }



}
//.addOnCompleteListener(getContext(), new OnCompleteListener<AuthResult>() {
//@Override
//public void onComplete(@NonNull Task<AuthResult> task) {
//        if(task.isSuccessful()){
//        FirebaseUser user = auth.getCurrentUser();
//        //updateUI(user);
//        }else {
//        Toast.makeText(getContext(),"Login Failed",Toast.LENGTH_LONG).show();
//        }
//        });