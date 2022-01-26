package com.example.sipsupporterproject.room;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.sipsupporterproject.model.ServerData;

@Dao
public interface ServerDataDao {

    @Query("INSERT INTO server_data_tb (centerName, IP, port) VALUES (:centerName, :IP, :port)")
    void insert(String centerName, String IP, String port);

    @Query("DELETE FROM server_data_tb WHERE centerName = :centerName")
    void delete(String centerName);

    @Query("UPDATE server_data_tb SET centerName = :newCenterName, IP = :IP, port = :port WHERE centerName = :oldCenterName")
    void update(String oldCenterName, String newCenterName, String IP, String port);

    @Query("SELECT * FROM server_data_tb WHERE centerName = :centerName")
    ServerData select(String centerName);
}
