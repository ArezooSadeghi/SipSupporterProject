package com.example.sipsupporterproject.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sipsupporterproject.R;
import com.example.sipsupporterproject.databinding.ServerDataAdapterItemBinding;
import com.example.sipsupporterproject.model.ServerData;
import com.example.sipsupporterproject.viewmodel.LoginViewModel;

import java.util.List;

public class ServerDataAdapter extends RecyclerView.Adapter<ServerDataAdapter.ServerDataHolder> {
    private LoginViewModel viewModel;
    private List<ServerData> serverDataList;

    public ServerDataAdapter(LoginViewModel viewModel, List<ServerData> serverDataList) {
        this.viewModel = viewModel;
        this.serverDataList = serverDataList;
    }

    @NonNull
    @Override
    public ServerDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ServerDataHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.server_data_adapter_item,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServerDataHolder holder, int position) {
        ServerData serverData = serverDataList.get(position);
        holder.bind(serverData);
        holder.binding.ivDelete.setOnClickListener(v -> {
            viewModel.getDeleteClicked().setValue(serverData.getCenterName());
        });

        holder.binding.ivEdit.setOnClickListener(v -> {
            viewModel.getEditClicked().setValue(serverData.getCenterName());
        });
    }

    @Override
    public int getItemCount() {
        return serverDataList != null ? serverDataList.size() : 0;
    }

    public class ServerDataHolder extends RecyclerView.ViewHolder {
        private ServerDataAdapterItemBinding binding;

        public ServerDataHolder(ServerDataAdapterItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(ServerData serverData) {
            binding.tvCenterName.setText(serverData.getCenterName());
            binding.tvAddress.setText(serverData.getIP() + ":" + serverData.getPort());
        }
    }
}
