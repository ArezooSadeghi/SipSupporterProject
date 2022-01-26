package com.example.sipsupporterproject.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.sipsupporterproject.R;
import com.example.sipsupporterproject.databinding.FragmentLoginBinding;
import com.example.sipsupporterproject.model.ServerData;
import com.example.sipsupporterproject.ui.dialog.WarningDialogFragment;
import com.example.sipsupporterproject.viewmodel.LoginViewModel;

import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_login,
                container,
                false);

        handleEvents();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserver();
    }

    private void createViewModel() {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    private void setupSpinner(List<ServerData> serverDataList) {
        List<String> centerNames = new ArrayList<>();
        for (ServerData serverData : serverDataList) {
            centerNames.add(serverData.getCenterName());
        }
        binding.spinnerServerData.setItems(centerNames);
    }

    private void handleEvents() {
        binding.ivMore.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.serverDataFragment);
        });
    }

    private void setupObserver() {
        viewModel.getServerDataListLiveData().observe(getViewLifecycleOwner(), serverDataList -> {
            setupSpinner(serverDataList);
            if (serverDataList.size() == 0) {
                WarningDialogFragment fragment = WarningDialogFragment.newInstance(getString(R.string.required_center_info));
                fragment.show(getParentFragmentManager(), WarningDialogFragment.TAG);
            }
        });
    }
}