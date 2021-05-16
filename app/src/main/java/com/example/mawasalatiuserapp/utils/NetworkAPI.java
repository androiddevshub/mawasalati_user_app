package com.example.mawasalatiuserapp.utils;

import com.example.mawasalatiuserapp.model.Booking;
import com.example.mawasalatiuserapp.model.User;
import com.example.mawasalatiuserapp.model.responsebean.BookingListResponse;
import com.example.mawasalatiuserapp.model.responsebean.BookingResponse;
import com.example.mawasalatiuserapp.model.responsebean.CityResponse;
import com.example.mawasalatiuserapp.model.responsebean.ScheduledBusResponse;
import com.example.mawasalatiuserapp.model.responsebean.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkAPI {

    @POST("/api/users")
    Call<UserResponse> userRegisterApi(@Body User user);

    @POST("/api/users/login")
    Call<UserResponse> userLoginApi(@Body User user);

    @GET("/api/scheduled_buses/cities")
    Call<CityResponse> cityResponseApi();

    @GET("/api/scheduled_buses/scheduled")
    Call<ScheduledBusResponse> scheduledBusesResponseApi(@Query("origin") String origin, @Query("destination") String destination, @Query("date") String date);

    @POST("/api/bookings")
    Call<BookingResponse> createBooking(@Body Booking booking);

    @GET("/api/bookings/{id}")
    Call<BookingResponse> bookingResponse(@Path("id") int id, @Query("token") String token);


    @GET("/api/bookings")
    Call<BookingListResponse> bookingResponseAll(@Query("token") String token);

}
