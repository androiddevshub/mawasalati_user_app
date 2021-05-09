package com.example.mawasalatiuserapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.mawasalatiuserapp.R;
import com.example.mawasalatiuserapp.model.ScheduledBus;
import com.example.mawasalatiuserapp.model.responsebean.ScheduledBusResponse;
import com.example.mawasalatiuserapp.utils.ApiClient;
import com.example.mawasalatiuserapp.utils.AppUtils;
import com.example.mawasalatiuserapp.utils.NetworkAPI;
import com.example.mawasalatiuserapp.view.adapters.ScheduledBusesRecyclerAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduledBusActivity extends AppCompatActivity {

    private AppUtils appUtils;
    private ProgressDialog progressDialog;
    private RecyclerView scheduledBusRecyclerView;

    private ScheduledBusesRecyclerAdapter scheduledBusesRecyclerAdapter;

    String date, origin, destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_bus);

        Intent intent = getIntent();

        date = intent.getStringExtra("date");
        origin = intent.getStringExtra("origin");
        destination = intent.getStringExtra("destination");

        progressDialog = new ProgressDialog(ScheduledBusActivity.this);
        appUtils = new AppUtils(getApplicationContext(), this);

        scheduledBusRecyclerView = findViewById(R.id.scheduled_bus_recyclerview);


        getScheduledBusData();


    }

    private void getScheduledBusData(){

        progressDialog.setMessage("Loading Buses");
        progressDialog.show();

        NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);

        Call<ScheduledBusResponse> scheduledBusResponseCall = networkAPI.scheduledBusesResponseApi(origin, destination, date);

        scheduledBusResponseCall.enqueue(new Callback<ScheduledBusResponse>() {
            @Override
            public void onResponse(Call<ScheduledBusResponse> call, Response<ScheduledBusResponse> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    ArrayList<ScheduledBus> scheduledBusArrayList = response.body().getScheduledBusArrayList();

                    scheduledBusesRecyclerAdapter = new ScheduledBusesRecyclerAdapter(scheduledBusArrayList, getApplicationContext());

                    scheduledBusRecyclerView.setAdapter(scheduledBusesRecyclerAdapter);
                    scheduledBusRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



                }else{
                    progressDialog.dismiss();
                    appUtils.showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ScheduledBusResponse> call, Throwable t) {

            }
        });
    }
}