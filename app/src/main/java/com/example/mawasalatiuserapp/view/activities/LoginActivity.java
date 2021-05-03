package com.example.mawasalatiuserapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mawasalatiuserapp.R;
import com.example.mawasalatiuserapp.model.User;
import com.example.mawasalatiuserapp.model.responsebean.UserResponse;
import com.example.mawasalatiuserapp.utils.ApiClient;
import com.example.mawasalatiuserapp.utils.AppUtils;
import com.example.mawasalatiuserapp.utils.NetworkAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextView register_here_text;

    private EditText etEmail, etPassword;
    private String email, password;

    private Button btnLogin;
    private AppUtils appUtils;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(LoginActivity.this);
        appUtils = new AppUtils(getApplicationContext(), this);

        etEmail = findViewById(R.id.et_login_email);
        etPassword = findViewById(R.id.et_login_password);

        btnLogin = findViewById(R.id.btn_login);
        register_here_text = findViewById(R.id.txt_register_here);

        register_here_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                if (email.isEmpty()){
                    appUtils.showToast("Please enter an email");
                }else if(password.isEmpty()){
                    appUtils.showToast("Please enter password");
                }else {
                    userLoginFun();
                }
            }
        });


    }

    private void userLoginFun(){

        progressDialog.setMessage("Logging In");
        progressDialog.show();

        NetworkAPI networkAPI = ApiClient.getClient().create(NetworkAPI.class);

        Call<UserResponse> userLoginResponseCall = networkAPI.userLoginApi(new User(email, password));

        userLoginResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    showToast(response.body().getMessage());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    appUtils.setLoggedIn(true,
                            String.valueOf(response.body().getUser().getUser_id()),
                            response.body().getUser().getUser_name(),
                            response.body().getUser().getUser_email(),
                            response.body().getUser().getUser_phone(),
                            response.body().getUser().getToken(),
                            String.valueOf(response.body().getUser().getUser_admin()));

                }else{
                    progressDialog.dismiss();
                    showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }

    public void showToast(String msg){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView text = layout.findViewById(R.id.text);
        text.setText(msg);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}