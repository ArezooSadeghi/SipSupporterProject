package com.example.sipsupporterproject.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.sipsupporterproject.model.ServerData;
import com.example.sipsupporterproject.room.ServerDataDao;
import com.example.sipsupporterproject.room.ServerDataRoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SipSupporterRepository {
    public static SipSupporterRepository sipSupporterRepository;
    private ServerDataDao serverDataDao;
    private LiveData<List<ServerData>> serverDataListLiveData;

    private static final String TAG = SipSupporterRepository.class.getSimpleName();

    private SipSupporterRepository(Context context) {
        ServerDataRoomDatabase db = ServerDataRoomDatabase.getServerDataRoomDatabase(context);
        serverDataDao = db.getServerDataDao();
        serverDataListLiveData = serverDataDao.selectAll();
    }

    public static SipSupporterRepository getSipSupporterRepository(Context context) {
        if (sipSupporterRepository == null) {
            sipSupporterRepository = new SipSupporterRepository(context.getApplicationContext());
        }
        return sipSupporterRepository;
    }

    public LiveData<List<ServerData>> getServerDataListLiveData() {
        return serverDataListLiveData;
    }

    public void insert(String centerName, String IP, String port) {
        new InsertAsyncTask(serverDataDao).execute(centerName, IP, port);
    }

    public void delete(String centerName) {
        new DeleteAsyncTask(serverDataDao).execute(centerName);
    }

    public void update(String oldCenterName, String newCenterName, String IP, String port) {
        new UpdateAsyncTask(serverDataDao).execute(oldCenterName, newCenterName, IP, port);
    }

    public ServerData select(String centerName) {
        try {
            return new SelectAsyncTask(serverDataDao).execute(centerName).get();
        } catch (ExecutionException e) {
            Log.e(TAG, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    private class InsertAsyncTask extends AsyncTask<String, Void, Void> {
        private ServerDataDao serverDataDao;

        InsertAsyncTask(ServerDataDao serverDataDao) {
            this.serverDataDao = serverDataDao;
        }

        @Override
        protected Void doInBackground(String... params) {
            serverDataDao.insert(params[0], params[1], params[2]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<String, Void, Void> {
        private ServerDataDao serverDataDao;

        DeleteAsyncTask(ServerDataDao serverDataDao) {
            this.serverDataDao = serverDataDao;
        }

        @Override
        protected Void doInBackground(String... params) {
            serverDataDao.delete(params[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends AsyncTask<String, Void, Void> {

        private ServerDataDao serverDataDao;

        UpdateAsyncTask(ServerDataDao serverDataDao) {
            this.serverDataDao = serverDataDao;
        }

        @Override
        protected Void doInBackground(String... params) {
            serverDataDao.update(params[0], params[1], params[2], params[3]);
            return null;
        }
    }

    private class SelectAsyncTask extends AsyncTask<String, Void, ServerData> {
        private ServerDataDao serverDataDao;

        SelectAsyncTask(ServerDataDao serverDataDao) {
            this.serverDataDao = serverDataDao;
        }

        @Override
        protected ServerData doInBackground(String... params) {
            return serverDataDao.select(params[0]);
        }
    }
}
