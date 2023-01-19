package com.example.foodplanner.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignUpChooserFragment extends Fragment {
    private static final Integer RC_SIGN_UP = 1;
    TextView logInBtn;
    Button googleSignUp;
    Button guestBtn;
    Button gotoSignup;
    private FirebaseAuth auth;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    View _view;
    ActivityResultLauncher<Intent> registerGoogleForActivityResult;
    public SignUpChooserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

       registerGoogleForActivityResult=registerForActivityResult(new ActivityResultContract<Intent, Task<GoogleSignInAccount>>() {
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
        }, new ActivityResultCallback<Task<GoogleSignInAccount>>() {
            @Override
            public void onActivityResult(Task<GoogleSignInAccount> result) {
                if(result == null){
                    //Failed
                }
                else {
                    //success
                    Toast.makeText(requireContext(), "done", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(_view).navigate(SignUpChooserFragmentDirections.actionChooserFragmentToLoaderFragment());
                    FirebaseAuth.getInstance().signInWithCredential(GoogleAuthProvider.getCredential(result.getResult().getIdToken(),null));
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logInBtn = view.findViewById(R.id.btn_goLogIn);
        gotoSignup = view.findViewById(R.id.buttonEmail);
        guestBtn = view.findViewById(R.id.buttonGuest);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        guestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(_view).navigate(SignUpChooserFragmentDirections.actionChooserFragmentToLoaderFragment());
            }
        });
//        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//        googleSignInClient = GoogleSignIn.getClient(requireContext(),googleSignInOptions);

        gotoSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(SignUpChooserFragmentDirections.actionSignUpFragmentToSignUp());
//                Navigation.findNavController(view).navigate(SignUpChooserFragmentDirections.actionChooserFragmentToHomeFragment());

            }
        });
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(SignUpChooserFragmentDirections.actionSignUpFragmentToLoginFragment());
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _view =inflater.inflate(R.layout.fragment_sign_up_chooser, container, false);
        googleSignUp = _view.findViewById(R.id.btn_google_signup);
        googleSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registration();

            }
        });

        return _view;
    }
    private void registration(){
//        Intent intent = googleSignInClient.getSignInIntent();
//        startActivityForResult(intent,RC_SIGN_UP);
        //register method
        registerGoogleForActivityResult.launch(GoogleSignIn.getClient(requireContext(),new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()).getSignInIntent());


    }



}