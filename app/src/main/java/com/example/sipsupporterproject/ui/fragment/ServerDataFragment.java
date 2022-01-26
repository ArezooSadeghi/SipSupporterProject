package com.example.sipsupporterproject.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.sipsupporterproject.R;
import com.example.sipsupporterproject.adapter.ServerDataAdapter;
import com.example.sipsupporterproject.databinding.FragmentServerDataBinding;
import com.example.sipsupporterproject.model.ServerData;
import com.example.sipsupporterproject.ui.dialog.AddEditServerDataDialogFragment;
import com.example.sipsupporterproject.ui.dialog.QuestionDialogFragment;
import com.example.sipsupporterproject.viewmodel.LoginViewModel;

import java.util.List;
import java.util.Objects;

public class ServerDataFragment extends Fragment {
    private FragmentServerDataBinding binding;
    private LoginViewModel viewModel;
    private String centerName;

    public static ServerDataFragment newInstance() {
        ServerDataFragment fragment = new ServerDataFragment();
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
                R.layout.fragment_server_data,
                container,
                false);

        initViews();
        handleEvents();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserver();
    }

    private void createViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
    }

    private void initViews() {
        binding.recyclerViewServerData.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(requireContext(), R.drawable.custom_divider_recycler_view)));
        binding.recyclerViewServerData.addItemDecoration(dividerItemDecoration);
    }

    private void setupAdapter(List<ServerData> serverDataList) {
        ServerDataAdapter adapter = new ServerDataAdapter(viewModel, serverDataList);
        binding.recyclerViewServerData.setAdapter(adapter);
    }

    private void handleEvents() {
        binding.fabAdd.setOnClickListener(v -> {
            AddEditServerDataDialogFragment fragment = AddEditServerDataDialogFragment.newInstance("");
            fragment.show(getParentFragmentManager(), AddEditServerDataDialogFragment.TAG);
        });
    }

    private void setupObserver() {
        viewModel.getServerDataListLiveData().observe(getViewLifecycleOwner(), serverDataList -> {
            setupAdapter(serverDataList);
            if (serverDataList.size() == 0)
                NavHostFragment.findNavController(this).navigate(R.id.loginFragment);
        });

        viewModel.getDeleteClicked().observe(getViewLifecycleOwner(), centerName -> {
            this.centerName = centerName;
            QuestionDialogFragment fragment = QuestionDialogFragment.newInstance(getString(R.string.delete_server_data_question));
            fragment.show(getParentFragmentManager(), QuestionDialogFragment.TAG);
        });

        viewModel.getYesDeleteClicked().observe(getViewLifecycleOwner(), yesDeleteClicked -> {
            viewModel.delete(centerName);
        });

        viewModel.getEditClicked().observe(getViewLifecycleOwner(), centerName -> {
            AddEditServerDataDialogFragment fragment = AddEditServerDataDialogFragment.newInstance(centerName);
            fragment.show(getParentFragmentManager(), AddEditServerDataDialogFragment.TAG);
        });
    }
}