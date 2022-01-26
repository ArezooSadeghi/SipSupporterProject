package com.example.sipsupporterproject.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.sipsupporterproject.R;
import com.example.sipsupporterproject.databinding.FragmentServerDataBinding;
import com.example.sipsupporterproject.ui.dialog.AddEditServerDataDialogFragment;

public class ServerDataFragment extends Fragment {
    private FragmentServerDataBinding binding;

    public static ServerDataFragment newInstance() {
        ServerDataFragment fragment = new ServerDataFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_server_data,
                container,
                false);

        handleEvents();

        return binding.getRoot();
    }

    private void handleEvents() {
        binding.fabAdd.setOnClickListener(v -> {
            AddEditServerDataDialogFragment fragment = AddEditServerDataDialogFragment.newInstance();
            fragment.show(getParentFragmentManager(), AddEditServerDataDialogFragment.TAG);
        });
    }
}