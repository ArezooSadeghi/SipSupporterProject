package com.example.sipsupporterproject.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.sipsupporterproject.model.ServerData;

@Database(entities = {ServerData.class}, version = 1, exportSchema = false)
public abstract class ServerDataRoomDatabase extends RoomDatabase {
    private static ServerDataRoomDatabase serverDataRoomDatabase;

    public abstract ServerDataDao getServerDataDao();

    public static ServerDataRoomDatabase getServerDataRoomDatabase(Context context) {
        synchronized (ServerDataRoomDatabase.class) {
            if (serverDataRoomDatabase == null) {
                serverDataRoomDatabase = Room
                        .databaseBuilder(context.getApplicationContext(), ServerDataRoomDatabase.class, "server_data_db")
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return serverDataRoomDatabase;
        }
    }
}
