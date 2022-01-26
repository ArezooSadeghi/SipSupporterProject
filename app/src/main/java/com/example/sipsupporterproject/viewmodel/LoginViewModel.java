package com.example.sipsupporterproject.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sipsupporterproject.model.ServerData;
import com.example.sipsupporterproject.repository.SipSupporterRepository;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {
    private SipSupporterRepository repository;
    private LiveData<List<ServerData>> serverDataListLiveData;
    private SingleLiveEvent<String> deleteClicked = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> yesDeleteClicked = new SingleLiveEvent<>();
    private SingleLiveEvent<String> editClicked = new SingleLiveEvent<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = SipSupporterRepository.getSipSupporterRepository(getApplication());
        serverDataListLiveData = repository.getServerDataListLiveData();
    }

    public LiveData<List<ServerData>> getServerDataListLiveData() {
        return serverDataListLiveData;
    }

    public SingleLiveEvent<String> getDeleteClicked() {
        return deleteClicked;
    }

    public SingleLiveEvent<Boolean> getYesDeleteClicked() {
        return yesDeleteClicked;
    }

    public SingleLiveEvent<String> getEditClicked() {
        return editClicked;
    }

    public void insert(String centerName, String IP, String port) {
        repository.insert(centerName, IP, port);
    }

    public void delete(String centerName) {
        repository.delete(centerName);
    }

    public void update(String oldCenterName, String newCenterName, String IP, String port) {
        repository.update(oldCenterName, newCenterName, IP, port);
    }

    public ServerData select(String centerName) {
        return repository.select(centerName);
    }
}
