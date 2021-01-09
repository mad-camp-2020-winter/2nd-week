package com.example.rentalservice.models;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class RentalDetail {
    @SerializedName("phone")
    private String phone;
    @SerializedName("item_id")
    private String item_id;
    @SerializedName("institution_id")
    private String institution_id;
    @SerializedName("approval")
    private int approval;
    @SerializedName("date")
    private Date date;
}
