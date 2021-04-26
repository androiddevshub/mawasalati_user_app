package com.example.mawasalatiuserapp.model.responsebean;

import com.example.mawasalatiuserapp.model.City;
import com.example.mawasalatiuserapp.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityResponse {

    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("data")
    @Expose
    private City city;

    public CityResponse(boolean status, City city) {
        this.status = status;
        this.city = city;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
