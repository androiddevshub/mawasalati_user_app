package com.example.mawasalatiuserapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mawasalatiuserapp.R;
import com.example.mawasalatiuserapp.model.User;
import com.example.mawasalatiuserapp.model.responsebean.CityResponse;
import com.example.mawasalatiuserapp.model.responsebean.UserResponse;
import com.example.mawasalatiuserapp.utils.ApiClient;
import com.example.mawasalatiuserapp.utils.AppUtils;
import com.example.mawasalatiuserapp.utils.NetworkAPI;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private HashMap<String, String> userDetails;
    private AppUtils appUtils;
    private TextView userName;
    private Button btnLogout;
    private SearchableSpinner spinnerOriginCity, spinnerDestinationCity;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private TextView tvWelcomeMsg;

    public static final String[] MONTHS = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};

    String[] courses1 = { "C", "Data structures",
            "Interview prep", "Algorithms",
            "DSA with java", "OS" };

    String[] courses2 = { "C", "Data structures",
            "Interview prep", "Algorithms",
            "DSA with java", "OS" };

    ArrayList<String> originCityArrayList = new ArrayList<>();
    ArrayList<String> destinationCityArrayList = new ArrayList<>();
    private TextView tvJourneyDate, tvJourneyDay, tvJourneyMonth, tvJourneyToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appUtils = new AppUtils(getApplicationContext());
        userDetails = appUtils.getUserDetails();

//        userName = findViewById(R.id.hello_message);
        btnLogout = findViewById(R.id.btn_logout);
        cityListFun();
        String name = userDetails.get(AppUtils.KEY_USER_NAME);
        String id =  userDetails.get(AppUtils.KEY_USER_ID);


        spinnerOriginCity = findViewById(R.id.spinner_origin_city);
        spinnerDestinationCity = findViewById(R.id.spinner_destination_city);
        tvJourneyDate = findViewById(R.id.tv_journey_date);
        tvJourneyDay = findViewById(R.id.tv_journey_day);
        tvJourneyMonth = findViewById(R.id.tv_journey_month);
        tvJourneyToday = findViewById(R.id.tv_journey_today);
        tvWelcomeMsg = findViewById(R.id.tv_welcome_msg);





        spinnerOriginCity.setTitle("Select Origin City");
        spinnerOriginCity.setTitle("Select Destination City");

        spinnerOriginCity.setOnItemSelectedListener(onCityOriginSelectedListener);
        spinnerDestinationCity.setOnItemSelectedListener(onCityDestinationSelectedListener);

//        userName.setText("Hello, "+name + ": "+ id);

        setTodayDate();
        tvWelcomeMsg.setText("Welcome, "+ name);

        tvJourneyToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTodayDate();
            }
        });



        tvJourneyDate.setOnClickListener(datePickerDialogOnClick);
        tvJourneyDay.setOnClickListener(datePickerDialogOnClick);
        tvJourneyMonth.setOnClickListener(datePickerDialogOnClick);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appUtils.logoutUser();
            }
        });

    }

    private AdapterView.OnItemSelectedListener onCityOriginSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            Log.v("Spinner City Origin", courses1[position]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener onCityDestinationSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.v("Spinner City Dest", courses1[position]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void cityListFun(){

        NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);

        Call<CityResponse> cityResponseCall = networkAPI.cityResponseApi();

        cityResponseCall.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
               if (response.isSuccessful()){
                   originCityArrayList = response.body().getCity().getOriginCityList();
                   destinationCityArrayList = response.body().getCity().getDestinationCityList();
                   setSpinnerData();
               }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {

            }
        });
    }

    private void setSpinnerData(){
        ArrayAdapter originCitiesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, originCityArrayList);
        ArrayAdapter destinationCitiesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, destinationCityArrayList);

        originCitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destinationCitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerOriginCity.setAdapter(originCitiesAdapter);
        spinnerDestinationCity.setAdapter(destinationCitiesAdapter);
    }

    private void setTodayDate(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
        Date date = new Date(mYear, mMonth, mDay-1);
        String dayOfWeek = simpledateformat.format(date);

        tvJourneyDate.setText(mDay+ "");
        tvJourneyDay.setText(dayOfWeek);
        tvJourneyMonth.setText(MONTHS[mMonth]);
    }

    private void datePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                        Date date = new Date(year, monthOfYear, dayOfMonth-1);
                        String dayOfWeek = simpledateformat.format(date);

                        tvJourneyDate.setText(dayOfMonth+ "");
                        tvJourneyDay.setText(dayOfWeek);
                        tvJourneyMonth.setText(MONTHS[monthOfYear]);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private View.OnClickListener datePickerDialogOnClick  = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            datePickerDialog();
        }
    };


}