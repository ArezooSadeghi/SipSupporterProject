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
import com.example.sipsupporterproject.databinding.FragmentWarningDialogBinding;

public class WarningDialogFragment extends DialogFragment {
    private FragmentWarningDialogBinding binding;

    private static final String ARGS_MSG = "msg";
    public static final String TAG = WarningDialogFragment.class.getSimpleName();

    public static WarningDialogFragment newInstance(String msg) {
        WarningDialogFragment fragment = new WarningDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_MSG, msg);
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
                R.layout.fragment_warning_dialog,
                null,
                false);

        initViews();
        handleEvents();

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

    private void initViews() {
        String msg = getArguments().getString(ARGS_MSG);
        binding.tvMsg.setText(msg);
    }

    private void handleEvents() {
        binding.btnConfirmation.setOnClickListener(v -> {
            AddEditServerDataDialogFragment fragment = AddEditServerDataDialogFragment.newInstance("");
            fragment.show(getParentFragmentManager(), AddEditServerDataDialogFragment.TAG);
            dismiss();
        });
    }
}