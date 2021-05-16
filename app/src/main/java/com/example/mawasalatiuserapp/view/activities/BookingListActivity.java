package com.example.mawasalatiuserapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.mawasalatiuserapp.R;
import com.example.mawasalatiuserapp.model.BookingList;
import com.example.mawasalatiuserapp.model.ScheduledBus;
import com.example.mawasalatiuserapp.model.responsebean.BookingListResponse;
import com.example.mawasalatiuserapp.model.responsebean.ScheduledBusResponse;
import com.example.mawasalatiuserapp.utils.ApiClient;
import com.example.mawasalatiuserapp.utils.AppUtils;
import com.example.mawasalatiuserapp.utils.NetworkAPI;
import com.example.mawasalatiuserapp.view.adapters.BookingListRecyclerAdapter;
import com.example.mawasalatiuserapp.view.adapters.ScheduledBusesRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingListActivity extends AppCompatActivity {

    private AppUtils appUtils;
    private ProgressDialog progressDialog;
    private RecyclerView bookingListRecyclerView;

    private BookingListRecyclerAdapter bookingListRecyclerAdapter;

    private HashMap<String, String> userDetails;
    private String token;
    private RelativeLayout rlNoBookingsFound;
    private ImageView backHomeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);

        bookingListRecyclerView = findViewById(R.id.booking_list_recyclerview);
        appUtils = new AppUtils(getApplicationContext(), this);
        progressDialog = new ProgressDialog(BookingListActivity.this);

        userDetails = appUtils.getUserDetails();
        token = userDetails.get(AppUtils.KEY_USER_TOKEN);

        rlNoBookingsFound = findViewById(R.id.rl_no_bookings_found);
        backHomeImg = findViewById(R.id.img_arrow_main_page);
        getAllBookings();


        backHomeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void getAllBookings(){

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);

        Call<BookingListResponse> bookingListCall = networkAPI.bookingResponseAll(token);

        bookingListCall.enqueue(new Callback<BookingListResponse>() {
            @Override
            public void onResponse(Call<BookingListResponse> call, Response<BookingListResponse> response) {
                if (response.body().isStatus()){
                    progressDialog.dismiss();

                    ArrayList<BookingList> bookingLists = response.body().getBookingListArrayList();

                    if (bookingLists.isEmpty()){
                        rlNoBookingsFound.setVisibility(View.VISIBLE);
                    }

                    bookingListRecyclerAdapter = new BookingListRecyclerAdapter(bookingLists, getApplicationContext());

                    bookingListRecyclerView.setAdapter(bookingListRecyclerAdapter);
                    bookingListRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



                }else{
                    progressDialog.dismiss();
                    appUtils.showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<BookingListResponse> call, Throwable t) {

            }
        });
    }
}