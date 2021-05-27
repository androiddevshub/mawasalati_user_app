package com.example.mawasalatiuserapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mawasalatiuserapp.R;
import com.example.mawasalatiuserapp.model.Booking;
import com.example.mawasalatiuserapp.model.Passenger;
import com.example.mawasalatiuserapp.model.ScheduledBus;
import com.example.mawasalatiuserapp.model.responsebean.BookingResponse;
import com.example.mawasalatiuserapp.utils.ApiClient;
import com.example.mawasalatiuserapp.utils.AppUtils;
import com.example.mawasalatiuserapp.utils.NetworkAPI;
import com.example.mawasalatiuserapp.view.adapters.PassengerSetDetailsRecyclerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookedActivity extends AppCompatActivity {

    private int bookingId;
    private HashMap<String, String> userDetails;
    private String token;
    private ScheduledBus scheduledBus;
    private Booking booking;
    private ImageView imgBackArrow;
    private AppUtils appUtils;
    private ProgressDialog progressDialog;

    private ImageView backImageArrow, homeImage;
    private TextView tvBookingId, tvBookingDate, tvBookedBusNo, tvBookedBusContacts, tvBookedBusPickupPoint, tvBookedDropPoint,
            tvBookedOrigin, tvBookedDestination, tvBookedBusPickupTime, tvBookedBusDropTime, tvBookedBusStartDate, tvBookedBusEndDate,
            tvBookedBusDuration, tvBookedBusName, tvBookedBusType, tvBookedPassengerCount, tvBookedBusBaseFare, tvBookedBusTaxFare,
            tvBookedBusTotalFare;

    private ArrayList<Passenger> passengerArrayList= new ArrayList<>();
    private RecyclerView recyclerViewPassengers;
    private PassengerSetDetailsRecyclerAdapter passengerSetDetailsRecyclerAdapter;


    public static final String[] MONTHS = {"Jan", "Feb", "March", "April", "May", "June",
            "July", "Aug", "Sept", "Oct", "Nov", "Dec"};

    public static final String[] WEEKS = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked);

        init();
        Intent intent = getIntent();
        bookingId = intent.getIntExtra("bookingId", 0);
        progressDialog = new ProgressDialog(BookedActivity.this);
        appUtils = new AppUtils(getApplicationContext(), this);
        userDetails = appUtils.getUserDetails();
        token = userDetails.get(AppUtils.KEY_USER_TOKEN);

        recyclerViewPassengers = findViewById(R.id.booked_bus_passengers_recyclerview);

        getBookingResponse();

        homeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(BookedActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });

    }

    private void getBookingResponse(){
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);

        Call<BookingResponse> bookingResponseCall = networkAPI.bookingResponse(bookingId, token);

        bookingResponseCall.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                if (response.body().isStatus()){
                    booking = response.body().getBooking();
                    scheduledBus = response.body().getScheduledBus();
                    passengerArrayList = booking.getPassengerArrayList();
                    initDataSource();
                    progressDialog.cancel();
                }else{
                    appUtils.showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {

            }
        });

    }

    private void init(){

        tvBookingId = findViewById(R.id.tv_booked_id);
        tvBookingDate = findViewById(R.id.tv_booked_bus_date);
        tvBookedBusNo = findViewById(R.id.tv_booked_bus_no);
        tvBookedBusContacts = findViewById(R.id.tv_booked_bus_contacts);
        tvBookedBusPickupPoint = findViewById(R.id.tv_booked_bus_pickup);
        tvBookedDropPoint = findViewById(R.id.tv_booked_bus_drop);
        tvBookedOrigin = findViewById(R.id.tv_booked_bus_origin);
        tvBookedDestination = findViewById(R.id.tv_booked_bus_destination);
        tvBookedBusPickupTime = findViewById(R.id.tv_booked_bus_pickup_time);
        tvBookedBusDropTime = findViewById(R.id.tv_booked_bus_drop_time);
        tvBookedBusStartDate = findViewById(R.id.tv_booked_bus_start_date);
        tvBookedBusEndDate = findViewById(R.id.tv_booked_bus_end_date);
        tvBookedBusDuration = findViewById(R.id.tv_booked_bus_duration);
        tvBookedBusName = findViewById(R.id.tv_booked_bus_name);
        tvBookedBusType = findViewById(R.id.tv_booked_bus_type);
        tvBookedBusBaseFare = findViewById(R.id.tv_base_fair_booked);
        tvBookedBusTaxFare = findViewById(R.id.tv_booked_tax_fees);
        tvBookedBusTotalFare = findViewById(R.id.tv_total_faretv);
        imgBackArrow = findViewById(R.id.imageView_back_arrow);
        homeImage = findViewById(R.id.imageView_home);
        recyclerViewPassengers = findViewById(R.id.booked_bus_passengers_recyclerview);

    }

    private void initDataSource(){
        tvBookingId.setText("Booking ID "+booking.getBooking_str_id());
        tvBookingDate.setText(booking.getDate());
        tvBookedBusNo.setText(scheduledBus.getBus_number());
        tvBookedBusContacts.setText(scheduledBus.getBus_contact_first() + " "+ scheduledBus.getBus_contact_second());
        tvBookedBusPickupPoint.setText(scheduledBus.getPickup_point());
        tvBookedDropPoint.setText(scheduledBus.getDrop_point());
        tvBookedOrigin.setText(scheduledBus.getOrigin());
        tvBookedDestination.setText(scheduledBus.getDestination());
        tvBookedBusPickupTime.setText(scheduledBus.getDeparture_time());
        tvBookedBusDropTime.setText(scheduledBus.getArrival_time());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date departureDate, arrivalDate;
        String departureDateStr, arrivalDateStr;
        try {
            departureDate = format.parse(scheduledBus.getDeparture_date());
            arrivalDate = format.parse(scheduledBus.getArrival_date());

            departureDateStr = WEEKS[departureDate.getDay()] + ", "+ departureDate.getDate() + " "+ MONTHS[departureDate.getMonth()];
            arrivalDateStr = WEEKS[arrivalDate.getDay()] + ", "+ arrivalDate.getDate() + " "+ MONTHS[arrivalDate.getMonth()];
            tvBookedBusStartDate.setText(departureDateStr);
            tvBookedBusEndDate.setText(arrivalDateStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvBookedBusDuration.setText(scheduledBus.getDuration());
        tvBookedBusName.setText(scheduledBus.getBus_name());
        tvBookedBusType.setText(scheduledBus.getBus_type());
        tvBookedBusBaseFare.setText(passengerArrayList.size() + " x $ "+ booking.getPassenger_fare());
        tvBookedBusTaxFare.setText("$ "+ booking.getTax_fare());
        tvBookedBusTotalFare.setText("$ "+ booking.getTotal_fare());

        passengerSetDetailsRecyclerAdapter = new PassengerSetDetailsRecyclerAdapter(passengerArrayList,getApplicationContext(), "created");
        recyclerViewPassengers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerViewPassengers.setAdapter(passengerSetDetailsRecyclerAdapter);
        passengerSetDetailsRecyclerAdapter.notifyDataSetChanged();
    }
}