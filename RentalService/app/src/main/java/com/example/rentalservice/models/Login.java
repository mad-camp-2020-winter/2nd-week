package com.example.rentalservice.models;

import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("id")
    private String id;
    @SerializedName("password")
    private String password;

    public String getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
