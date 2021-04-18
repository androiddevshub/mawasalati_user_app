package com.example.mawasalatiuserapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mawasalatiuserapp.R;
import com.example.mawasalatiuserapp.model.User;
import com.example.mawasalatiuserapp.model.responsebean.UserResponse;
import com.example.mawasalatiuserapp.utils.ApiClient;
import com.example.mawasalatiuserapp.utils.AppUtils;
import com.example.mawasalatiuserapp.utils.NetworkAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    private TextView login_here_text;

    private EditText etName, etEmail, etPhone, etAddress, etPassword;
    private Button btnRegister;
    private AppUtils appUtils;
    private String name, email, phone, address, password;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        login_here_text = findViewById(R.id.txt_login_here);

        appUtils = new AppUtils(getApplicationContext());

        progressDialog = new ProgressDialog(RegistrationActivity.this);

        etName = findViewById(R.id.et_regis_name);
        etEmail = findViewById(R.id.et_regis_email);
        etPhone = findViewById(R.id.et_regis_phone);
        etAddress = findViewById(R.id.et_regis_address);
        etPassword = findViewById(R.id.et_regis_password);
        btnRegister = findViewById(R.id.btn_register);

        login_here_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                name = etName.getText().toString();
                email = etEmail.getText().toString();
                phone = etPhone.getText().toString();
                address = etAddress.getText().toString();
                password = etPassword.getText().toString();

                if (name.isEmpty()){
                    appUtils.showToast("Please enter name");
                }else if(email.isEmpty()){
                    appUtils.showToast("Please enter email");
                }else if(phone.isEmpty()){
                    appUtils.showToast("Please enter phone");
                }else if(address.isEmpty()){
                    appUtils.showToast("Please enter address");
                }else if(password.isEmpty()){
                    appUtils.showToast("Please enter password");
                }
                else {
                    userRegisterFun();
                }



            }
        });
    }

    public void userRegisterFun(){

        progressDialog.setMessage("Signing up");
        progressDialog.show();

        final NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);

        Call<UserResponse> userRegisterResponseCall = networkAPI.userRegisterApi(new User(name, email, phone, address, password));

        userRegisterResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if (response.body().isStatus()){
                    progressDialog.dismiss();
                    appUtils.showToast(response.body().getMessage());
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.v("UserRegistration", t.getLocalizedMessage());

            }
        });
    }


}