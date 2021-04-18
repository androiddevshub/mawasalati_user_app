package com.example.mawasalatiuserapp.utils;

import com.example.mawasalatiuserapp.model.User;
import com.example.mawasalatiuserapp.model.responsebean.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NetworkAPI {

    @POST("/api/users")
    Call<UserResponse> userRegisterApi(@Body User user);

    @POST("/api/users/login")
    Call<UserResponse> userLoginApi(@Body User user);
}
