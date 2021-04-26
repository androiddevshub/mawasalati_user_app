package com.example.mawasalatiuserapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class City {

    @SerializedName("origin_cities")
    @Expose
    ArrayList<String> originCityList;

    @SerializedName("destination_cities")
    @Expose
    ArrayList<String> destinationCityList;


    public ArrayList<String> getOriginCityList() {
        return originCityList;
    }

    public void setOriginCityList(ArrayList<String> originCityList) {
        this.originCityList = originCityList;
    }

    public ArrayList<String> getDestinationCityList() {
        return destinationCityList;
    }

    public void setDestinationCityList(ArrayList<String> destinationCityList) {
        this.destinationCityList = destinationCityList;
    }
}
