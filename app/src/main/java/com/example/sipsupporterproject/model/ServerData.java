package com.example.sipsupporterproject.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "server_data_tb")
public class ServerData {
    @PrimaryKey
    @NonNull
    private String centerName;
    private String IP;
    private String port;

    public ServerData(@NonNull String centerName, String IP, String port) {
        this.centerName = centerName;
        this.IP = IP;
        this.port = port;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
