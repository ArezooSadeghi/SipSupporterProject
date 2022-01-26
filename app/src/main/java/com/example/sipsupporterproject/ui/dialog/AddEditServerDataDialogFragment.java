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
import androidx.lifecycle.ViewModelProvider;

import com.example.sipsupporterproject.R;
import com.example.sipsupporterproject.databinding.FragmentAddEditServerDataDialogBinding;
import com.example.sipsupporterproject.model.ServerData;
import com.example.sipsupporterproject.utils.Checking;
import com.example.sipsupporterproject.viewmodel.LoginViewModel;

public class AddEditServerDataDialogFragment extends DialogFragment {
    private FragmentAddEditServerDataDialogBinding binding;
    private LoginViewModel viewModel;
    private ServerData serverData;
    private String oldCenterName;

    private static final String ARGS_CENTER_NAME = "centerName";
    public static final String TAG = AddEditServerDataDialogFragment.class.getSimpleName();

    public static AddEditServerDataDialogFragment newInstance(String centerName) {
        AddEditServerDataDialogFragment fragment = new AddEditServerDataDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_CENTER_NAME, centerName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createViewModel();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()),
                R.layout.fragment_add_edit_server_data_dialog,
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

    private void createViewModel() {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    private void initViews() {
        oldCenterName = getArguments().getString(ARGS_CENTER_NAME);
        if (!oldCenterName.isEmpty()) {
            serverData = viewModel.select(oldCenterName);
        }
        if (serverData != null) {
            binding.edTxtCenterName.setText(serverData.getCenterName());
            binding.edTxtIP.setText(serverData.getIP());
            binding.edTxtPort.setText(serverData.getPort());
        }
    }

    private void handleError(String msg) {
        ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(msg);
        fragment.show(getParentFragmentManager(), ErrorDialogFragment.TAG);
    }

    private void handleEvents() {
        binding.btnClose.setOnClickListener(v -> {
            if (viewModel.getServerDataListLiveData().getValue().size() == 0) {
                WarningDialogFragment fragment = WarningDialogFragment.newInstance(getString(R.string.fill_required_info));
                fragment.show(getParentFragmentManager(), WarningDialogFragment.TAG);
            }
            dismiss();
        });

        binding.btnSave.setOnClickListener(v -> {
            String centerName = binding.edTxtCenterName.getText().toString().replaceAll(" ", "");
            String IP = binding.edTxtIP.getText().toString().trim().replaceAll(" ", "");
            String port = binding.edTxtPort.getText().toString().replaceAll(" ", "");
            if (centerName.isEmpty() || IP.isEmpty() || port.isEmpty())
                handleError(getString(R.string.fill_required_info));
            else {
                if (Checking.hasLetter(IP) || Checking.hasLetter(port) || !Checking.hasThreeDots(IP) || IP.length() < 7)
                    handleError(getString(R.string.incorrect_format));
                else {
                    serverData = null;
                    if (!oldCenterName.equals(centerName))
                        serverData = viewModel.select(centerName);
                    if (serverData == null) {
                        if (!oldCenterName.isEmpty())
                            viewModel.update(oldCenterName, centerName, IP, port);
                        else
                            viewModel.insert(centerName, IP, port);
                        dismiss();
                    } else
                        handleError(getString(R.string.duplicate_center_name));
                }
            }
        });
    }
}