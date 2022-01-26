package com.example.sipsupporterproject.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sipsupporterproject.R;
import com.example.sipsupporterproject.databinding.ServerDataAdapterItemBinding;
import com.example.sipsupporterproject.model.ServerData;

import java.util.List;

public class ServerDataAdapter extends RecyclerView.Adapter<ServerDataAdapter.ServerDataHolder> {
    private List<ServerData> serverDataList;

    public ServerDataAdapter(List<ServerData> serverDataList) {
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
            binding.tvCenterName.setText(serverData.getIP() + ":" + serverData.getPort());
        }
    }
}
