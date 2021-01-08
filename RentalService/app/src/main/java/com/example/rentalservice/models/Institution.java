package com.example.rentalservice.models;

import com.google.gson.annotations.SerializedName;

public class Institution {
        @SerializedName("_id")
        private String _id;
        @SerializedName("name")
        private String name;
        @SerializedName("number")
        private String number;
        @SerializedName("location")
        private String location;

        public String get_id(){ return _id; }
        public String getName(){ return name; }
        public String getNumber(){ return number; }
        public String getLocation(){ return location; }

        public void set_id(String id){this.name = id;}
        public void setName(String name){this.name = name;}
        public void setNumber(String number){this.number = number;}
        public void setLocation(String location){this.location = location;}
}
