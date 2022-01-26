package com.example.sipsupporterproject.ui.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.sipsupporterproject.R;
import com.example.sipsupporterproject.databinding.FragmentAddEditServerDataDialogBinding;

public class AddEditServerDataDialogFragment extends DialogFragment {
    private FragmentAddEditServerDataDialogBinding binding;

    public static final String TAG = AddEditServerDataDialogFragment.class.getSimpleName();

    public static AddEditServerDataDialogFragment newInstance() {
        AddEditServerDataDialogFragment fragment = new AddEditServerDataDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()),
                R.layout.fragment_add_edit_server_data_dialog,
                null,
                false);

        AlertDialog dialog = new AlertDialog
                .Builder(getContext())
                .setView(binding.getRoot())
                .create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }
}