package com.example.foodplanner.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignUp extends Fragment {
    FirebaseAuth auth;
    FirebaseFirestore db;
    private EditText signupEmail, signupName, signupPassword, signupPasswordAgain;
    private AppCompatButton signUpBtn;
    public SignUp() {
        // Required empty public constructor
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
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = signupEmail.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();
                if(signupName.getText().toString().trim().isEmpty()){
                    signupName.setError("Name can't be empty");
                }
                if(signupPasswordAgain.getText().toString().trim().isEmpty()){
                    signupPasswordAgain.setError("Password can't be empty");
                }
                if(email.isEmpty()){
                    signupEmail.setError("Email can't be empty");
                }
                if(password.isEmpty()){
                    signupPassword.setError("Password can't be empty");
                }else {
                    auth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("userEmail", signupEmail.getText().toString());
                                        user.put("userName", signupName.getText().toString());

                                        // Add a new document with a generated ID
                                        db.collection("users")
                                                .document(auth.getUid())
                                                .set(user)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
//                                                                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

                                                    }
                                                });

                                        Navigation.findNavController(view).navigate(SignUpDirections.actionSignUpToLoaderFragment());
                                        Toast.makeText(getContext(),"SignUp Successful",Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(getContext(),"SignUp Failed "+task.getException().getMessage(),Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                }


            }
        });
    }
}

