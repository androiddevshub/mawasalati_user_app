package com.example.mawasalatiuserapp.view.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mawasalatiuserapp.R;
import com.example.mawasalatiuserapp.model.Booking;
import com.example.mawasalatiuserapp.model.Passenger;
import com.example.mawasalatiuserapp.model.ScheduledBus;
import com.example.mawasalatiuserapp.model.User;
import com.example.mawasalatiuserapp.model.responsebean.BookingResponse;
import com.example.mawasalatiuserapp.model.responsebean.UserResponse;
import com.example.mawasalatiuserapp.utils.ApiClient;
import com.example.mawasalatiuserapp.utils.AppUtils;
import com.example.mawasalatiuserapp.utils.NetworkAPI;
import com.example.mawasalatiuserapp.view.adapters.PassengerSetDetailsRecyclerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity {

    private HashMap<String, String> userDetails;

    private ScheduledBus scheduledBus;
    private ImageView imgBackArrow;
    private TextView tvMainOrigin, tvMainDestination, tvBookingBusName, tvBookingBusType,tvBusPickUpTime, tvBusDropTime,
            tvBusBookingDateOrigin, tvBusBookingDateDestination, tvBusScheduledDuration, tvBusBookingOrigin, tvBusBookingDestination,
            tvBusBookingOriginPickup, tvBusBookingDestinationDrop, tvBusBookingPassengerFare, tvBusBookingTaxFare,
            tvBusBookingTotalFare, tvBusBookingTotalFareBold;

    private EditText etPassengerName, etPassengerAge, etBookingEmail, etBookingPhone;
    private Button btnAddPassenger, btnBookTicket;

    private AppUtils appUtils;

    private CardView cardViewAddedPassengers;

    public static final String[] MONTHS = {"Jan", "Feb", "March", "April", "May", "June",
            "July", "Aug", "Sept", "Oct", "Nov", "Dec"};

    public static final String[] WEEKS = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

    private RadioGroup radioGroupPassengerType;
    private RadioButton radioButtonPassengerType;
    private ArrayList<Passenger> passengerArrayList= new ArrayList<>();
    private RecyclerView recyclerViewPassengers;
    private PassengerSetDetailsRecyclerAdapter passengerSetDetailsRecyclerAdapter;

    private int userId;
    private int passengerFareInteger, taxFareInteger, totalFareInteger;
    private ProgressDialog progressDialog;
    private Booking booking;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        scheduledBus = getIntent().getExtras().getParcelable("scheduledBus");
        Log.v("Data Data", scheduledBus.getBus_name());

        appUtils = new AppUtils(getApplicationContext(), this);
        progressDialog = new ProgressDialog(BookingActivity.this);
        userDetails = appUtils.getUserDetails();
        userId = Integer.valueOf(userDetails.get(AppUtils.KEY_USER_ID));

        init();
        initDataSource();

        if (passengerArrayList.isEmpty()){
            cardViewAddedPassengers.setVisibility(View.GONE);
            setBookingFareData();
        }

        passengerSetDetailsRecyclerAdapter = new PassengerSetDetailsRecyclerAdapter(passengerArrayList,getApplicationContext(), "");
        recyclerViewPassengers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingActivity.this, ScheduledBusActivity.class);
                intent.putExtra("date", scheduledBus.getDeparture_date());
                intent.putExtra("origin", scheduledBus.getOrigin());
                intent.putExtra("destination", scheduledBus.getDestination());
                startActivity(intent);
            }
        });

        btnAddPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String passengerNameStr = etPassengerName.getText().toString();
                String passengerAgeStr = etPassengerAge.getText().toString();
                int selectedId = radioGroupPassengerType.getCheckedRadioButtonId();
                radioButtonPassengerType = (RadioButton)findViewById(selectedId);

                if (selectedId == -1){
                    appUtils.showToast("Please select gender");
                }
                else if (passengerNameStr.isEmpty()){
                    appUtils.showToast("Please enter name");
                }else if(passengerAgeStr.isEmpty()){
                    appUtils.showToast("Please enter age");
                }
                else {
                    cardViewAddedPassengers.setVisibility(View.VISIBLE);
                    Passenger passenger = new Passenger(passengerNameStr, passengerAgeStr,  String.valueOf(radioButtonPassengerType.getText()).toLowerCase());
                    passengerArrayList.add(passenger);
                    recyclerViewPassengers.setAdapter(passengerSetDetailsRecyclerAdapter);
                    passengerSetDetailsRecyclerAdapter.notifyDataSetChanged();
                    etPassengerName.setText("");
                    etPassengerAge.setText("");
                    radioGroupPassengerType.clearCheck();
                    setBookingFareData();
                }
            }
        });

        passengerSetDetailsRecyclerAdapter.setOnItemClickListener((view, position) -> {
            Passenger passenger = passengerArrayList.get(position);

            appUtils.showToast("Deleted: "+passenger.getName());
            passengerArrayList.remove(position);
            passengerSetDetailsRecyclerAdapter.notifyDataSetChanged();
            if (passengerArrayList.isEmpty()){
                cardViewAddedPassengers.setVisibility(View.GONE);
            }
            setBookingFareData();
        });


        btnBookTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (passengerArrayList.isEmpty()){
                    appUtils.showToast("Add passengers");
                }else{

                    String email = etBookingEmail.getText().toString();
                    String phone = etBookingPhone.getText().toString();

                    if (email.isEmpty()){
                        appUtils.showToast("Please enter contact email");
                    }else if(phone.isEmpty()){
                        appUtils.showToast("Please enter contact phone");
                    }else{
                        booking = new Booking();

                        String time = new SimpleDateFormat("hh:mm", Locale.getDefault()).format(new Date());
                        Date date = new Date();

                        long bookingId = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;

                        booking.setScheduled_bus_id(scheduledBus.getId());
                        booking.setUser_id(userId);
                        booking.setBooking_str_id(String.valueOf(bookingId));
                        booking.setDate(WEEKS[date.getDay()] + ", "+ date.getDate() + " "+ MONTHS[date.getMonth()] + " "+ time);
                        booking.setTax_fare(Double.valueOf(taxFareInteger));
                        booking.setPassenger_fare(Double.valueOf(passengerFareInteger));
                        booking.setTotal_fare(Double.valueOf(totalFareInteger));
                        booking.setEmail(email);
                        booking.setPhone(phone);
                        booking.setStatus("created");
                        booking.setPassengerArrayList(passengerArrayList);
                        createBookingFun();
                    }

                }

            }
        });

    }



    private void init(){
        tvMainOrigin = findViewById(R.id.tv_main_origin);
        tvMainDestination = findViewById(R.id.tv_main_destination);
        tvBookingBusName = findViewById(R.id.tv_booking_bus_name);
        tvBookingBusType = findViewById(R.id.tv_booking_bus_type);
        tvBusPickUpTime = findViewById(R.id.tv_booking_bus_pickup_time);
        tvBusDropTime = findViewById(R.id.tv_booking_bus_drop_time);
        tvBusBookingDateOrigin = findViewById(R.id.tv_booking_bus_pickup_date);
        tvBusBookingDateDestination = findViewById(R.id.tv_booking_bus_drop_date);
        tvBusScheduledDuration = findViewById(R.id.tv_booking_bus_journey_duration);
        tvBusBookingOrigin = findViewById(R.id.tv_booking_bus_origin);
        tvBusBookingDestination = findViewById(R.id.tv_booking_bus_destination);
        tvBusBookingOriginPickup = findViewById(R.id.tv_booking_bus_origin_pickup);
        tvBusBookingDestinationDrop = findViewById(R.id.tv_booking_bus_destination_drop);
        tvBusBookingPassengerFare = findViewById(R.id.tv_base_price_traveller);
        tvBusBookingTaxFare = findViewById(R.id.tv_tax_fees);
        tvBusBookingTotalFare = findViewById(R.id.tv_total_price);
        tvBusBookingTotalFareBold = findViewById(R.id.tv_total_price_bold);

        imgBackArrow = findViewById(R.id.imageView_back_arrow);

        radioGroupPassengerType = findViewById(R.id.radioGroup_passenger_gender);

        etPassengerName = findViewById(R.id.et_passenger_name);
        etPassengerAge = findViewById(R.id.et_passenger_age);
        etBookingEmail = findViewById(R.id.et_contact_email);
        etBookingPhone = findViewById(R.id.et_contact_phone);

        cardViewAddedPassengers = findViewById(R.id.cardView_added_passengers);
        recyclerViewPassengers = findViewById(R.id.bus_passenger_recyclerView);

        btnBookTicket = findViewById(R.id.btn_book_ticket);
        btnAddPassenger = findViewById(R.id.btn_add_passenger);
    }


    private void initDataSource(){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date departureDate, arrivalDate;
        String departureDateStr, arrivalDateStr;
        try {
            departureDate = format.parse(scheduledBus.getDeparture_date());
            arrivalDate = format.parse(scheduledBus.getArrival_date());

            departureDateStr = WEEKS[departureDate.getDay()] + ", "+ departureDate.getDate() + " "+ MONTHS[departureDate.getMonth()];
            arrivalDateStr = WEEKS[arrivalDate.getDay()] + ", "+ arrivalDate.getDate() + " "+ MONTHS[arrivalDate.getMonth()];

            tvBusBookingDateOrigin.setText(departureDateStr);
            tvBusBookingDateDestination.setText(arrivalDateStr);



        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvMainOrigin.setText(scheduledBus.getOrigin());
        tvMainDestination.setText(scheduledBus.getDestination());
        tvBookingBusName.setText(scheduledBus.getBus_name());
        tvBookingBusType.setText(scheduledBus.getBus_type());
        tvBusPickUpTime.setText(scheduledBus.getDeparture_time());
        tvBusDropTime.setText(scheduledBus.getArrival_time());

        tvBusScheduledDuration.setText(scheduledBus.getDuration());
        tvBusBookingOrigin.setText(scheduledBus.getOrigin());
        tvBusBookingDestination.setText(scheduledBus.getDestination());
        tvBusBookingOriginPickup.setText(scheduledBus.getPickup_point());
        tvBusBookingDestinationDrop.setText(scheduledBus.getDrop_point());
//        tvBusBookingPassengerFare.setText(scheduledBus.getBus_name());
//        tvBusBookingTaxFare.setText(scheduledBus.getBus_name());
//        tvBusBookingTotalFare.setText(scheduledBus.getBus_name());
//        tvBusBookingTotalFareBold.setText(scheduledBus.getBus_name());
    }

    private void setBookingFareData() {

        if(passengerArrayList.isEmpty()){
            tvBusBookingPassengerFare.setText(""+0);
            tvBusBookingTaxFare.setText(""+0);
            tvBusBookingTotalFare.setText(""+0);
            tvBusBookingTotalFareBold.setText("$ "+0);
        }else{

            passengerFareInteger = passengerArrayList.size()*scheduledBus.getPrice();
            taxFareInteger = 20;
            totalFareInteger = passengerFareInteger + taxFareInteger;

            tvBusBookingPassengerFare.setText(passengerArrayList.size() + " x " + scheduledBus.getPrice());
            tvBusBookingTaxFare.setText(""+taxFareInteger);
            tvBusBookingTotalFare.setText(""+totalFareInteger);
            tvBusBookingTotalFareBold.setText("$ "+totalFareInteger);
        }
    }


    private void createBookingFun(){
        progressDialog.setMessage("Booking...");
        progressDialog.show();

        NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);

        Call<BookingResponse> bookingResponseCall = networkAPI.createBooking(booking);

        bookingResponseCall.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                if (response.body().isStatus()){
                    Intent intent = new Intent(BookingActivity.this, BookedActivity.class);
                    intent.putExtra("bookingId", response.body().getId());
                    startActivity(intent);
                    finish();
                }else{
                    appUtils.showToast("Something went wrong");
                    progressDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {

            }
        });

    }

}
