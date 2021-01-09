package com.example.rentalservice.models;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("_id")
    private String _id;
    @SerializedName("name")
    private String name;
    @SerializedName("photo")
    private String photo;
    @SerializedName("count")
    private String count;
    @SerializedName("institution_id")
    private String institution_id;

    public String get_id() { return _id; }
    public String getName() { return name; }
    public String getPhoto() { return photo; }
    public String getCount() { return count; }
    public String getInstitution_id() { return institution_id; }

    public void set_id(String _id) { this._id = _id; }
    public void setName(String name) { this.name = name; }
    public void setPhoto(String photo) { this.photo = photo; }
    public void setCount(String count) { this.count = count; }
    public void setInstitution_id(String institution_id) { this.institution_id = institution_id; }
}
