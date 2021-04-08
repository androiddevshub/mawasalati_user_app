package com.example.mawasalatiuserapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {


    @SerializedName("id")
    @Expose
    private String user_id;

    @SerializedName("name")
    @Expose
    private String user_name;

    @SerializedName("email")
    @Expose
    private String user_email;

    @SerializedName("phone")
    @Expose
    private String user_phone;

    @SerializedName("address")
    @Expose
    private String user_address;

    @SerializedName("password")
    @Expose
    private String user_password;

//    @SerializedName("token")
//    @Expose
//    private String user_id;
//
//    @SerializedName("id")
//    @Expose
//    private String user_id;




}
