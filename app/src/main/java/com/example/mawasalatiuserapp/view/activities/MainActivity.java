package com.example.mawasalatiuserapp.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
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
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private HashMap<String, String> userDetails;
    private AppUtils appUtils;
    private TextView userName;
    private SearchableSpinner spinnerOriginCity, spinnerDestinationCity;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private TextView tvWelcomeMsg;
    String dateStr, origin, destination;
    private ArrayAdapter originCitiesAdapter, destinationCitiesAdapter;

    private Button btnSearchBuses;
    public static final String[] MONTHS = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};

    String[] courses1 = { "C", "Data structures",
            "Interview prep", "Algorithms",
            "DSA with java", "OS" };

    String[] courses2 = { "C", "Data structures",
            "Interview prep", "Algorithms",
            "DSA with java", "OS" };

    ArrayList<String> originCityArrayList;
    ArrayList<String> destinationCityArrayList;
    private TextView tvJourneyDate, tvJourneyDay, tvJourneyMonth, tvJourneyToday;
    private ImageView imageViewMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appUtils = new AppUtils(getApplicationContext(), this);
        userDetails = appUtils.getUserDetails();

        cityListFun();
        String name = userDetails.get(AppUtils.KEY_USER_NAME);
        String token =  userDetails.get(AppUtils.KEY_USER_TOKEN);


        spinnerOriginCity = findViewById(R.id.spinner_origin_city);
        spinnerDestinationCity = findViewById(R.id.spinner_destination_city);
        tvJourneyDate = findViewById(R.id.tv_journey_date);
        tvJourneyDay = findViewById(R.id.tv_journey_day);
        tvJourneyMonth = findViewById(R.id.tv_journey_month);
        tvJourneyToday = findViewById(R.id.tv_journey_today);
        tvWelcomeMsg = findViewById(R.id.tv_welcome_msg);
        btnSearchBuses = findViewById(R.id.btn_search_buses);
        imageViewMenu = findViewById(R.id.image_view_menu);



        spinnerOriginCity.setTitle("Select Origin City");
        spinnerOriginCity.setTitle("Select Destination City");

        spinnerOriginCity.setOnItemSelectedListener(onCityOriginSelectedListener);
        spinnerDestinationCity.setOnItemSelectedListener(onCityDestinationSelectedListener);



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


        btnSearchBuses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScheduledBusActivity.class);
                intent.putExtra("date", dateStr);
                intent.putExtra("origin", origin);
                intent.putExtra("destination", destination);
                startActivity(intent);
            }
        });



        imageViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });

    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.main_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.logout:
                        appUtils.logoutUser();
                        break;
                    case R.id.bookings:
                        Intent intent1 = new Intent(MainActivity.this, BookingListActivity.class);
                        startActivity(intent1);
                        break;
                }
                return true;
            }
        });

        popup.show();
    }



    private AdapterView.OnItemSelectedListener onCityOriginSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            origin = originCityArrayList.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener onCityDestinationSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            destination = destinationCityArrayList.get(position);
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
        originCitiesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, originCityArrayList);
        destinationCitiesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, destinationCityArrayList);

        originCitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destinationCitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerOriginCity.setAdapter(originCitiesAdapter);

        spinnerDestinationCity.setAdapter(destinationCitiesAdapter);

        origin = String.valueOf(originCitiesAdapter.getItem(0));
        destination = String.valueOf(destinationCitiesAdapter.getItem(0));

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

        dateStr = getDateString(mDay,mMonth+1, mYear);

    }

    private void datePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                        Date date = new Date(year, monthOfYear, dayOfMonth);
                        String dayOfWeek = simpledateformat.format(date);

                        tvJourneyDate.setText(dayOfMonth+ "");
                        tvJourneyDay.setText(dayOfWeek);
                        tvJourneyMonth.setText(MONTHS[monthOfYear]);


                        dateStr = getDateString(dayOfMonth, monthOfYear+1, year);


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

    private String getDateString(int day, int month, int year){
        String dateDay = (String.valueOf(day).length() == 1) ? ("0"+ day) : (""+day);
        String dateMonth = (String.valueOf(month).length() == 1) ? ("0"+ month) : (""+month);
        return year + "-"+ dateMonth+ "-"+ dateDay;
    }

}