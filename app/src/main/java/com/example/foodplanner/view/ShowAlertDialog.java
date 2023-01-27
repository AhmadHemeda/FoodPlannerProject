package com.example.foodplanner.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

import com.example.foodplanner.R;

import java.util.Objects;

public class ShowAlertDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Sign Up for More Features")
                .setMessage("Add your food preferences, plan your meals and more")
                .setPositiveButton(R.string.sign_up_text, (dialog, which) -> Navigation
                        .findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.chooserFragment))
                .setNegativeButton(R.string.cancel_text, (dialog, which) -> {});

        return builder.create();
    }
}
