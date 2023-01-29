package com.example.foodplanner.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.foodplanner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends Fragment {
    FirebaseAuth auth;
    FirebaseFirestore db;
    private EditText signupEmail, signupName, signupPassword, signupPasswordAgain;
    private AppCompatButton signUpBtn;

    public SignUp() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signupEmail = view.findViewById(R.id.et_SignupEmail);
        signupName = view.findViewById(R.id.et_SignupName);
        signupPassword = view.findViewById(R.id.et_SignupPassword);
        signupPasswordAgain = view.findViewById(R.id.et_passwordAgain);
        signUpBtn = view.findViewById(R.id.btn_signup);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        signUpBtn.setOnClickListener(view1 -> {
            String email = signupEmail.getText().toString().trim();
            String password = signupPassword.getText().toString().trim();
            if (signupName.getText().toString().trim().isEmpty()) {
                signupName.setError("Name can't be empty");
            }
            if (signupPasswordAgain.getText().toString().trim().isEmpty()) {
                signupPasswordAgain.setError("Password can't be empty");
            }
            if (email.isEmpty()) {
                signupEmail.setError("Email can't be empty");
            }
            if (password.isEmpty()) {
                signupPassword.setError("Password can't be empty");
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Navigation.findNavController(view1).navigate(SignUpDirections.actionSignUpToLoaderFragment());
                                Toast.makeText(getContext(), "SignUp Successful", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(), "SignUp Failed " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
            }
        });
    }
}