package com.example.rentalservice.models;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class RentalDetail {
    @SerializedName("item_id")
    private String item_id;
    @SerializedName("institution_id")
    private String institution_id;
    @SerializedName("approval")
    private int approval;
    @SerializedName("server_date")
    private int server_date;
    @SerializedName("rental_date")
    private int rental_date;
    @SerializedName("user_phone")
    private String user_phone;
    @SerializedName("user_name")
    private String user_name;
    @SerializedName("comment")
    private String comment;
    @SerializedName("count")
    private int count;

    public String getItem_id() { return item_id; }
    public String getInstitution_id() { return institution_id; }
    public int getApproval() { return approval; }
    public int getServer_date() { return server_date; }
    public int getRental_date() { return rental_date; }
    public String getUser_phone() { return user_phone; }
    public String getUser_name() { return user_name; }
    public String getComment() { return comment; }
    public int getCount() { return count; }

    public void setItem_id(String item_id) { this.item_id = item_id; }
    public void setInstitution_id(String institution_id) { this.institution_id = institution_id; }
    public void setApproval(int approval) { this.approval = approval; }
    public void setServer_date(int server_date) { this.server_date = server_date; }
    public void setRental_date(int rental_date) { this.rental_date = rental_date; }
    public void setUser_phone(String user_phone) { this.user_phone = user_phone; }
    public void setUser_name(String user_name) { this.user_name = user_name; }
    public void setComment(String comment) { this.comment = comment; }
    public void setCount(int count) { this.count = count; }
}
